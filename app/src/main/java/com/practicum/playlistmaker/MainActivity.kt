package com.practicum.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var intent: Intent
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonSearch = findViewById<MaterialButton>(R.id.button_search)
        val buttonMedia = findViewById<MaterialButton>(R.id.button_media)
        val buttonSettings = findViewById<MaterialButton>(R.id.button_settings)

        buttonSearch.setOnClickListener {
            intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        buttonMedia.setOnClickListener {
            intent = Intent(this, MediaActivity::class.java)
            startActivity(intent)
        }

        buttonSettings.setOnClickListener {
            intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}