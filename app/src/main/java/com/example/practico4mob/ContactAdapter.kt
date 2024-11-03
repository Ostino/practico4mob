package com.example.practico4mob

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var contacts = listOf<Contact>()

    fun setContacts(newContacts: List<Contact>) {
        contacts = newContacts
        notifyDataSetChanged() // Asegúrate de que esto esté llamando a notifyDataSetChanged
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.textViewName.text = "${contact.name} ${contact.last_name}"
        holder.textViewEmail.text = contact.emails.joinToString { it.email }

        // Cargar la imagen de perfil desde la URL usando Glide
        Glide.with(holder.itemView.context)
            .load(contact.profile_picture) // Asegúrate de que profile_picture contenga la URL
            // Imagen de placeholder (opcional)
            .into(holder.imageViewProfile)
    }

    override fun getItemCount() = contacts.size

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewEmail: TextView = itemView.findViewById(R.id.textViewEmail)
        val imageViewProfile: ImageView = itemView.findViewById(R.id.imageViewProfile)
    }
}
