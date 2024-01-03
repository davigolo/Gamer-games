package com.example.gamergames

import android.content.Context
import android.text.Editable
import android.widget.ImageView
import android.widget.Toast

import com.squareup.picasso.Picasso

/**Load images from url*/
fun ImageView.loadUrl(url: String) {
    if (url.isNotEmpty()) {
        Picasso.with(context).load(url).into(this)
    }
}
fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

/**Shows a toast with HTTP petition result if it was an exception*/
fun showExceptionText(exceptionMessage: String, context: Context?) {
    Toast.makeText(context, exceptionMessage, Toast.LENGTH_SHORT).show()
}
/**Shows a toast with HTTP petition result if it was an error*/
fun showErrorText(errorMessage: String, context: Context?) {
    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
}
