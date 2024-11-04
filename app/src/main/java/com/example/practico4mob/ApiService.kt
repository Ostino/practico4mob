package com.example.practico4mob

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @GET("personas")
    suspend fun getContacts(): Response<List<Contact>>
    @POST("personas")
    suspend fun addContact(@Body contact: Contact): Response<Contact>
    @DELETE("personas/{id}")
    suspend fun deleteContact(@Path("id") contactId: Int): Response<Unit>
    @POST("phones")
    suspend fun addPhone(@Body phone: Phone): Response<Phone> // Cambiado a recibir un objeto Phone
    @POST("emails")
    suspend fun addEmail(@Body email: Email): Response<Email>
}
