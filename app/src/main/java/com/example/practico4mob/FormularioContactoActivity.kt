package com.example.practico4mob

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practico4mob.databinding.ActivityFormularioContactoBinding
class FormularioContactoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormularioContactoBinding
    private val viewModel: ContactoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormularioContactoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración del Spinner para etiquetas de teléfono
        val etiquetasTelefono = listOf("Casa", "Trabajo", "Celular", "Personalizada")
        val adapterTelefono = ArrayAdapter(this, R.layout.simple_spinner_item, etiquetasTelefono)
        adapterTelefono.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerEtiquetaTelefono.adapter = adapterTelefono

        // Configuración del Spinner para etiquetas de email (suponiendo que también tienes este Spinner)
        val etiquetasEmail = listOf("Persona", "Trabajo", "Universidad", "Personalizada")
        val adapterEmail = ArrayAdapter(this, R.layout.simple_spinner_item, etiquetasEmail)
        adapterEmail.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerEtiquetaEmail.adapter = adapterEmail

        binding.btnGuardar.setOnClickListener {
            guardarContacto()
        }
    }

    private fun guardarContacto() {
        val nombre = binding.etNombre.text.toString()
        val apellido = binding.etApellido.text.toString()
        val compania = binding.etCompania.text.toString()
        val direccion = binding.etDireccion.text.toString()
        val ciudad = binding.etCiudad.text.toString()
        val estado = binding.etEstado.text.toString()

        val listaTelefonos = mutableListOf<Phone>()
        val listaEmails = mutableListOf<Email>()

        // Obtener los datos del teléfono
        val telefono = binding.etTelefono.text.toString() // Asegúrate de que este EditText esté en tu layout
        val etiquetaTelefono = binding.spinnerEtiquetaTelefono.selectedItem.toString() // Obtener la etiqueta seleccionada
        Log.d("FormularioContacto", "Teléfono: $telefono, Etiqueta Teléfono: $etiquetaTelefono") // Log de teléfono
        if (telefono.isNotBlank()) {
            listaTelefonos.add(Phone(0, etiquetaTelefono, telefono)) // Agregar a la lista solo si no está vacío
            Log.d("FormularioContacto", "Lista de Teléfonos: $listaTelefonos") // Log de lista de teléfonos
        } else {
            Log.w("FormularioContacto", "Teléfono está vacío, no se agregará a la lista de teléfonos")
        }

        // Obtener los datos del email
        val email = binding.etEmail.text.toString() // Asegúrate de que este EditText esté en tu layout
        val etiquetaEmail = binding.spinnerEtiquetaEmail.selectedItem.toString() // Obtener la etiqueta seleccionada
        Log.d("FormularioContacto", "Email: $email, Etiqueta Email: $etiquetaEmail") // Log de email
        if (email.isNotBlank()) {
            listaEmails.add(Email(0, etiquetaEmail, email)) // Agregar a la lista solo si no está vacío
            Log.d("FormularioContacto", "Lista de Emails: $listaEmails") // Log de lista de emails
        } else {
            Log.w("FormularioContacto", "Email está vacío, no se agregará a la lista de emails")
        }

        // Si no tienes la URL de la imagen en ese momento, podrías dejarla como una cadena vacía o como nula
        val urlImagen = "" // O establece esto según la lógica de tu aplicación

        binding.btnGuardar.setOnClickListener {
            val contact = Contact(
                id = 0,
                name = nombre,
                last_name = apellido,
                company = compania,
                address = direccion,
                city = ciudad,
                state = estado,
                profile_picture = urlImagen,
                phones = listaTelefonos,
                emails = listaEmails
            )

            viewModel.agregarContacto(contact).observe(this) { success ->
                if (success) {
                    // Volver a la lista de contactos
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Log.e("FormularioContacto", "Error al guardar contacto")
                }
            }
        }
    }
}