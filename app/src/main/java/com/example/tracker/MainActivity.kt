package com.example.tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import com.example.track3dlibrary.Common.BaseViewModel
import com.example.tracker.Common.Navigation.Navigation
import com.example.tracker.Presentation.UserScreen.Dashboard
import com.example.tracker.Presentation.UserScreen.UserScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                Column {
//                    UserScreen()
                    Navigation(BaseViewModel.Companion)
                    // TODO: Replace with your own content.
                    Text(text = "Hello World!")
                }





        }
    }
}