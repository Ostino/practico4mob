package com.example.practico4mob

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ContactoViewModel : ViewModel() {
    private val _contactos = MutableLiveData<List<Contact>>().apply { value = listOf() }
    val contactos: LiveData<List<Contact>> get() = _contactos

    private val apiService = RetrofitInstance.api // Asegúrate de que esta instancia sea correcta

    fun fetchContacts() {
        viewModelScope.launch {
            try {
                val response = apiService.getContacts()

                if (response.isSuccessful) {
                    response.body()?.let { contacts ->
                        _contactos.value = contacts
                        Log.d("ContactoViewModel", "Contactos obtenidos: $contacts") // Verifica que los datos están llegando aquí
                    } ?: Log.e("ContactoViewModel", "El cuerpo de la respuesta es nulo")
                } else {
                    Log.e("ContactoViewModel", "Error al obtener contactos: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ContactoViewModel", "Error en la obtención de contactos", e)
            }
        }
    }

    fun agregarContacto(contact: Contact): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        // Agrega un log antes de hacer la llamada a la API
        Log.d("ContactoViewModel", "Agregando contacto: $contact")

        apiService.addContact(contact).enqueue(object : Callback<ResponseType> {
            override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>) {
                if (response.isSuccessful) {
                    result.value = true
                    Log.d("ContactoViewModel", "Contacto guardado correctamente")
                    // Después de agregar el contacto, puedes llamar a fetchContacts() para refrescar la lista
                    fetchContacts()
                } else {
                    result.value = false
                    Log.e("ContactoViewModel", "Error al guardar contacto: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ResponseType>, t: Throwable) {
                result.value = false
                Log.e("ContactoViewModel", "Error en la llamada a la API: ${t.message}")
            }
        })

        return result
    }

}
