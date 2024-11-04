package com.example.practico4mob

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
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

        Log.d("FormularioContactoActivity", "Activity creada")

        setupSpinners()

        binding.btnGuardar.setOnClickListener {
            guardarContacto()
        }
    }

    private fun setupSpinners() {
        val etiquetasTelefono = listOf("Casa", "Trabajo", "Celular", "Personalizada")
        val etiquetasEmail = listOf("Persona", "Trabajo", "Universidad", "Personalizada")

        val adapterTelefono = ArrayAdapter(this, android.R.layout.simple_spinner_item, etiquetasTelefono)
        val adapterEmail = ArrayAdapter(this, android.R.layout.simple_spinner_item, etiquetasEmail)

        adapterTelefono.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterEmail.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerEtiquetaTelefono.adapter = adapterTelefono
        binding.spinnerEtiquetaEmail.adapter = adapterEmail

        binding.spinnerEtiquetaTelefono.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                Log.d("FormularioContactoActivity", "Etiqueta teléfono seleccionada: ${etiquetasTelefono[position]}")
                if (position != 0) {
                    binding.etTelefono.setText("") // Limpia el campo de teléfono
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.spinnerEtiquetaEmail.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                Log.d("FormularioContactoActivity", "Etiqueta email seleccionada: ${etiquetasEmail[position]}")
                if (position != 0) {
                    binding.etEmail.setText("") // Limpia el campo de email
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun guardarContacto() {
        val nombre = binding.etNombre.text.toString()
        val apellido = binding.etApellido.text.toString()
        val compania = binding.etCompania.text.toString()
        val direccion = binding.etDireccion.text.toString()
        val ciudad = binding.etCiudad.text.toString()
        val estado = binding.etEstado.text.toString()

        Log.d("FormularioContactoActivity", "Datos del contacto: Nombre: $nombre, Apellido: $apellido, Compañía: $compania, Dirección: $direccion, Ciudad: $ciudad, Estado: $estado")

        val listaTelefonos = mutableListOf<Phone>()
        val listaEmails = mutableListOf<Email>()

        val telefono = binding.etTelefono.text.toString()
        val etiquetaTelefono = binding.spinnerEtiquetaTelefono.selectedItem.toString()
        val email = binding.etEmail.text.toString()
        val etiquetaEmail = binding.spinnerEtiquetaEmail.selectedItem.toString()

        // Validación y adición de teléfonos
        if (telefono.isNotBlank()) {
            listaTelefonos.add(Phone(0, 0, telefono, etiquetaTelefono)) // Agrega ID temporal
            Log.d("FormularioContactoActivity", "Teléfono agregado: $telefono con etiqueta $etiquetaTelefono")
        } else {
            Log.w("FormularioContactoActivity", "Teléfono está vacío, no se agregará a la lista de teléfonos")
        }

        // Validación y adición de emails
        if (email.isNotBlank()) {
            listaEmails.add(Email(0, 0, email, etiquetaEmail)) // Agrega ID temporal
            Log.d("FormularioContactoActivity", "Email agregado: $email con etiqueta $etiquetaEmail")
        } else {
            Log.w("FormularioContactoActivity", "Email está vacío, no se agregará a la lista de emails")
        }

        val urlImagen = "" // Asigna la URL de la imagen según tu lógica

        val contact = Contact(
            id = 0, // Asigna un ID temporal
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

        Log.d("FormularioContactoActivity", "Contacto a guardar: $contact")

        // Almacena el contacto en el ViewModel
        viewModel.agregarContacto(contact).observe(this) { success ->
            if (success) {
                Log.d("FormularioContactoActivity", "Contacto guardado exitosamente, redirigiendo a MainActivity")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Log.e("FormularioContactoActivity", "Error al guardar contacto")
            }
        }
    }
}