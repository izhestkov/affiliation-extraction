package cermine

class AffiliationTokenizer : TextTokenizer<Token<AffiliationLabel>> {

    companion object {
        fun asciiTextToTokens(
            text: String,
            asciiIndices: List<Int>
        ): List<Token<AffiliationLabel>> {
            val DELIMITER_REGEX = "\\d+|\\W|_"
            val tokens: MutableList<Token<AffiliationLabel>> = ArrayList<Token<AffiliationLabel>>()
            val delimeterMatcher: Matcher = Pattern.compile(DELIMITER_REGEX).matcher(text)
            var lastEnd = 0
            while (delimeterMatcher.find()) {
                val currentStart: Int = delimeterMatcher.start()
                val currentEnd: Int = delimeterMatcher.end()

                val skippedText = text.substring(lastEnd, currentStart)
                if (skippedText != "") {
                    tokens.add(
                        Token<AffiliationLabel>(
                            skippedText,
                            asciiIndices[lastEnd], asciiIndices[currentStart]
                        )
                    )
                }

                val matchedText = text.substring(currentStart, currentEnd)
                // ignore whitespace
                if (!matchedText.matches("\\s")) {
                    tokens.add(
                        Token<AffiliationLabel>(
                            matchedText,
                            asciiIndices[currentStart], asciiIndices[currentEnd]
                        )
                    )
                }
                lastEnd = currentEnd
            }
            val skippedText = text.substring(lastEnd, text.length)
            if (skippedText != "") {
                tokens.add(
                    Token<AffiliationLabel>(
                        skippedText,
                        asciiIndices[lastEnd], asciiIndices[text.length]
                    )
                )
            }
            return tokens
        }

        fun getAsciiSubstringIndices(text: String): MutableList<Int> {
            val indices: MutableList<Int> = ArrayList()
            val asciiMatcher: Matcher = Pattern.compile("\\p{ASCII}").matcher(text)
            while (asciiMatcher.find()) {
                indices.add(asciiMatcher.start())
            }
            return indices
        }

        fun getSubstring(text: String, indices: List<Int>): String {
            val substringBuilder = StringBuilder()
            for (i in indices) {
                substringBuilder.append(text[i])
            }
            return substringBuilder.toString()
        }
    }

    fun tokenize(text: String): List<Token<AffiliationLabel>> {
        val asciiIndices = getAsciiSubstringIndices(text)
        val asciiText = getSubstring(text, asciiIndices)
        asciiIndices.add(text.length)
        return asciiTextToTokens(asciiText, asciiIndices)
    }
}
