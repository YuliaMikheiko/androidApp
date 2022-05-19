package com.example.imdbapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HistoryActivity : AppCompatActivity() {
    private lateinit var returnButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        init()
        val titleList = intent.getStringArrayListExtra( "dataList" )
        returnButton.setOnClickListener(returnButtonListener)

        val recyclerView: RecyclerView = findViewById(R.id.historyView)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = CustomRecyclerAdapter(titleList!!)


    }

    private var returnButtonListener: View.OnClickListener = View.OnClickListener {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun init(){
        returnButton = findViewById(R.id.toMainButton)
    }
}