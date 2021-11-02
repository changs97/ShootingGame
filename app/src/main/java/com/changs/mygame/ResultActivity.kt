package com.changs.mygame

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import github.hongbeomi.touchmouse.TouchMouseManager
import github.hongbeomi.touchmouse.TouchMouseOption


class ResultActivity : AppCompatActivity() {
    var sub_result1: TextView? = null
    var sub_result2: TextView? = null
    var record: TextView? = null
    var sub_retry: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result) //주소로 알고있는 xml을 눈에 보이는 view로 바꿔줌 ->InfLate
        sub_result1 = findViewById(R.id.sub_result)
        sub_result2 = findViewById(R.id.sub_result2)
        sub_retry = findViewById(R.id.sub_retry)
        record = findViewById(R.id.record)
        val score = intent.getIntExtra("score", 0)

        var spf: SharedPreferences? = null
        spf = getSharedPreferences("spfScore",MODE_PRIVATE); // 키값이 또 있으면 덮어쓰겠다
        record!!.setText("최고기록 : " + spf.getInt("spfscore", 0))

        if (spf.getInt("spfscore", 0) < score) { //내점수가 저번 점수보다 크면
            spf.edit().putInt("spfscore", score).commit() //반영의 commit(). 현재상태 저장
            sub_result1!!.setText("신기록 달성!\n${score}점")
            record!!.setText("최고기록 : ${score}점")
        }
        else{
            sub_result1!!.setText(score.toString() + "점")
        }






        sub_retry!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ResultActivity, StartActivity::class.java)
            startActivity(intent)
            finish()
        })
        TouchMouseManager.setOptions(
            TouchMouseOption(
                cursorColor = R.color.black
                // 커스텀 drawable을 사용하고 싶을 때

            )
        )
       when(score){
           0 ->{sub_result2!!.setText("총을 발로 쏘셨군요!!")}
           in 1..10 -> {sub_result2!!.setText("혹시 미필이신가요?!")}
           in 10..30 -> {sub_result2!!.setText("당신은 사격의 신!!")}
           else -> {sub_result2!!.setText("대대장님이 포상 휴가 주신데요!!")}
       }


    }
}

