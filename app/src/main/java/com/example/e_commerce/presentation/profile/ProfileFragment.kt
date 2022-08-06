package com.example.e_commerce.presentation.profile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    companion object{
         val TAKE_PHOTO_CODE = 1222
         val CHOOSE_PHOTO_FROM_GALLERY_CODE = 100
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

        profileBinding?.btnChooseFromGallery?.setOnClickListener {
            pickImageFromGallery()
        }

        return profileBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==TAKE_PHOTO_CODE){
            val images  = data?.extras?.get("data") as Bitmap
            profileBinding?.imageView?.setImageBitmap(images)
        }
        else if(requestCode== CHOOSE_PHOTO_FROM_GALLERY_CODE && requestCode==RESULT_OK){
            profileBinding?.imageView?.setImageURI(data?.data)
        }
    }

    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, CHOOSE_PHOTO_FROM_GALLERY_CODE)
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