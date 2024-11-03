package com.example.practico4mob

data class Phone(
    val id: Int,
    val label: String,
    val number: String
)
data class Email(
    val id: Int,
    val label: String,
    val email: String
)
data class Contact(
    val id: Int,
    val name: String,
    val last_name: String,
    val company: String,
    val address: String,
    val city: String,
    val state: String,
    val profile_picture: String,
    val phones: List<Phone>, // Asegúrate de que esto sea una lista
    val emails: List<Email>  // Asegúrate de que esto sea una lista
)
data class ResponseType(
    val success: Boolean,
    val message: String,
    val data: Contact? // Puedes incluir más campos según tu API
)

