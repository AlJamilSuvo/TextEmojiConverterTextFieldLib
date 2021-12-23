package al.jamil.suvo.textemojiconvertertextfieldlib


import java.util.regex.Pattern

object TextEmojis {


    private val textEmojiRegExes = ArrayList<String>()

    private const val GRINNING_EMOJI = "\uD83D\uDE00"  // 😀
    private const val SMILEY_EMOJI = "\uD83D\uDE03" // 😃
    private const val SUN_GLASS_EMOJI = "\uD83D\uDE0E" // 😎
    private const val CRY_EMOJI = "\uD83D\uDE25" // 😥
    private const val SMILE_EMOJI = "\uD83E\uDD72" // 🥲
    private const val DISAPPOINTED_EMOJI = "\uD83D\uDE41" //🙁
    private const val STUCK_OUT_TONGUE_EMOJI = "\uD83D\uDE1B" //😛
    private const val SLIGHTLY_SMILING_EMOJI = "\uD83D\uDE42" //🙂
    private const val OPEN_MOUTH_EMOJI = "\uD83D\uDE2E" //😮
    private const val RED_HEARTS_EMOJI = "❤"
    private const val EXPRESSIONLESS_EMOJI = "\uD83D\uDE10" //😐
    private const val CONFUSED_EMOJI = "\uD83D\uDE15" //😕

    private const val LIKE_EMOJI = "\uD83D\uDC4D" //👍
    private const val KISS_EMOJI = "\uD83D\uDE18" //😘
    private const val WINK_EMOJI = "\uD83D\uDE09" //😉
    private const val WINK_STUCK_OUT_TONGUE_EMOJI = "\uD83D\uDE1C" //😜
    private const val ANGRY_EMOJI = "\uD83D\uDE23" //😣


    private val textEmojiToGraphicalEmojiMap = HashMap<String, String>()

    private var isTextEmojiInitialized = false


    private fun enListTextEmoji(
        graphicalEmoji: String,
        textEmojis: Array<String>,
        textEmojiRegex: Array<String>
    ) {
        textEmojiRegExes.addAll(textEmojiRegex)
        for (textEmoji in textEmojis) {
            textEmojiToGraphicalEmojiMap[textEmoji] = graphicalEmoji
        }
    }


    private fun initializeTextEmojiData() {
        if (isTextEmojiInitialized) return
        enListTextEmoji(GRINNING_EMOJI, arrayOf(":D", ":-D"), arrayOf(":D", ":-D"))
        enListTextEmoji(SMILEY_EMOJI, arrayOf("=)"), arrayOf("=\\)"))
        enListTextEmoji(SUN_GLASS_EMOJI, arrayOf("8)"), arrayOf("8\\)"))
        enListTextEmoji(DISAPPOINTED_EMOJI, arrayOf(":(", ":-("), arrayOf(":\\(", ":-\\("))
        enListTextEmoji(CRY_EMOJI, arrayOf(":'("), arrayOf(":\\'-\\("))
        enListTextEmoji(SMILE_EMOJI, arrayOf(":')"), arrayOf(":\\'-\\)"))
        enListTextEmoji(
            STUCK_OUT_TONGUE_EMOJI,
            arrayOf(":p", ":P", ":-P", ":-p"),
            arrayOf(":p", ":P", ":-P", ":-p")
        )
        enListTextEmoji(SLIGHTLY_SMILING_EMOJI, arrayOf(":)", ":-)"), arrayOf(":\\)", ":-\\)"))
        enListTextEmoji(
            OPEN_MOUTH_EMOJI,
            arrayOf(":o", ":O", ":-o", ":-O"),
            arrayOf(":o", ":O", ":-o", ":-O")
        )
        enListTextEmoji(RED_HEARTS_EMOJI, arrayOf("<3"), arrayOf("<3"))
        enListTextEmoji(EXPRESSIONLESS_EMOJI, arrayOf("-_-"), arrayOf("-_-"))
        enListTextEmoji(CONFUSED_EMOJI, arrayOf(":/", ":-/"), arrayOf(":/", ":-/"))
        enListTextEmoji(LIKE_EMOJI, arrayOf("(y)", "(Y)"), arrayOf("\\(y\\)", "\\(Y\\)"))
        enListTextEmoji(KISS_EMOJI, arrayOf(":*", ":-*"), arrayOf(":\\*", ":-\\*"))
        enListTextEmoji(WINK_EMOJI, arrayOf(";)", ";-)"), arrayOf(";\\)", ";-\\)"))
        enListTextEmoji(
            WINK_STUCK_OUT_TONGUE_EMOJI,
            arrayOf(";p", ";P", ";-P", ";-p"),
            arrayOf(";p", ";P", ";-P", ";-p")
        )
        enListTextEmoji(ANGRY_EMOJI, arrayOf(">_<"), arrayOf(">_<"))
        isTextEmojiInitialized = true

    }


