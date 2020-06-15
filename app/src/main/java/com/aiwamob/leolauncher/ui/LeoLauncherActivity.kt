package com.aiwamob.leolauncher.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aiwamob.leolauncher.R
import com.aiwamob.leolauncher.databinding.ActivityLeoLauncherBinding

class LeoLauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeoLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeoLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}