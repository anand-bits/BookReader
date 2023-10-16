package com.example.bookreader.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreader.R
import com.example.bookreader.adapter.DashboardRecyclerAdapter
import com.example.bookreader.model.Book

class DashboardFragment : Fragment() {
// 1. recycler view manager.

    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    val bookList = arrayListOf(
        "P.S. I Love You",
        "To Kill a Mockingbird",
        "1984",
        "The Great Gatsby",
        "The Catcher in the Rye",
        "The Lord of the Rings",
        "Harry Potter and the Sorcerer's Stone",
        "The Hunger Games",
        "The Da Vinci Code",
        "The Hobbit"
    )

// You now have a list of 10 book titles in the 'bookList' ArrayList.

    lateinit var recylerAdapter: DashboardRecyclerAdapter
    val bookInfoList = arrayListOf<Book>(
        Book("P.S. I love You", "Cecelia Ahern", "Rs. 299", "4.5", R.drawable.ps_ily),
        Book("The Great Gatsby", "F. Scott Fitzgerald", "Rs. 399", "4.1", R.drawable.great_gatsby),
        Book("Anna Karenina", "Leo Tolstoy", "Rs. 199", "4.3", R.drawable.anna_kare),
        Book("Madame Bovary", "Gustave Flaubert", "Rs. 500", "4.0", R.drawable.madame),
        Book("War and Peace", "Leo Tolstoy", "Rs. 249", "4.8", R.drawable.war_and_peace),
        Book("Lolita", "Vladimir Nabokov", "Rs. 349", "3.9", R.drawable.lolita),
        Book("Middlemarch", "George Eliot", "Rs. 599", "4.2", R.drawable.middlemarch),
        Book("The Adventures of Huckleberry Finn", "Mark Twain", "Rs. 699", "4.5", R.drawable.adventures_finn),
        Book("Moby-Dick", "Herman Melville", "Rs. 499", "4.5", R.drawable.moby_dick),
        Book("The Lord of the Rings", "J.R.R Tolkien", "Rs. 749", "5.0", R.drawable.lord_of_rings)
    )




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //fragment outlent will be in container.
        //last boolean value will be passed.. false mean not one fragement permanmentaly added.

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerDashboard = view.findViewById(R.id.recyclDashboard);
        layoutManager = LinearLayoutManager(activity)
        recylerAdapter = DashboardRecyclerAdapter(activity as Context, bookInfoList)
        recyclerDashboard.adapter = recylerAdapter
        recyclerDashboard.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(recyclerDashboard.context, LinearLayoutManager.VERTICAL)
        recyclerDashboard.addItemDecoration(itemDecoration)

        return view
    }


}