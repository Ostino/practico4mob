package com.example.practico4mob

data class Phone(
    val id: Int,
    var persona_id: Int,
    val label: String,
    val number: String
)
data class Email(
    val id: Int,
    var persona_id: Int,
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
    val phones: List<Phone> = listOf(),
    val emails: List<Email> = listOf()
)

