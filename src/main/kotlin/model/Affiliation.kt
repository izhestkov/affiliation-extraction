package model

data class Affiliation(
    val institution: String? = null,
    val organization: String,
    val address: Address,
    val email: String? = null,
) {

    override fun toString(): String {
        return "institution: \"$institution\" \n" +
                "organization: \"$organization\" \n" +
                "$address \n" +
                "email: \"$email\""
    }
}
