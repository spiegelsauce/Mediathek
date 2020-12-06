package com.example.orfdownloader.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.orfdownloader.R
import com.example.orfdownloader.ui.station.StationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, StationFragment.newInstance())
                    .commit()
        }
    }
}