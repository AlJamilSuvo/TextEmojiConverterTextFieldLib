package al.jamil.suvo.textemojiconvertertextfieldlib

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


class EditTextTextEmojiConverter(private val editText: EditText) {

    init {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val cursorPosition: Int = editText.selectionStart
                val replaceResult: TextEmojiSearchAndReplaceResult =
                    TextEmojis.searchAndReplaceTextEmoji(s.toString(), cursorPosition)

                if (replaceResult.isTextChanged) {
                    editText.setText(replaceResult.changedText)
                    editText.setSelection(replaceResult.newCursorPosition)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }


}
