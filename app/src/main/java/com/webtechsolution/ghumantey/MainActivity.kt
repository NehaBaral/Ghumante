package com.webtechsolution.ghumantey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.webtechsolution.ghumantey.data.Preferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (preferences.isAgency == false){
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController.setGraph(R.navigation.main_nav)
        }else{
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController.setGraph(R.navigation.agency_nav)
        }
        if (!preferences.isUserLoggedIn) {
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController.apply {
                popBackStack()
                if (preferences.isAgency == false){
                    navigate(MainNavDirections.actionToAuth())
                }else{
                    navigate(AgencyNavDirections.actionToAuth())
                }

            }
        }
    }
}