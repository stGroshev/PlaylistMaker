package com.practicum.playlistmaker


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.isVisible

class SearchActivity : AppCompatActivity() {
    private var value = TEXT_EDITTEXT
    private lateinit var searchPanel: EditText
    private lateinit var resetInput: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val arrowBtn = findViewById<ImageView>(R.id.arrow_search_activity)
        searchPanel = findViewById(R.id.search_panel)
        resetInput = findViewById(R.id.reset_input)

        arrowBtn.setOnClickListener {
            finish()
        }

        resetInput.setOnClickListener {
            searchPanel.setText("")
            val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(searchPanel.windowToken, 0)
            searchPanel.isCursorVisible = false
            resetInput.isVisible = false
        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                resetInput.isVisible = true
              //resetInput.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
               searchPanel.isCursorVisible = true

            }

            override fun afterTextChanged(s: Editable?) {
                value = s.toString()
            }
        }

        searchPanel.addTextChangedListener(simpleTextWatcher)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY, value)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //if (savedInstanceState != null) {
            value = savedInstanceState.getString(KEY, TEXT_EDITTEXT)
            searchPanel.setText(value)
        //} убрал проверку так как студия говорит, что условие проверки всегда истинно и в ней нет смысла поэтому
    }

    companion object {
        private const val KEY = "KEY"
        private const val TEXT_EDITTEXT: String = ""
    }


}
