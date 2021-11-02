package com.changs.mygame

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.changs.mygame.databinding.ActivityExplainBinding
import github.hongbeomi.touchmouse.TouchMouseManager
import github.hongbeomi.touchmouse.TouchMouseOption

class ExplainActivity : AppCompatActivity() {
    val binding by lazy{ActivityExplainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        TouchMouseManager.setOptions(
            TouchMouseOption(
                cursorColor = R.color.black
                // 커스텀 drawable을 사용하고 싶을 때

            )
        )


        binding.okbtn.setOnClickListener {
            finish()
        }


    }
}


