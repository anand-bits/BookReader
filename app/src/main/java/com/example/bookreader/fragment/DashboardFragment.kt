package com.example.bookreader.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
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
import com.android.volley.toolbox.JsonObjectRequest as JsonObjectRequest

class DashboardFragment : Fragment() {
// 1. recycler view manager.

    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var btnCheckinternet: Button
   val bookInfoList = arrayListOf<Book>()


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
        //Internet Check Is available

        btnCheckinternet = view.findViewById(R.id.btnCheckInternet)

        btnCheckinternet.setOnClickListener {
            if (ConnectionManager().checkConnectivity(activity as Context)) {
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("success")
                dialog.setMessage("Internet Connection Found")
                dialog.setPositiveButton("Ok") { text, listner ->
                }
                dialog.setNegativeButton("Cancel") { text, listner ->
                }
                dialog.create()
                dialog.show()


            } else {

                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Connection Not Found")
                dialog.setPositiveButton("Ok") { text, listner ->
                }
                dialog.setNegativeButton("Cancel") { text, listner ->
                }
                dialog.create()
                dialog.show()
            }
        }





        val queue = Volley.newRequestQueue(activity as Context)

        val url = "http://13.235.250.119/v1/book/fetch_books/"

        val jsonObjectRequest =
            object : JsonObjectRequest(Method.GET, url, null, Response.Listener {

// Here we will handle the  response.

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
                        val itemDecoration =
                            DividerItemDecoration(recyclerDashboard.context, LinearLayoutManager.VERTICAL)
                        recyclerDashboard.addItemDecoration(itemDecoration)
                    }

                }
                else
                {
                    Toast.makeText(activity as Context,"Some error Occured!!",Toast.LENGTH_SHORT).show()

                }

            }, Response.ErrorListener {


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
        return view
    }


}