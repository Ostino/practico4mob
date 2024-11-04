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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practico4mob.databinding.ActivityFormularioContactoBinding

class FormularioContactoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormularioContactoBinding
    private val viewModel: ContactoViewModel by viewModels()
    private val listaTelefonos = mutableListOf<Phone>() // Lista de teléfonos
    private val listaEmails = mutableListOf<Email>() // Lista de emails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormularioContactoBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                if (binding.etTelefono.text.toString().isNotBlank()) {
                    // Agregar teléfono a la lista al cambiar la etiqueta
                    val telefono = binding.etTelefono.text.toString()
                    val etiquetaTelefono = etiquetasTelefono[position]
                    listaTelefonos.add(Phone(0, 0, telefono, etiquetaTelefono))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.spinnerEtiquetaEmail.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (binding.etEmail.text.toString().isNotBlank()) {
                    // Agregar email a la lista al cambiar la etiqueta
                    val email = binding.etEmail.text.toString()
                    val etiquetaEmail = etiquetasEmail[position]
                    listaEmails.add(Email(0, 0, email, etiquetaEmail))
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

        // Agregar el último teléfono ingresado antes de guardar
        if (binding.etTelefono.text.toString().isNotBlank()) {
            val telefono = binding.etTelefono.text.toString()
            val etiquetaTelefono = binding.spinnerEtiquetaTelefono.selectedItem.toString()
            listaTelefonos.add(Phone(0, 0, telefono, etiquetaTelefono))
        }

        // Agregar el último email ingresado antes de guardar
        if (binding.etEmail.text.toString().isNotBlank()) {
            val email = binding.etEmail.text.toString()
            val etiquetaEmail = binding.spinnerEtiquetaEmail.selectedItem.toString()
            listaEmails.add(Email(0, 0, email, etiquetaEmail))
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

        // Almacena el contacto en el ViewModel
        viewModel.agregarContacto(contact).observe(this) { success ->
            if (success) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                // Manejar error al guardar
            }
        }
    }
}
