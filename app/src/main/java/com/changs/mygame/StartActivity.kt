package com.changs.mygame

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.changs.mygame.databinding.ActivityStartBinding
import github.hongbeomi.touchmouse.TouchMouseManager
import github.hongbeomi.touchmouse.TouchMouseOption


class StartActivity : AppCompatActivity() {

        companion object {
            var mediaPlayer : MediaPlayer? = null

        }

    val binding by lazy{ActivityStartBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        TouchMouseManager.setOptions(
            TouchMouseOption(
                cursorColor = R.color.black
                // 색상을 변경하고 싶을 때

            )
        )

        mediaPlayer = MediaPlayer.create(this, R.raw.bgm) //배경음악 설정

       /* Glide.with(this).load("https://www.wallpapertip.com/wmimgs/78-786355_battlefield-4-wallpaper-sniper.jpg").
        into(binding.imageView)*/




        binding.startbtn.setOnClickListener {
            val intent = Intent(this, ReadyActivity::class.java)
            startActivity(intent)

        }

        binding.explainbtn.setOnClickListener {
            val intent = Intent(this, ExplainActivity::class.java)
            startActivity(intent)
        }



    }




    override fun onResume() {
        super.onResume()
        mediaPlayer!!.start()

    }

    override fun onPause() {
        super.onPause()
        mediaPlayer!!.pause()

    }



    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer!!.stop()

    }



}