package com.practicum.playlistmaker


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
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
    private lateinit var placeholder: TextView

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
        placeholder = findViewById(R.id.placeholder)
        rswTrackList = findViewById(R.id.rsw_track_list)

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


        searchPanel.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                if (searchPanel.text.isNotEmpty()) {
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
                                    trackListAdapter.notifyDataSetChanged()
                                }
                                if (trackList.isEmpty()) {
                                    showMessage("Ничего не нашлось", "")
                                } else {
                                    showMessage("", "")
                                }
                            } else {
                                showMessage(
                                    "Нет соединения с интернетом",
                                    response.code().toString()
                                )
                            }
                        }

                        override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                            showMessage(
                                "Нет соединения с интернетом",
                                t.message.toString()
                            )
                        }
                    })

                }
                true
            }
            false
        }
    }

    private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            placeholder.visibility = View.VISIBLE
            trackList.clear()
            trackListAdapter.notifyDataSetChanged()
            placeholder.text = text
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            placeholder.visibility = View.GONE
        }
    }

}
