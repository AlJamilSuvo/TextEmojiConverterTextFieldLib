package al.jamil.suvo.textemojiconvertersamplecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import al.jamil.suvo.textemojiconvertersamplecompose.ui.theme.TextEmojiConverterSampleComposeTheme
import al.jamil.suvo.textemojiconvertertextfieldlib.TextEmojiConverterTextField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextEmojiConverterSampleComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    SampleTextEmojiConverterTextFieldScreen()
                }
            }
        }
    }
}

@Composable
fun SampleTextEmojiConverterTextFieldScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SampleTextEmojiConverterTextField()
    }
}

@Composable
fun SampleTextEmojiConverterTextField() {
    val textFieldValue = remember {
        mutableStateOf("")
    }
    TextEmojiConverterTextField(value = textFieldValue.value, onValueChange = {
        textFieldValue.value =it
    })

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TextEmojiConverterSampleComposeTheme {
        SampleTextEmojiConverterTextFieldScreen()
    }
}