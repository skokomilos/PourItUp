package com.example.pouritup.screens.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

/*
    When we want to use coroutines in fragment, and coroutines have to have scope from reason that we arent gonna get any NPE when corotunes finnishes its job only after the fragment has been already destroyed
   6*13*
*/
abstract class ScopedFragment: Fragment(), CoroutineScope {
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        //meaning of this is that job is going to run on the main Dispatcher in fact on the main thread
        //!! everything that is execude on UI must run on Main Thread, otherwise we can not change state and data on UI
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    //whenever fragment is destroyed we are canceling job here by this we preventing any exception or crash
    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    abstract fun bindUI() : Job
}