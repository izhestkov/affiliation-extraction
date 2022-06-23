package model

data class Address(
    val street: Street? = null,
    val city: String,
    val postcode: String? = null,
    val country: String,
) {

    override fun toString(): String {
        val streetString = street?.toString() ?: ("street: \"null\" \n" + "home: \"null\"")

        return "$streetString \n" +
                "city: \"$city\" \n" +
                "postcode: \"$postcode\" \n" +
                "country: \"$country\""
    }
}

data class Street(
    val name: String,
    val number: String,
) {

    override fun toString(): String {
        return "street: \"$name\" \n" +
                "home: \"$number\""
    }
}
