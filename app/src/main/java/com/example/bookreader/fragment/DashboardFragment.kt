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
        recylerAdapter = DashboardRecyclerAdapter(activity as Context, bookList)
        recyclerDashboard.adapter = recylerAdapter
        recyclerDashboard.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(recyclerDashboard.context, LinearLayoutManager.VERTICAL)
        recyclerDashboard.addItemDecoration(itemDecoration)

        return view
    }


}