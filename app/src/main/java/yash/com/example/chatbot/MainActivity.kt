package yash.com.example.chatbot

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences(
            "sharedPrefs", MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        val isDarkModeOn = sharedPreferences.getBoolean(
                "isDarkModeOn", false
            )


        val srtbutton: Button = findViewById(R.id.firstbutton)
        val btnToggleDark: Button = findViewById(R.id.darkMode)


        srtbutton.setOnClickListener {
            val myIntent = Intent(this, LastPage::class.java)
            this.startActivity(myIntent)
        }

        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            btnToggleDark.text = "Disable Dark Mode"
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            btnToggleDark.text = "Enable Dark Mode"
        }

        btnToggleDark.setOnClickListener {
            if (isDarkModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                editor.putBoolean("isDarkModeOn", false)
                editor.apply()

                btnToggleDark.text="Enable Dark Mode"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                editor.putBoolean("isDarkModeOn", true)
                editor.apply()

                btnToggleDark.text = "Disable Dark Mode"
            }
        }
    }

}
