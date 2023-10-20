package com.example.bookreader.fragment

import android.content.AsyncQueryHandler
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

import com.example.bookreader.R
import com.example.bookreader.adapter.FavouriteRecyclerAdapter
import com.example.bookreader.database.BookDatabase
import com.example.bookreader.database.BookEntity


class FavouritesFragment : Fragment() {

    lateinit var recyclerFavourites: RecyclerView
    lateinit var progresslayout: RelativeLayout
    lateinit var progressbar: ProgressBar
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: FavouriteRecyclerAdapter

    var dbBooklist = listOf<BookEntity>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)

        recyclerFavourites = view.findViewById(R.id.recyclerFavourites)
        progresslayout = view.findViewById(R.id.progressLayout)
        progressbar = view.findViewById(R.id.progressBar)
        layoutManager = GridLayoutManager(activity as Context, 2)

        dbBooklist = RetrieveFavourites(activity as Context).execute().get()


        if (activity != null) {
            progresslayout.visibility = View.GONE
            recyclerAdapter = FavouriteRecyclerAdapter(activity as Context, dbBooklist)
            recyclerFavourites.adapter = recyclerAdapter
            recyclerFavourites.layoutManager = layoutManager

        }



        return view
    }

    class RetrieveFavourites(val context: Context) : AsyncTask<Void, Void, List<BookEntity>>() {
        override fun doInBackground(vararg p0: Void?): List<BookEntity> {
            val db= Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()
            return db.bookDao().getAllBooks()
        }

    }
}