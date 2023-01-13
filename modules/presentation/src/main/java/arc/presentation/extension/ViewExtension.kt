package arc.presentation.extension

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

/**
 * @author jimlyas
 * @since 12 Jan 2023
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */

/**
 * Function to display [View]
 */
fun View.visible() {
    visibility = View.VISIBLE
}

/**
 * Function to hide [View]
 */
fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * Function to remove [View]
 */
fun View.gone() {
    visibility = View.GONE
}

/**
 * Function to enable [View]
 */
fun View.enable() {
    isEnabled = true
}

/**
 * Function to disable [View]
 */
fun View.disable() {
    isEnabled = false
}

/**
 * Function to define listener when text changed on [EditText]
 * @param doWhenChange action to do when the text changed
 */
fun EditText.onTextChanged(doWhenChange: (text: String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(currentText: Editable?) {
            doWhenChange(currentText.toString())
        }

        override fun beforeTextChanged(currentText: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // Intended to be empty
        }

        override fun onTextChanged(currentText: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // Intended to be empty
        }
    })
}