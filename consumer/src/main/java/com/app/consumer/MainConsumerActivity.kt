package com.app.consumer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.consumer.DbUri.DbProperty.Companion.CONTENT_URI
import com.app.consumer.databinding.ActivityMainBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainConsumerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecyclerViewFavAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        adapter = RecyclerViewFavAdapter()
        adapter.notifyDataSetChanged()
        val recyclerViewFavAdapter = findViewById<RecyclerView>(R.id.id_home_recycler_fav)
        recyclerViewFavAdapter.layoutManager = LinearLayoutManager(this)
        recyclerViewFavAdapter.adapter = adapter
        loadData()
    }

    private fun loadData() {
//main scope, menggantikan GlobalScope, fix the error CalledFromWrongThreadException ketika consumer app di compile terlebih dahulu sebelum app provider
        MainScope().launch {
            val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
            if (cursor != null) {
                val list = ArrayList<GitUserFav>()
                cursor.apply {
                    while (moveToNext()) {
                        val gitUserFav = GitUserFav(
                            getInt(0),
                            getString(1),
                            getString(2),
                            getString(3),
                            getString(4),
                            getString(5)
                        )
                        list.addAll(listOf(gitUserFav))
                    }
                }
                Log.d("Main_Resolve list", list.size.toString())
                adapter.setDataForAdapter(list)
            }
        }
    }
}