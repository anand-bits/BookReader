package com.example.bookreader.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.bookreader.R
import com.example.bookreader.adapter.DashboardRecyclerAdapter
import com.example.bookreader.model.Book
import com.example.bookreader.util.ConnectionManager
import org.json.JSONException
import java.util.Collections
import com.android.volley.toolbox.JsonObjectRequest as JsonObjectRequest

class DashboardFragment : Fragment() {
// 1. recycler view manager.

    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var progressBar:ProgressBar
    lateinit var progressLayout: RelativeLayout

   val bookInfoList = arrayListOf<Book>()
    val  ratingComparator= Comparator<Book>
    {
        book1,book2->
        book1.bookRating.compareTo((book2.bookRating),true)
    }


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
        setHasOptionsMenu(true)

        recyclerDashboard = view.findViewById(R.id.recyclDashboard);
        //Internet Check Is available


        progressLayout= view.findViewById(R.id.progresslayout)
        progressBar=view.findViewById(R.id.progressbar)

        progressLayout.setVisibility(View.VISIBLE);







        val queue = Volley.newRequestQueue(activity as Context)

        val url = "http://13.235.250.119/v1/book/fetch_books/"
        if(ConnectionManager().checkConnectivity(activity as Context))
        {
            progressLayout.setVisibility(View.GONE);

            val jsonObjectRequest =
                object : JsonObjectRequest(Method.GET, url, null, Response.Listener {

// Here we will handle the  response.
                    try{
                        val success=it.getBoolean("success")
                        println(success);
                        if(success)
                        {
                            // println("Success ${it}")
                            val data= it.getJSONArray("data")
                            for(i in 0 until data.length())
                            {
                                val bookJsonObject= data.getJSONObject(i)
                                val bookObject= Book(
                                    bookJsonObject.getString("book_id"),
                                    bookJsonObject.getString("name"),
                                    bookJsonObject.getString("author"),
                                    bookJsonObject.getString("rating"),
                                    bookJsonObject.getString("price"),
                                    bookJsonObject.getString("image")


                                )

                                bookInfoList.add(bookObject)
                                println(bookInfoList.size)

                                layoutManager = LinearLayoutManager(activity)
                                recylerAdapter = DashboardRecyclerAdapter(activity as Context, bookInfoList)
                                recyclerDashboard.adapter = recylerAdapter
                                recyclerDashboard.layoutManager = layoutManager

                            }

                        }
                        else
                        {
                            Toast.makeText(activity as Context,"Some error Occured!!",Toast.LENGTH_SHORT).show()

                        }
                    }
                    catch (e:JSONException)
                    {
                        Toast.makeText(activity as Context,"SOme Unexpected exception occured!!",Toast.LENGTH_SHORT).show()

                    }



                }, Response.ErrorListener {

                    Toast.makeText(activity as Context,"Some Volley Error occured",Toast.LENGTH_SHORT).show()

                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-Type"] = "application/json"
                        headers["token"] = "be4cdec1ce786a"
                        return headers
                    }
                }
            println("reached here")
            queue.add(jsonObjectRequest)
        }
        else
        {
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection Not Found")
            dialog.setPositiveButton("Open Setting") { text, listner ->
                val settingsIntent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()

            }
            dialog.setNegativeButton("Exit") { text, listner ->

                ActivityCompat.finishAffinity(activity as Activity)

            }
            dialog.create()
            dialog.show()
        }


        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       inflater?.inflate(R.menu.menu_dashboard,menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {



        val id=item?.itemId
        if(id==R.id.action_sort)
        {
            Collections.sort(bookInfoList,ratingComparator)
            bookInfoList.reverse()
        }
        recylerAdapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }


}