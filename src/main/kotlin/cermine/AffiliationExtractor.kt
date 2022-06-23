package cermine

class AffiliationExtractor : FeatureExtractor<DocumentAffiliation> {

    private var binaryFeatures: MutableList<BinaryTokenFeatureCalculator>
    private var keywordFeatures: List<KeywordFeatureCalculator<Token<AffiliationLabel>>>
    private var wordFeature: WordFeatureCalculator

    init {
        binaryFeatures = listOf(
            IsUpperCaseFeature(),
            IsAllUpperCaseFeature(),
            IsAllLowerCaseFeature()
        )
        keywordFeatures = listOf(
            AffiliationDictionaryFeature(
                "KeywordAddress",
                "address_keywords.txt",
                false
            ),
            AffiliationDictionaryFeature(
                "KeywordCountry",
                "metadata/affiliation/features/countries.txt",
                true
            ),
            AffiliationDictionaryFeature(
                "KeywordInstitution",
                "metadata/affiliation/features/institution_keywords.txt",
                false
            )
        )
        wordFeature = WordFeatureCalculator(listOf(IsNumberFeature(), false)
    }

    override fun calculateFeatures(affiliation: DocumentAffiliation) {
        val tokens: List<Token<AffiliationLabel>> = affiliation.getTokens()
        for (token in tokens) {
            for (binaryFeatureCalculator in binaryFeatures) {
                if (binaryFeatureCalculator.calculateFeaturePredicate(token, affiliation)) {
                    token.addFeature(binaryFeatureCalculator.getFeatureName())
                }
            }
            val wordFeatureString: String = wordFeature.calculateFeatureValue(token, affiliation)
            if (wordFeatureString != null) {
                token.addFeature(wordFeatureString)
            }
        }
        for (dictionaryFeatureCalculator in keywordFeatures) {
            dictionaryFeatureCalculator.calculateDictionaryFeatures(tokens)
        }
    }
}
