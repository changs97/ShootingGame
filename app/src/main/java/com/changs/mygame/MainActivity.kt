package com.changs.mygame


import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import github.hongbeomi.touchmouse.TouchMouseManager
import github.hongbeomi.touchmouse.TouchMouseOption
import java.util.*


class MainActivity : AppCompatActivity() {
    var hit = 0F
    var fire = 0F
    var Accuracy = 0
    var time: TextView? = null
    var count: TextView? = null
    var start: Button? = null
    var accuracy: TextView? = null
    var img_array = arrayOfNulls<ImageView>(6)
    var imageID = intArrayOf(
        R.id.imageView1,
        R.id.imageView5,
        R.id.imageView6,
        R.id.imageView7,
        R.id.imageView8,
        R.id.imageView9
    )
    val TAG_ON = "on" //태그용
    val TAG_OFF = "off"
    var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        time = findViewById<View>(R.id.time) as TextView
        count = findViewById<View>(R.id.count) as TextView
        accuracy = findViewById<View>(R.id.accuracy) as TextView

//효과음 설정
        val soundPool: SoundPool
        val soundID: Int
        soundPool = SoundPool(5, AudioManager.STREAM_MUSIC, 0)
        soundID = soundPool.load(this, R.raw.gun1, 1)
//마우스 커서 설정
        TouchMouseManager.setOptions(
            TouchMouseOption(
                cursorDrawable = R.drawable.point
                // 커스텀 drawable을 사용하고 싶을 때

            )
        )


        for (i in img_array.indices) {
            img_array[i] = findViewById<View>(imageID[i]) as ImageView
            img_array[i]!!.setImageResource(R.drawable.gametarget2)
            img_array[i]!!.tag = TAG_OFF
            //타겟 이미지에 온클릭리스너
            img_array[i]!!.setOnClickListener { v ->


                //타겟 이미지 터치시 효과음 발생
                soundPool.play(soundID, 1f, 1f, 0, 0, 1f)

                fire++

                if ((v as ImageView).tag.toString() == TAG_ON) {
                    hit++
                    score++
                    count!!.text ="score : "+ score.toString()
                    v.setImageResource(R.drawable.gametarget2)
                    v.setTag(TAG_OFF)
                } else {
                    if (score <= 0) {
                        score = 0
                        count!!.text ="score : "+ score.toString()
                    } else {
                        score--
                        count!!.text ="score : "+ score.toString()
                    }
                }
                Accuracy = ((hit / fire) * 100).toInt()
                accuracy!!.text = "명중률 : " + Accuracy.toString() + "%"

            }
        }



        time!!.text = "30초"
        count!!.text = "score : 0"
        accuracy!!.text = "명중률 : 0%"


        // 스레드 활용
        Thread(timeCheck()).start()

        for (i in img_array.indices) {
            Thread(DThread(i)).start()
        }
    }





    var onHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            img_array[msg.arg1]!!.setImageResource(R.drawable.gametarget)
            img_array[msg.arg1]!!.tag = TAG_ON //올라오면 ON태그 달아줌
        }
    }
    var offHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            img_array[msg.arg1]!!.setImageResource(R.drawable.gametarget2)
            img_array[msg.arg1]!!.tag = TAG_OFF //내려오면 OFF태그 달아줌
        }
    }

    //타겟을 올라갔다 내려갔다 해줌
    inner class DThread internal constructor(index: Int) : Runnable {
        var index : Int //타겟 번호
        init {
            this.index = index
        }
        override fun run() {
            while (true) {
                try {
                    val msg1 = Message()
                    val offtime = Random().nextInt(5000) + 500 //난수 생성 범위 조절
                    Thread.sleep(offtime.toLong()) //타겟이 내려가있는 시간 스레드 활용
                    msg1.arg1 = index
                    onHandler.sendMessage(msg1)

                    val ontime = Random().nextInt(2000) + 500 //난수 생성 범위 조절
                    Thread.sleep(ontime.toLong()) //타겟이 올라가있는 시간 스레드 활용
                    val msg2 = Message()
                    msg2.arg1 = index
                    offHandler.sendMessage(msg2)

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }


    }

    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            time!!.text = msg.arg1.toString() + "초"
        }
    }
    var isRunning = true
    //타이머 일시정지 - 문제 : 시간 차이 2초 정도 생김

    override fun onResume() {
        super.onResume()
        isRunning = true

    }

    override fun onPause() {
        super.onPause()
        isRunning = false
    }

    //플레이 타이머
    inner class timeCheck : Runnable {
        val MAXTIME = 30
        var i = MAXTIME
        override fun run() {
           while (i > 0 ) {
               if(isRunning) {
                   val msg = Message()
                   msg.arg1 = i
                   handler.sendMessage(msg)
                   i--
                   try {
                       Thread.sleep(1000)
                   } catch (e: InterruptedException) {
                       e.printStackTrace()
                   }
               }else{
                   i++
                   while (isRunning == false) {
                       Thread.sleep(1000)
                   }
               }

            }
            //결과 화면으로 이동
            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            if(score > 0){
                intent.putExtra("score", score)} //score가 양수일때, 게임 종료 후 아무런 터치 동작을 하지 않았는데 score가 1증가하는 문제가 있음
            else{
                intent.putExtra("score", score)}
            startActivity(intent)
            finish()
        }
    }



}

