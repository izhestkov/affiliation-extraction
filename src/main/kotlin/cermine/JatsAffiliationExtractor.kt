package cermine

class JatsAffiliationExtractor :
    NLMParsableStringExtractor<AffiliationLabel, Token<AffiliationLabel>, DocumentAffiliation>() {

    private val TOKENIZER = AffiliationTokenizer()
    private val TAGS_LABEL_MAP = HashMap<String, AffiliationLabel>()

    init {
        TAGS_LABEL_MAP["addr-line"] = AffiliationLabel.ADDRESS
        TAGS_LABEL_MAP["institution"] = AffiliationLabel.INST
        TAGS_LABEL_MAP["country"] = AffiliationLabel.COUNTRY
        TAGS_LABEL_MAP["author"] = AffiliationLabel.AUTH
        TAGS_LABEL_MAP["text"] = AffiliationLabel.TEXT
    }

    protected val tagLabelMap = TAGS_LABEL_MAP

    protected fun createParsableString() = DocumentAffiliation("")

    protected fun createParsableString(text: String?): DocumentAffiliation {
        val instance = DocumentAffiliation(text)
        instance.setTokens(TOKENIZER.tokenize(instance.getRawText()))
        return instance
    }
}