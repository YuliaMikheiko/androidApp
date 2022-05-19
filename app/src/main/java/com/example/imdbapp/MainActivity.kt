package com.example.imdbapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import com.example.imdbapp.db.DbManager
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.lang.Thread.sleep

private lateinit var searchButton: Button
private lateinit var searchText: EditText

private lateinit var textView: TextView
private lateinit var movieFrame: FrameLayout
private lateinit var movieFragment: MovieFragment
private lateinit var errorView: TextView



class MainActivity : AppCompatActivity() {
    private val dbManager = DbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        errorView.text = ""
        searchButton.setOnClickListener(searchButtonListener)

    }

    private var searchButtonListener: View.OnClickListener = View.OnClickListener {
        if(!searchButton.isClickable){
            return@OnClickListener
        }
        else {
            searchButton.setText(R.string.search)
            searchButton.isClickable = false
            var text = checkText()
            if (text == "false") {
                Toast.makeText(applicationContext, R.string.field_error, Toast.LENGTH_SHORT).show()
                return@OnClickListener
            } else {
                searchMovie(text)

            }
            searchButton.isClickable = true
            searchButton.setText(R.string.search_button_text)
        }
    }


    private fun checkText() : String{
        var text = ""
        if(searchText.text.isEmpty()){

            return "false"
        }
        else{
            dbManager.openDb()
            dbManager.insertToDb(searchText.text.toString())
            dbManager.closeDb()

            text = searchText.text.toString()
            text = text.replace(" ", "%20", false)
            Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
            return text

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.history){
            dbManager.openDb()
            val historyData = dbManager.readDbData()
            dbManager.closeDb()

            val i = Intent(this, HistoryActivity::class.java)
            i.putStringArrayListExtra("dataList", historyData)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchMovie(string: String){
        errorView.text = ""
        val thread = Thread {
            try {
                val client = OkHttpClient()

                val request = Request.Builder()
                    .url("https://imdb-data-searching.p.rapidapi.com/om?t=${string}")
                    .get()
                    .addHeader("X-RapidAPI-Host", "imdb-data-searching.p.rapidapi.com")
                    .addHeader("X-RapidAPI-Key", "dfa6290850msh64016a7d44346b0p13fe35jsn072b27ab7125")
                    .build()

                val response = client.newCall(request).execute()
                val test = response.body()?.string()
                var error = JSONObject(test).getString("Response")

                if(error == "False"){
                    runOnUiThread {
                        errorView.setText(R.string.not_found)
                    }
                    return@Thread
                }


                    var title = JSONObject(test).getString("Title")
                    var years = JSONObject(test).getString("Year")
                    var genre = JSONObject(test).getString("Genre")
                    var plot = JSONObject(test).getString("Plot")
                    var imdb = JSONObject(test).getString("imdbRating")
                    var totalSeasons = JSONObject(test).getString("totalSeasons")
                    var imageTitle = JSONObject(test).getString("Poster")

                    val bundle = Bundle()
                    bundle.putString("title", title)
                    bundle.putString("years", years)
                    bundle.putString("genre", genre)
                    bundle.putString("plot", plot)
                    bundle.putString("imdb", imdb)
                    bundle.putString("totalSeasons", totalSeasons)
                    bundle.putString("imageTitle", imageTitle)

                    openFragment(bundle)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    private fun openFragment(bundle: Bundle){
        movieFragment = MovieFragment()
        movieFragment.arguments = bundle
        var fragmentTransaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(movieFrame.id, movieFragment)
        fragmentTransaction.commit()
    }

    private fun init(){

        searchButton = findViewById(R.id.searchButton)
        searchText = findViewById(R.id.movieText)
        textView = findViewById(R.id.textView)
        movieFrame = findViewById(R.id.movieFrame)
        errorView = findViewById(R.id.errorView)
    }
}