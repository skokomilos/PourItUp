package com.example.pouritup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pouritup.data.network.CoctailApiService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
/*
1. Navigacija iz listview recimo, mozemo raditi i preko item adaptera (onclicklistener ubacim u xml itema) a ne samo preko fragmenta
Pa navigaciju radim iz adaptera as listview do detailview.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
