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
            finish()
        }

        userAgreement.setOnClickListener {
            val intent = Intent(ACTION_VIEW, Uri.parse(this.getString(R.string.oferta_url)))
            startActivity(intent)
        }

        support.setOnClickListener {
            val email = arrayOf(getString(R.string.email_developer))
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.putExtra(Intent.EXTRA_EMAIL, email)
            intent.putExtra(Intent.EXTRA_SUBJECT, this.getString(R.string.subject_message))
            intent.putExtra(Intent.EXTRA_TEXT, this.getString(R.string.text_message))

            intent.data = Uri.parse("mailto:")
            startActivity(intent)
        }

        share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra("Share this", this.getString(R.string.yandex_practicum_android))
            //добавил chooser чтоб посмотреть, чем он отличается от стандартного вызова меню выбора
            val chooser = Intent.createChooser(intent, getString(R.string.share_using))
            startActivity(chooser)
        }

    }
}