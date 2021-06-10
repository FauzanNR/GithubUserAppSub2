package com.app.githubuserappsub2.viewmodel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.net.ConnectivityManager.EXTRA_NO_CONNECTIVITY
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.githubuserappsub2.R
import com.app.githubuserappsub2.adapter.RecyclerViewAdapter

abstract class FragmentModel : Fragment() {
    protected var isConnected = true
    protected lateinit var progressBar: ProgressBar
    protected lateinit var textViewInfo: TextView

    protected val recyclerViewAdapter: RecyclerViewAdapter by lazy {
        RecyclerViewAdapter().apply {
            notifyDataSetChanged()
        }
    }
    protected val setViewModel: DataViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DataViewModel::class.java)
    }
    protected val connectionBroadcastReceiver: BroadcastReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

                val isNotConnected = intent?.getBooleanExtra(EXTRA_NO_CONNECTIVITY, false)
                if (isNotConnected == false) {
                    val action = intent.action
                    onConnected()
                } else {
                    onDisconnected()
                    Toast.makeText(
                        this@FragmentModel.context,
                        R.string.no_connection,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    abstract fun onDisconnected() //block process
    abstract fun onConnected() //process
    abstract fun queryApi(q: String)//do when connected


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().registerReceiver(
            connectionBroadcastReceiver,
            IntentFilter(CONNECTIVITY_ACTION)
        )
    }

    override fun onResume() {
        super.onResume()
        recyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(connectionBroadcastReceiver)
    }
}