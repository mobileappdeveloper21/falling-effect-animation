package com.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.myapplication.databinding.ActivityDemoBinding

class DemoActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}