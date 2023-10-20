package com.example.bookreader.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bookreader.R


class AboutAppFragment : Fragment() {

    lateinit var txtAboutTitle: TextView
    lateinit var txtAboutContent: TextView
    lateinit var imgAboutMe: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about_app, container, false)

        txtAboutTitle = view.findViewById(R.id.txtAboutTitle)
        imgAboutMe = view.findViewById(R.id.imgAboutImage)

        txtAboutContent =
            view.findViewById(R.id.txtAboutContent)// Set the text for the title and content views within the CardView
        txtAboutTitle.text = "About App " // Set the title text
        txtAboutContent.text =
            "BookReader: Your ultimate book library. Discover a curated collection of books, view ratings, and prices. Get a brief introduction to each book. Powered by Kotlin, HTTP, Volley, and Room Database—a library for book enthusiasts." // Set the content text

        return view
    }
}