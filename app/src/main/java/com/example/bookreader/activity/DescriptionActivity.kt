package com.example.bookreader.activity

import android.content.Context
import android.media.Rating
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookreader.R
import com.example.bookreader.database.BookDatabase
import com.example.bookreader.database.BookEntity
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception
import java.net.ResponseCache

class DescriptionActivity : AppCompatActivity() {

    lateinit var txtBookName: TextView
    lateinit var txtAuthorName: TextView
    lateinit var txtBookPrice: TextView
    lateinit var txtBookRating: TextView
    lateinit var imgBookImage: ImageView
    lateinit var txtBookDesc: TextView
    lateinit var btnAddTofav: Button
    lateinit var progresslayout: RelativeLayout
    lateinit var progressBar: ProgressBar

    var bookId: String? = "100"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        txtBookName = findViewById(R.id.txtBookName)
        txtAuthorName = findViewById(R.id.txtBookAuthor)
        txtBookPrice = findViewById(R.id.txtBookPrice)
        txtBookRating = findViewById(R.id.txtBookRating)
        txtBookDesc = findViewById(R.id.txtBookDescription)
        imgBookImage = findViewById(R.id.imgBookImage)
        btnAddTofav = findViewById(R.id.btnAddfav)
        progresslayout = findViewById(R.id.progressLayout)
        progresslayout.visibility = View.VISIBLE
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE



        if (intent != null) {
            bookId = intent.getStringExtra("book_id")

        } else {
            finish()
            Toast.makeText(
                this@DescriptionActivity,
                "Some Unexpected Error Occured",
                Toast.LENGTH_SHORT
            ).show()
        }

        if (bookId == "100") {
            finish()
            Toast.makeText(
                this@DescriptionActivity,
                "Some Unexpected Error Occured",
                Toast.LENGTH_SHORT
            ).show()
        }

        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = "http://13.235.250.119/v1/book/get_book/"

        val jsonParams = JSONObject()
        jsonParams.put("book_id", bookId)

        // ...

        val jsonRequest =
            object : JsonObjectRequest(Method.POST, url, jsonParams, Response.Listener {

                try {
                    val success = it.getBoolean("success")
                    println(success)

                    if (success) {
                        val bookJsonObject = it.getJSONObject("book_data")
                        progresslayout.visibility = View.GONE
                        val bookImageUrl = bookJsonObject.getString("image")


                        // Load the image into the ImageView using Picasso
                        Picasso.get().load(bookJsonObject.getString("image")).into(imgBookImage)
                        txtBookName.text = bookJsonObject.getString("name")
                        txtAuthorName.text = bookJsonObject.getString("author")
                        txtBookPrice.text = bookJsonObject.getString("price")
                        txtBookRating.text = bookJsonObject.getString("rating")
                        txtBookDesc.text = bookJsonObject.getString("description")

                        val bookEntity = BookEntity(
                            bookId?.toInt() as Int,
                            txtBookName.text.toString(),
                            txtAuthorName.text.toString(),
                            txtBookPrice.text.toString(),
                            txtBookRating.text.toString(),
                            txtBookDesc.text.toString(),
                            bookImageUrl

                        )
                        val checkFav = DBAsynTask(applicationContext, bookEntity, 1).execute()
                        val isFav = checkFav.get()
                        if (isFav) {
                            btnAddTofav.text = "Remove From Favourites"
                            val favColor =
                                ContextCompat.getColor(applicationContext, R.color.colorFavourites)
                            btnAddTofav.setBackgroundColor(favColor)

                        } else {
                            btnAddTofav.text = "Add To Favourites"
                            val favColor =
                                ContextCompat.getColor(applicationContext, R.color.colorPrimary)
                            btnAddTofav.setBackgroundColor(favColor)
                        }

                        btnAddTofav.setOnClickListener {
                            if (!DBAsynTask(applicationContext, bookEntity, 1).execute().get()) {
                                val async = DBAsynTask(applicationContext, bookEntity, 2).execute()
                                val result = async.get()
                                if (result) {
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Book is Added Into Favourite",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val async = DBAsynTask(applicationContext, bookEntity, 4).execute()


                                    btnAddTofav.text = "Remove From Favourites"
                                    val favColor = ContextCompat.getColor(
                                        applicationContext,
                                        R.color.colorFavourites
                                    )
                                    btnAddTofav.setBackgroundColor(favColor)
                                } else {
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Some Error occured",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }


                            } else {
                                val async = DBAsynTask(applicationContext, bookEntity, 3).execute()
                                val result = async.get()

                                if (result) {
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Book is Removed  From Favourites",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    btnAddTofav.text = "Add To Favourites"
                                    val favColor = ContextCompat.getColor(
                                        applicationContext,
                                        R.color.colorPrimary
                                    )
                                    btnAddTofav.setBackgroundColor(favColor)
                                } else {
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Some Error occured",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }


                    } else {
                        Toast.makeText(
                            this@DescriptionActivity,
                            "Some Error Occurred",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this@DescriptionActivity,
                        "Some Error Occurred",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }, Response.ErrorListener {
                Toast.makeText(
                    this@DescriptionActivity,
                    "Some Volley error occurred",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-Type"] = "application/json"
                    headers["token"] = "be4cdec1ce786a"
                    return headers
                }
            }

// Add the request to the queue to execute it
        queue.add(jsonRequest)


    }

    class DBAsynTask(val context: Context, val bookEntity: BookEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        /*
        Mode 1--> Check db if the book is favourite or not
        Mode 2-->Save thhe book into Db as Favouriote
        Mode 3--> Remove the Favourite
         */

        val db = Room.databaseBuilder(context, BookDatabase::class.java, "books-db").build()

        override fun doInBackground(vararg p0: Void?): Boolean {

            when (mode) {
                1 -> {
                    val book: BookEntity? = db.bookDao().getBookById(bookEntity.book_id.toString())
                    db.close()
                    return book != null


                }

                2 -> {
                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true
                }

                3 -> {
                    db.bookDao().deleteBook(bookEntity)
                    db.close()
                    return true

                }
                4 -> {
                    val allBooks: List<BookEntity> = db.bookDao().getAllBooks()
                    db.close()

                    for (book in allBooks) {
                        Log.d("Database Content", book.toString())
                    }

                    // You can also log the count of records
                    Log.d("Database Content", "Total Records: ${allBooks.size}")

                    return allBooks.isNotEmpty()
                }
            }


            return false


        }

    }
}
