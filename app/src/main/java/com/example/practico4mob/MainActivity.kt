package com.example.practico4mob

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practico4mob.databinding.ActivityMainBinding
import com.example.practico4mob.ContactAdapter as ContactAdapter
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ContactoViewModel
    private lateinit var adapter: ContactAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa el ViewModel
        viewModel = ViewModelProvider(this).get(ContactoViewModel::class.java)

        // Inicializa el adaptador y el RecyclerView
        adapter = ContactAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Configura el listener para cuando se hace clic en un contacto
        adapter.setOnItemClickListener(object : ContactAdapter.OnItemClickListener {
            override fun onItemClick(contact: Contact) {
                showOptionsDialog(contact)
            }
        })

        // Observa la lista de contactos desde el ViewModel
        viewModel.contactos.observe(this) { contactos ->
            Log.d("MainActivity", "Lista de contactos observada: $contactos")
            adapter.setContacts(contactos)
        }

        val btnNuevoContacto: Button = binding.btnNuevoContacto
        btnNuevoContacto.setOnClickListener {
            val intent = Intent(this, FormularioContactoActivity::class.java)
            startActivity(intent)
        }

        // Cargar los contactos inicialmente
        viewModel.fetchContacts()
    }

    private fun showOptionsDialog(contact: Contact) {
        val options = arrayOf("Eliminar")

        // Muestra un cuadro de diálogo con las opciones
        AlertDialog.Builder(this)
            .setTitle("Selecciona una opción")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> confirmDelete(contact)
                }
            }
            .show()
    }

    private fun confirmDelete(contact: Contact) {
        // Muestra un diálogo de confirmación
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Eliminar contacto")
            .setMessage("¿Estás seguro de que deseas eliminar este contacto?")
            .setPositiveButton("Sí") { _, _ ->
                viewModel.deleteContact(contact.id) // Llama a la función de eliminación en el ViewModel
                viewModel.fetchContacts() // Recargar la lista de contactos
            }
            .setNegativeButton("No", null)
            .create()
        alertDialog.show()
    }
}