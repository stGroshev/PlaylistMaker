package com.practicum.playlistmaker


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

        val rswTrackList = findViewById<RecyclerView>(R.id.rsw_track_list)
        rswTrackList.layoutManager = LinearLayoutManager(this)
        val trackListAdapter = TrackListAdapter(createTrackList())
        rswTrackList.adapter = trackListAdapter


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
        value = savedInstanceState.getString(KEY, TEXT_EDITTEXT)
        searchPanel.setText(value)
    }

    companion object {
        private const val KEY = "KEY"
        private const val TEXT_EDITTEXT: String = ""
    }


    private fun createTrackList(): ArrayList<Track> {
        return arrayListOf(
            Track(
                "Smells Like Teen Spirit",
                "Nirvana",
                "4:35",
                "https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg"
            ),
            Track(
                "Billie Jean",
                "Michael Jackson",
                "4:35",
                "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg"
            ),
            Track(
                "Stayin' Alive",
                "Bee Gees",
                "4:10",
                "https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg",
            ),
            Track(
                "Whole Lotta Love",
                "Led Zeppelin",
                "5:33",
                "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg",
            ),
            Track(
                "Sweet Child O'Mine",
                "Guns N' Roses",
                "5:03",
                "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg",
            )
        )
    }
}
