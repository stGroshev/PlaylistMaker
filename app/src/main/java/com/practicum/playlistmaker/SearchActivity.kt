package com.practicum.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val arrowBtn = findViewById<ImageView>(R.id.arrow_search_activity)
        val searchPanel = findViewById<EditText>(R.id.search_panel)
        val resetInput = findViewById<ImageView>(R.id.reset_input)

        arrowBtn.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            finish()
        }

        resetInput.setOnClickListener{
            searchPanel.setText("")
        }

        val simpleTextWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                resetInput.visibility = if (s.isNullOrEmpty())View.GONE else View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
            }

        }
        searchPanel.addTextChangedListener(simpleTextWatcher)
    }

}