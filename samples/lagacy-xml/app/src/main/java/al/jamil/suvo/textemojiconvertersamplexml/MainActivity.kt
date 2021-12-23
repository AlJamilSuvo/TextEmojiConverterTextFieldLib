package al.jamil.suvo.textemojiconvertersamplexml

import al.jamil.suvo.textemojiconvertertextfieldlib.EditTextTextEmojiConverter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editText = findViewById<EditText>(R.id.editText)
        EditTextTextEmojiConverter(editText)
    }
}