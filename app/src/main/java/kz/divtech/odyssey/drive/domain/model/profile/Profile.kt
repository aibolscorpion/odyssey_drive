package kz.divtech.odyssey.drive.domain.model.profile

import java.lang.StringBuilder

data class Profile(
    val name: String,
    val surname: String,
    val patronymic: String,
    val vehicleName: String,
    val stateNumber: String,
    val seatCount: Int)

fun Profile.getFullNameWithInitials(): String{
    val fullName = StringBuilder("")

    if(this.surname.isNotBlank()) {
        fullName.append("${this.surname} ")
    }

    if(this.name.isNotBlank()){
        val name = this.name.first().uppercase()
        fullName.append("$name. ")
    }

    if(this.patronymic.isNotBlank()){
        val patronymic = this.patronymic.first().uppercase()
        fullName.append("$patronymic.")
    }
    return fullName.toString()
}
val emptyProfile: Profile =
    Profile("", "", "", "", "", 0)
