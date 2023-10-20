package com.example.bookreader.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class], version = 1)

abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao():BookDao

}
// Async Class

//Async task Ui thread Ko proper use kar sakte .
// It allows to perform background  operations on a worker thread then publish the results on the main ui thread
