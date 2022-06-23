package cermine

class DocumentAffiliation : ParsableString<Token<AffiliationLabel?>?> {

    private var tokens: List<Token<AffiliationLabel>>

    val organization: String?
        get() {
            for (token in tokens) {
                if (token.getLabel().equals(AffiliationLabel.INST)) {
                    return Normalizer.normalize(token.getText(), Normalizer.Form.NFC)
                }
            }
            return null
        }

    val country: String?
        get() {
            for (token in tokens) {
                if (token.getLabel().equals(AffiliationLabel.COUNTRY)) {
                    return token.getText().replaceAll("[^ a-zA-Z]$", "")
                }
            }
            return null
        }

    fun getTokens(): List<Token<AffiliationLabel>?>? {
        return tokens
    }

    fun setTokens(tokens: List<Token<AffiliationLabel?>?>) {
        this.tokens = tokens
    }

    fun addToken(token: Token<AffiliationLabel?>?) {
        tokens.add(token)
    }

    fun mergeTokens() {
        if (tokens == null || tokens!!.isEmpty()) {
            return
        }
        var actToken: Token<AffiliationLabel>? = null
        val newTokens: MutableList<Token<AffiliationLabel>?> = ArrayList<Token<AffiliationLabel>?>()
        for (token in tokens) {
            if (actToken == null) {
                actToken = Token<AffiliationLabel>(
                    token.getText(),
                    token.getStartIndex(),
                    token.getEndIndex(),
                    token.getLabel()
                )
            } else if (actToken.getLabel().equals(token.getLabel())) {
                actToken.setEndIndex(token.getEndIndex())
            } else {
                newTokens.add(actToken)
                actToken = Token<AffiliationLabel>(
                    token.getText(),
                    token.getStartIndex(),
                    token.getEndIndex(),
                    token.getLabel()
                )
            }
        }
        newTokens.add(actToken)
        for (token in newTokens) {
            val i = newTokens.indexOf(token)
            if (i + 1 == newTokens.size) {
                token.setEndIndex(rawText.length)
            } else {
                token.setEndIndex(newTokens[i + 1].getStartIndex())
            }
            token.setText(rawText.substring(token.getStartIndex(), token.getEndIndex()))
        }
        tokens = newTokens
    }
}
