package com.changs.mygame

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.changs.mygame.databinding.ActivityIntroBinding
import com.changs.mygame.databinding.ActivityReadyBinding

class ReadyActivity : AppCompatActivity() {
    companion object {
        var mediaPlayer1: MediaPlayer? = null

    }

    val binding by lazy { ActivityReadyBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mediaPlayer1 = MediaPlayer.create(this, R.raw.countdown) //배경음악 설정


        var handler = Handler()
        handler.postDelayed(
            {
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            },
            5000
        )
    }




    override fun onResume() {
        super.onResume()
        mediaPlayer1!!.setLooping(true)
        mediaPlayer1!!.start()

    }

    override fun onPause() {
        super.onPause()
        finish()

    }



    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer1!!.stop()

    }


}