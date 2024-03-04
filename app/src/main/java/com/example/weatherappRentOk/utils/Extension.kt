package com.example.weatherappRentOk.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

inline fun <reified T : Activity> Activity.startActivityWithBundle(bundle: () -> Bundle ) {
    val intent =Intent(this, T::class.java)
    intent.putExtras(bundle())
    startActivity(intent)
}
