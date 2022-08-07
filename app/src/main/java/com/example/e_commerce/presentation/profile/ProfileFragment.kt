package com.example.e_commerce.presentation.profile

import android.Manifest
import android.R
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.e_commerce.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var profileBinding: FragmentProfileBinding? = null
    private val viewModel: ProfileViewModel by viewModels()
    val IS_DARK = "IS_DARK"
    lateinit var myPreference: MyPreference
    val languageList = arrayOf("az","en")

    companion object{
         val TAKE_PHOTO_CODE = 1222
    }

    @Inject
    lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileBinding = FragmentProfileBinding.inflate(inflater)

        if(ContextCompat.checkSelfPermission(context!!.applicationContext,Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA),TAKE_PHOTO_CODE)
        }

        profileBinding?.btnTakePhoto?.setOnClickListener {
            val image = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(image,TAKE_PHOTO_CODE)
        }


        myPreference = MyPreference(requireContext())

        profileBinding?.spinner?.adapter = ArrayAdapter(requireContext(),
            R.layout.simple_list_item_1,languageList)

        val lang = myPreference.getLoginCount()
        val index = languageList.indexOf(lang)
        if(index >= 0){
            profileBinding?.spinner?.setSelection(index)
        }

        profileBinding?.btnOk?.setOnClickListener {
            myPreference.setLoginCount(languageList[profileBinding?.spinner?.selectedItemPosition!!])
        }

        return profileBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        profileBinding?.apply {
            switchMode.isChecked = false

            switchMode.setOnCheckedChangeListener{btnView,isChecked ->
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    prefs.edit().putBoolean(IS_DARK, true).apply();
                    switchMode.text = "Dark"
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    prefs.edit().putBoolean(IS_DARK, false).apply();
                    switchMode.text = "Light"
                }
            }
        }

        initObservers()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==TAKE_PHOTO_CODE){
            val images  = data?.extras?.get("data") as Bitmap
            profileBinding?.imageView?.setImageBitmap(images)
        }
    }

    private fun initObservers() {
        viewModel.handleEvent(ProfileUiEvent.GetCurrentUserFromDatabase(userId))
        viewModel.handleEvent(ProfileUiEvent.GetFavoriteItemCount(userId))
        viewModel.handleEvent(ProfileUiEvent.GetCollectionItemCount(userId))
        viewModel.handleEvent(ProfileUiEvent.GetPurchasedItemCount(userId))

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel._uiState.collect { state ->
                    state.user?.let { user ->
                        profileBinding?.apply {
                            textUsername.text = user.userName
                            textEmail.text = user.userMail
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel._uiState.collect { state ->
                    state.favoriteCount?.let { favorite ->
                        profileBinding?.apply {
                            textFavCount.text = favorite.toString()
                        }
                    }
                    state.collectionCount?.let { collection ->
                        profileBinding?.apply {
                            textCollectionCount.text = collection.toString()
                        }
                    }
                    state.favoriteCount?.let { favorite ->
                        profileBinding?.apply {
                            textPurchasedCount.text = favorite.toString()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        profileBinding = null
    }
}