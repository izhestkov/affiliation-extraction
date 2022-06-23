package cermine

class MetadataToNLMConverter : ModelToModelConverter<DocumentMetadata?, Element?> {

    @Throws(TransformationException::class)
    fun convertAffiliation(affiliation: DocumentAffiliation): Element {
        val aff = Element(TAG_AFFILIATION)
        aff.setAttribute(ATTR_ID, "aff" + affiliation.getId())
        addElement(aff, TAG_LABEL, affiliation.getId())
        for (token in affiliation.getTokens()) {
            when (token.getLabel()) {
                TEXT -> aff.addContent(token.getText())
                COUN -> {
                    val finder = CountryISOCodeFinder()
                    val isoCode: String = finder.getCountryISOCode(token.getText())
                    if (isoCode == null) {
                        addElement(aff, TAG_COUNTRY, token.getText())
                    } else {
                        addElement(aff, TAG_COUNTRY, token.getText(), ATTR_COUNTRY, isoCode)
                    }
                }
                INST -> addElement(aff, TAG_INSTITUTION, token.getText())
                ADDR -> addElement(aff, TAG_ADDRESS, token.getText())
                else -> {}
            }
        }
        return aff
    }

    @Throws(TransformationException::class)
    fun convertAuthor(author: DocumentAuthor): Element {
        val contributor = Element(TAG_CONTRIB)
        contributor.setAttribute(ATTR_CONTRIB_TYPE, ATTR_VALUE_CONTRIB_AUTHOR)
        addElement(contributor, TAG_STRING_NAME, author.getName())
        for (email in author.getEmails()) {
            addElement(contributor, TAG_EMAIL, email)
        }
        for (aff in author.getAffiliations()) {
            val affRef: Element = createElement(TAG_XREF, aff.getId())
            affRef.setAttribute(ATTR_REF_TYPE, ATTR_VALUE_REF_TYPE_AFF)
            affRef.setAttribute(ATTR_RID, "aff" + aff.getId())
            contributor.addContent(affRef)
        }
        return contributor
    }

    private fun addElement(parent: Element, names: Array<String>, text: String?) {
        if (text != null && names.size != 0) {
            var prev: Element = parent
            for (name in names) {
                val element = Element(name)
                prev.addContent(element)
                prev = element
            }
            prev.setText(XMLTools.removeInvalidXMLChars(text))
        }
    }

    private fun createElement(name: String, text: String): Element {
        val element = Element(name)
        element.setText(XMLTools.removeInvalidXMLChars(text))
        return element
    }
}
