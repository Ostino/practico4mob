package com.example.practico4mob

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practico4mob.databinding.ActivityMainBinding
import com.example.practico4mob.ContactAdapter as ContactAdapter
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ContactoViewModel
    private lateinit var adapter: ContactAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ContactoViewModel::class.java)

        adapter = ContactAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : ContactAdapter.OnItemClickListener {
            override fun onItemClick(contact: Contact) {
                showOptionsDialog(contact)
            }
        })

        viewModel.contactos.observe(this) { contactos ->
            Log.d("MainActivity", "Lista de contactos observada: $contactos")
            adapter.setContacts(contactos)
        }

        val btnNuevoContacto: Button = binding.btnNuevoContacto
        btnNuevoContacto.setOnClickListener {
            val intent = Intent(this, FormularioContactoActivity::class.java)
            startActivity(intent)
        }
        viewModel.fetchContacts()
    }

    private fun showOptionsDialog(contact: Contact) {
        val options = arrayOf("Eliminar")
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
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Eliminar contacto")
            .setMessage("¿Estás seguro de que deseas eliminar este contacto?")
            .setPositiveButton("Sí") { _, _ ->
                viewModel.deleteContact(contact.id)
                viewModel.fetchContacts()
            }
            .setNegativeButton("No", null)
            .create()
        alertDialog.show()
    }
}