    private fun getTextEmojiRegexPattern(): Pattern {
        if (!isTextEmojiInitialized) initializeTextEmojiData()
        val emojiMatchingRegex = "(" + textEmojiRegExes.joinToString("|") + ")"
        return Pattern.compile("([^\\S]$emojiMatchingRegex)\\s|(^$emojiMatchingRegex\\s)")
    }

    private fun getOnlyTextEmojiRegexPattern(): Pattern {
        if (!isTextEmojiInitialized) initializeTextEmojiData()
        val emojiMatchingRegex = "(" + textEmojiRegExes.joinToString("|") + ")"
        return Pattern.compile("^$emojiMatchingRegex\$")
    }

    private fun convertTextEmojiToGraphicalEmoji(textEmoji: String): String? {
        if (!isTextEmojiInitialized) initializeTextEmojiData()
        return if (textEmoji in textEmojiToGraphicalEmojiMap.keys) textEmojiToGraphicalEmojiMap[textEmoji]
        else null
    }


    private val textEmojiRegexPatternInternal = getTextEmojiRegexPattern()
    private val onlyTextEmojiRegexPatternInternal = getOnlyTextEmojiRegexPattern()

    fun searchAndReplaceTextEmoji(
        textInput: String,
        currentCursorPosition: Int
    ): TextEmojiSearchAndReplaceResult {

        var changedTextInput = textInput
        var isTextChanged = false
        var newCursorPosition = currentCursorPosition

        var matcher = textEmojiRegexPatternInternal.matcher(textInput)
        while (matcher.find()) {

            val matchedTextEmojiWithWs = matcher.group()
            val matchStartIndex = matcher.start()
            val matchEndIndex = matcher.end()

            if (matchedTextEmojiWithWs.isNotEmpty()) {
                val precedingWs =
                    if (matchedTextEmojiWithWs[0].isWhitespace())
                        matchedTextEmojiWithWs[0].toString()
                    else
                        ""
                val succeedingWs = matchedTextEmojiWithWs.last()
                val textEmoji = matchedTextEmojiWithWs.trim()

                val graphicalEmoji = convertTextEmojiToGraphicalEmoji(textEmoji) ?: continue

                isTextChanged = true
                val graphicalEmojiReplacer = "$precedingWs$graphicalEmoji$succeedingWs"
                changedTextInput = changedTextInput.substring(0, matchStartIndex) +
                        graphicalEmojiReplacer +
                        changedTextInput.substring(matchEndIndex)


                val changeInTextInputLength =
                    graphicalEmojiReplacer.length - matchedTextEmojiWithWs.length
                if (newCursorPosition >= matchEndIndex) newCursorPosition += changeInTextInputLength
                matcher = textEmojiRegexPatternInternal.matcher(changedTextInput)
            }
        }

        if (newCursorPosition > changedTextInput.length) newCursorPosition = changedTextInput.length
        if (newCursorPosition < 0) newCursorPosition = 0
        return TextEmojiSearchAndReplaceResult(isTextChanged, changedTextInput, newCursorPosition)

    }


    fun searchAndReplaceTextEmoji(
        textInput: String
    ): String {
        var changedTextInput = textInput
        val matcher = onlyTextEmojiRegexPatternInternal.matcher(textInput)
        while (matcher.find()) {
            val textEmoji = matcher.group()
            val graphicalEmoji = convertTextEmojiToGraphicalEmoji(textEmoji) ?: continue
            changedTextInput = graphicalEmoji
        }
        return changedTextInput

    }


}


data class TextEmojiSearchAndReplaceResult(
    val isTextChanged: Boolean,
    val changedText: String,
    val newCursorPosition: Int
)


