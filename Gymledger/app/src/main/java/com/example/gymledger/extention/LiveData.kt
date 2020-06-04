package com.example.gymledger.extention

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by Costa van Elsas on 4-6-2020.
 */

/**
 * Allows the user to pass in a reference to a function in order to separate the
 * code from the observer.
 */
fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, f: (T) -> Unit) {
    this.observe(owner, Observer { t -> t?.let(f) })
}

/**
 * Also allows the user to pass in a reference to a function, however with this, the function is
 * able to not require the user to pass in a parameter.
 */
fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, f: () -> Unit) {
    this.observe(owner, Observer { f() })
}