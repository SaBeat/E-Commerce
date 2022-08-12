package com.example.e_commerce.presentation.profile

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
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
    private val ISDARK = "IS_DARK"

    @Inject
    lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileBinding = FragmentProfileBinding.inflate(inflater)

        return profileBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        profileBinding?.apply {
            switchMode.isChecked = false

            switchMode.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    prefs.edit().putBoolean(ISDARK, true).apply()
                    switchMode.text = "Dark"
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    prefs.edit().putBoolean(ISDARK, false).apply()
                    switchMode.text = "Light"
                }
            }
        }

        initObservers()

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