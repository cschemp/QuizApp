package de.champy.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    // Variable für Nutzernamen
    private var mUserName: String? = null
    private var mCurrentPosition = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedOption: Int = 0
    private var mCorrectAnswers: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        // Liest den extra String aus dem Intent aus und speichert diesen
        // in der Variable userName
        mUserName = intent.getStringExtra(Constants.USER_NAME)

        setQuestion()

        // Zeigt den Nutzernamen als Toast an
        //Toast.makeText(this, "$userName", Toast.LENGTH_LONG).show()

        tvOptionOne.setOnClickListener(this)
        tvOptionTwo.setOnClickListener(this)
        tvOptionThree.setOnClickListener(this)
        tvOptionFour.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    private fun setQuestion() {

        //Abrufen der Fragenliste


        //Zurücksetzten des Layouts der Fragenoptionen
        defaultOptionsView()

        if (mCurrentPosition == mQuestionList!!.size){
            btnSubmit.text = "BEENDEN"
        } else {
            btnSubmit.text = "BESTÄTIGEN"
        }

        //Aktuelle Frage aus Fragenliste in Objekt der Datenklasse Question
        val question = mQuestionList!![mCurrentPosition-1]


        progressBar.progress = mCurrentPosition
        tvProgress.text = "$mCurrentPosition/" + mQuestionList!!.size

        tvQuestion.text = question!!.question
        ivPicture.setImageResource((question.image))
        tvOptionOne.text = question.optionOne
        tvOptionOne.text = question.optionTwo
        tvOptionOne.text = question.optionThree
        tvOptionOne.text = question.optionFour
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0, tvOptionOne)
        options.add(1, tvOptionTwo)
        options.add(2, tvOptionThree)
        options.add(3, tvOptionFour)

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this, R.drawable.default_option_border
            )
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvOptionOne -> {
                selectedOptionview(tvOptionOne, 1)
            }
            R.id.tvOptionTwo -> {
                selectedOptionview(tvOptionTwo, 2)
            }
            R.id.tvOptionThree -> {
                selectedOptionview(tvOptionThree, 3)
            }
            R.id.tvOptionFour -> {
                selectedOptionview(tvOptionFour, 4)
            }
            R.id.btnSubmit -> {



                if (mSelectedOption == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionList!!.size -> {
                            setQuestion()
                        } else ->{

                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra(Constants.USER_NAME, mUserName)
                        intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers.toString())
                        intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList!!.size.toString())
                        startActivity(intent)

                        finish()

                    }
                    }
                } else {

                    btnSubmit.isEnabled=false
                    tvOptionOne.isEnabled=false
                    tvOptionTwo.isEnabled=false
                    tvOptionThree.isEnabled=false
                    tvOptionFour.isEnabled=false

                    val question = mQuestionList?.get(mCurrentPosition-1)
                    if (question!!.correctAnswer != mSelectedOption){
                        answerView(mSelectedOption, R.drawable.wrong_option_border)
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(question!!.correctAnswer, R.drawable.correct_option_border)

                    if (mCurrentPosition == mQuestionList!!.size){
                        btnSubmit.text = "BEENDEN"
                    } else {
                        btnSubmit.text = "NÄCHSTE FRAGE"
                    }
                    mSelectedOption = 0

                    Handler().postDelayed({
                        btnSubmit.isEnabled=true
                        btnSubmit.performClick()
                        tvOptionOne.isEnabled=true
                        tvOptionTwo.isEnabled=true
                        tvOptionThree.isEnabled=true
                        tvOptionFour.isEnabled=true
                    }, 2000)


                }
            }
        }
    }



    private fun answerView(answer: Int, drawableView: Int){
        when (answer){
            //Option 1
            1 -> {
                tvOptionOne.background = ContextCompat.getDrawable(
                        this, drawableView
                )
            }
            //Option 2
            2 -> {
                tvOptionTwo.background = ContextCompat.getDrawable(
                        this, drawableView
                )
            }
            //Option 3
            3 -> {
                tvOptionThree.background = ContextCompat.getDrawable(
                        this, drawableView
                )
            }
            //Option 4
            4 -> {
                tvOptionFour.background = ContextCompat.getDrawable(
                        this, drawableView
                )
            }
        }
    }

    private fun selectedOptionview(tv: TextView, selectedOptionNum:Int){
        defaultOptionsView()
        mSelectedOption = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this, R.drawable.selected_option_border
        )
    }
}



