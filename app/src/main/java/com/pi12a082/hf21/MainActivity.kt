package com.pi12a082.hf21

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.pi12a082.hf21.navigation.SetUpNavGraph
import com.pi12a082.hf21.ui.theme.NectarTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            val navController = rememberNavController()
            NectarTheme {
                SetUpNavGraph(navController = navController)
            }
        }
    }
}