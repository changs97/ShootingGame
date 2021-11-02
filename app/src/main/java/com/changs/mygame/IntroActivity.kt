package com.changs.mygame

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.changs.mygame.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    val binding by lazy{ActivityIntroBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mediaPlayer1 = MediaPlayer.create(this, R.raw.introgun) //배경음악 설정
        mediaPlayer1.start()


        var handler = Handler()
        handler.postDelayed( {
            var intent = Intent( this, StartActivity::class.java)
            startActivity(intent) },
            3000) }

    override fun onPause() {
        super.onPause()
        finish()
    }


}
