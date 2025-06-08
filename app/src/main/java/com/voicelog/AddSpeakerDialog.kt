package com.voicelog

import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout

class AddSpeakerDialog(
    context: Context,
    private val onAdd: (name: String, phone: String?, isSelf: Boolean) -> Unit
) {
    private val dialog: AlertDialog

    init {
        val nameInput = EditText(context).apply { hint = "Name" }
        val phoneInput = EditText(context).apply {
            hint = "Phone (optional)"
            inputType = InputType.TYPE_CLASS_PHONE
        }

        val selfCheck = android.widget.CheckBox(context).apply { text = "This is me" }

        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 16, 32, 0)
            addView(nameInput)
            addView(phoneInput)
            addView(selfCheck)
        }

        dialog = AlertDialog.Builder(context)
            .setTitle("New Speaker")
            .setView(layout)
            .setPositiveButton("Save") { _, _ ->
                val name = nameInput.text.toString()
                val phone = phoneInput.text.toString().takeIf { it.isNotBlank() }
                onAdd(name, phone, selfCheck.isChecked)
            }
            .setNegativeButton("Cancel", null)
            .create()
    }

    fun show() {
        dialog.show()
    }
}
