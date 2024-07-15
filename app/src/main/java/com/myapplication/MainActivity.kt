package com.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.myapplication.customView.confettiView.ConfettiView
import com.myapplication.databinding.ActivityMainBinding
import com.myapplication.dialogs.EmojiDialog
import com.myapplication.dialogs.OptionsDialogs
import java.util.Random
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    private val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnClick()
    }

    private fun btnClick() {

        binding.snowBtn.setOnClickListener {
            binding.customeView.stopFalling()
            binding.customeView.setCongratulationAnimation(false)
            binding.customeView.setFallingIcon(R.drawable.snowflake)
            binding.customeView.createFallingIconArray()
        }

        binding.rainbtn.setOnClickListener {
            binding.customeView.stopFalling()
            binding.customeView.setCongratulationAnimation(false)
            binding.customeView.setFallingIcon(R.drawable.rain_line)
            binding.customeView.createFallingIconArray()
        }

        binding.congratulationBtn.setOnClickListener {
            binding.customeView.stopFalling()
            binding.customeView.setCongratulationAnimation(true)
            binding.customeView.createFallingIconArray()
        }

        binding.emojiBtn.setOnClickListener {
            val emojiDialog = EmojiDialog(){emoji->
                binding.customeView.stopFalling()
                binding.customeView.setCongratulationAnimation(false)
                binding.customeView.createFallingIconArray(isText = true,emoji)
            }
            emojiDialog.show(supportFragmentManager,"EmojiDialog")
        }

        binding.moreBtn.setOnClickListener {

            val optionDialog = OptionsDialogs(){text ->
                binding.customeView.stopFalling()
                binding.customeView.setCongratulationAnimation(false)
                binding.customeView.createFallingIconArray(isText = true,text)
            }

            optionDialog.show(supportFragmentManager,"MoreOptionDialog")
        }

    }
}