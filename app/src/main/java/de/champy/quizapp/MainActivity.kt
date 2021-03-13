package de.champy.quizapp


import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.util.*


class MainActivity : AppCompatActivity() {
    val questionsList = ArrayList<Question>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Methode zum verstecken der Statusleiste
        hideStatusBar()
        c = this;


        // onClickListener für Startbutton
        btnStart.setOnClickListener {
            // Prüft, ob Nutzer einen Namen eingegeben hat
            if (etName.text.toString().isEmpty()) {
                // Meldung an den Nutzer
                Toast.makeText(this, "Bitte gib deinen Namen ein", Toast.LENGTH_LONG).show()
            }


            StartQuiz()


        }
    }


    fun StartQuiz() {
        val intent = Intent(this, QuizQuestionsActivity::class.java)
        // Speichert Benutzereingabe im Intent, sodass dieser übergeben werden kann
        intent.putExtra(Constants.USER_NAME, etName.text.toString())
        // Start der Activity - Die Infos dazu befinden sich in intent
        startActivity(intent)
        finish()
    }

    /*
    Methode fragt aktuelle Version von Android App. Ist diese kleiner als 30, wird
    die alte Methode für das Ausblenden der Statusbar benutzt. Ist die Version aktueller
    wird die neue Methode mit dem WindowInsetsController genutzt.
     */
    fun hideStatusBar() {
        if (Build.VERSION.SDK_INT < 30) {
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

    companion object {
        lateinit var c: Context
    }
}