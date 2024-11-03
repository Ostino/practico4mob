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

        // Observa la lista de contactos desde el ViewModel
        viewModel.contactos.observe(this) { contactos ->
            Log.d("MainActivity", "Lista de contactos observada: $contactos")
            adapter.setContacts(contactos) // Actualiza la lista de contactos en el adaptador
        }
        val btnNuevoContacto: Button = binding.btnNuevoContacto
        btnNuevoContacto.setOnClickListener {
            val intent = Intent(this, FormularioContactoActivity::class.java)
            startActivity(intent)
        }

        // Cargar los contactos inicialmente
        viewModel.fetchContacts()
    }
}