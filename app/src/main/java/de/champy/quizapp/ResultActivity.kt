package de.champy.quizapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        hideStatusBar()


        //man könnte auch getIntExtra machen, für die Ausgabe am Ende, anstatt toString bei QuizQuestionActivity
        //wenn man dies macht, muss noch default angegeben werden
        val username = intent.getStringExtra(Constants.USER_NAME)
        val correctAnswers = intent.getStringExtra(Constants.CORRECT_ANSWERS)
        val totalQuestion = intent.getStringExtra(Constants.TOTAL_QUESTIONS)

        tvName.text = username
        tvScore.text = "Du hast $correctAnswers von $totalQuestion Fragen richtig beantwortet."

        btnFinish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

    }



    fun hideStatusBar() {
        if (Build.VERSION.SDK_INT < 30 ) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        } else {
            window.setDecorFitsSystemWindows(false)
            val controller = window.insetsController
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars())
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }
}