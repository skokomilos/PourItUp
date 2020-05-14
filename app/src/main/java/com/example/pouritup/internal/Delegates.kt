package com.example.pouritup.internal

import kotlinx.coroutines.*

//for moore info visit : https://medium.com/@sampsonjoliver/lazy-evaluated-coroutines-in-kotlin-bf5be004233
fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy{
        GlobalScope.async(start = CoroutineStart.LAZY){
            block.invoke(this)
        }
    }
}