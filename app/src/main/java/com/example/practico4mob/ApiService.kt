package com.example.practico4mob

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("personas")
    suspend fun getContacts(): Response<List<Contact>>
    @POST("personas")
    fun addContact(@Body contact: Contact): Call<ResponseType>
}
