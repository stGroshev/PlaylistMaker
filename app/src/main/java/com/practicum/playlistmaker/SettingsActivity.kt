package com.practicum.playlistmaker

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val back = findViewById<ImageView>(R.id.back)
        val userAgreement = findViewById<LinearLayout>(R.id.userAgreementLinearLayout)
        val support = findViewById<LinearLayout>(R.id.supportLinearLayout)
        val share = findViewById<LinearLayout>(R.id.shareLinearLayout)

        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            finish()
        }

        userAgreement.setOnClickListener {
            val address = Uri.parse("https://yandex.ru/legal/practicum_offer/")
            val intent = Intent(ACTION_VIEW, address)
            startActivity(intent)
        }

        support.setOnClickListener {
            val email = arrayOf("st.groshev96@gmail.com")
            val subject = getString(R.string.subject_message)
            val text = getString(R.string.text_message)

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.putExtra(Intent.EXTRA_EMAIL, email)
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, text)

            intent.data = Uri.parse("mailto:")
            startActivity(intent)
        }

        share.setOnClickListener {
            val textMessage = getString(R.string.yandex_practicum_android)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra("Share this", textMessage)
            //добавил chooser чтоб посмотреть, чем он отличается от стандартного вызова меню выбора
            val chooser = Intent.createChooser(intent, getString(R.string.share_using))
            startActivity(chooser)
        }

    }
}