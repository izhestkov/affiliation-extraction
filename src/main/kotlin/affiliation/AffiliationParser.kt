package affiliation

import model.Address
import model.Affiliation
import model.Street

class AffiliationParser(affiliation: String) {

    private var affiliationList = affiliation
        .cleanse()
        .split()
        .toMutableList()

    fun parse(): Affiliation {
        val country = affiliationList
            .lastOrNull()
            ?.extractCountry()
            ?.also { affiliationList.removeLastOrNull() }

        val cityAndPostcode = affiliationList
            .lastOrNull()
            ?.extractCityAndPostcode()
            .also { affiliationList.removeLastOrNull() }

        val street = affiliationList
            .lastOrNull()
            ?.extractStreet()
            ?.also { affiliationList.removeLastOrNull() }

        val organization = affiliationList
            .lastOrNull()
            ?.also { affiliationList.removeLastOrNull() }

        val institution = affiliationList
            .lastOrNull()
            ?.also { affiliationList.removeLastOrNull() }

        return Affiliation(
            institution = institution,
            organization = organization ?: "",
            address = Address(
                street = street?.let {
                    Street(
                        name = street.second,
                        number = street.first,
                    )
                },
                city = cityAndPostcode?.first ?: "",
                postcode = cityAndPostcode?.second,
                country = country ?: ""
            ),
            email = null
        )
    }

    private fun String.cleanse() = replace("\\s+".toRegex(), " ")

    private fun String.split() = this
        .split(",")
        .map { it.trim() }

    private fun String.extractCountry(): String? {
        return takeIf {
            it.uppercase().contains("RUSSIA")
        }
    }

    private fun String.extractCityAndPostcode(): Pair<String, String?> {
        return if (AffiliationRegex.cityAndPostcodeRegex.matches(this)) {
            substringBefore(" ") to substringAfter(" ")
        } else {
            this to null
        }
    }

    private fun String.extractStreet(): Pair<String, String>? {
        return if (AffiliationRegex.streetRegex.matches(this)) {
            substringBefore(" ") to substringAfter(" ")
        } else {
            null
        }
    }
}
