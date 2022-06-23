package affiliation

object AffiliationRegex {

    val cityAndPostcodeRegex = Regex("^[a-zA-Z]+?\\s\\d{6}$")
    val streetRegex = Regex("^\\d+?\\s[a-zA-Z .]+$")
    val emailRegex = Regex("^[a-zA-Z\\d_.+-]+@[a-zA-Z\\d-]+\\.[a-zA-Z\\d-.]+$")
}
