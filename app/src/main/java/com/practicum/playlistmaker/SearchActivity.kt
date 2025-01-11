package com.practicum.playlistmaker


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    companion object {
        private const val KEY = "KEY"
        private const val TEXT_EDITTEXT: String = ""
    }

    private var value = TEXT_EDITTEXT

    private lateinit var searchPanel: EditText
    private lateinit var resetInput: ImageView
    private lateinit var rswTrackList: RecyclerView
    private lateinit var placeholderNotFound: LinearLayout
    private lateinit var placeholderNotInternet: LinearLayout
    private lateinit var btnUpdate: Button

    private var lastQuery: String = ""


    private var itunesBaseUrl = "https://itunes.apple.com"
    private val trackListAdapter = TrackListAdapter()

    private val retrofit = Retrofit.Builder()
        .baseUrl(itunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val itunesService = retrofit.create(ItunesAPI::class.java)

    private val trackList = ArrayList<Track>()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY, value)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        value = savedInstanceState.getString(KEY, TEXT_EDITTEXT)
        searchPanel.setText(value)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val arrowBtn = findViewById<ImageView>(R.id.arrow_search_activity)

        searchPanel = findViewById(R.id.search_panel)
        resetInput = findViewById(R.id.reset_input)
        placeholderNotFound = findViewById(R.id.placeholder_not_found)
        rswTrackList = findViewById(R.id.rsw_track_list)
        placeholderNotInternet = findViewById(R.id.placeholder_not_internet)
        btnUpdate = findViewById(R.id.btn_update)

        rswTrackList.layoutManager = LinearLayoutManager(this)
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

            trackList.clear()
            trackListAdapter.updateTracks(trackList)
            placeholderNotFound.visibility = View.GONE
            placeholderNotInternet.visibility = View.GONE
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                resetInput.isVisible = true
                resetInput.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                searchPanel.isCursorVisible = true

            }

            override fun afterTextChanged(s: Editable?) {
                value = s.toString()
            }
        }

        searchPanel.addTextChangedListener(simpleTextWatcher)


        searchPanel.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                if (searchPanel.text.isNotEmpty()) {

                    val inputMethodManager = searchPanel.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(searchPanel.windowToken, 0) // скрываем клавиатуру

                    searchPanel.isCursorVisible = false //скрываем курсор

                    lastQuery = searchPanel.text.toString() // сохраняем последний запрос в переменную

                    performSearch(lastQuery)
                }
                true
            }
            false
        }

        btnUpdate.setOnClickListener {
            if (lastQuery.isNotEmpty()) {
                performSearch(lastQuery)
            } else {
                Toast.makeText(this, "Введите запрос для поиска", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun performSearch (query: String){

        placeholderNotFound.visibility = View.GONE
        placeholderNotInternet.visibility = View.GONE
        rswTrackList.visibility = View.GONE

        itunesService.search(searchPanel.text.toString()).enqueue(object :
            Callback<TracksResponse> {
            override fun onResponse(
                call: Call<TracksResponse>,
                response: Response<TracksResponse>
            ) {
                if (response.code() == 200) {
                    trackList.clear()
                    if (response.body()?.results?.isNotEmpty() == true) {
                        trackList.addAll(response.body()?.results!!)
                        trackListAdapter.updateTracks(trackList)
                        rswTrackList.visibility = View.VISIBLE
                    }
                    if (trackList.isEmpty()) {
                        placeholderNotFound.visibility = View.VISIBLE
                    }
                } else {
                    placeholderNotInternet.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                placeholderNotInternet.visibility = View.VISIBLE
            }
        })
    }
}
