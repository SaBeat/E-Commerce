package com.example.e_commerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.e_commerce.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        NavigationUI.setupWithNavController(
            mainBinding.bottomNavigationView,
            navHostFragment.navController
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginRegisterFragment) {
                mainBinding.bottomNavigationView.visibility = View.GONE
            } else if (destination.id == R.id.splashFragment) {
                mainBinding.bottomNavigationView.visibility = View.GONE
            } else if(destination.id == R.id.forgotPasswordFragment){
                mainBinding.bottomNavigationView.visibility = View.GONE
            }
            else {
                mainBinding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }
}