package com.example.bookreader

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    //lateinit indicate that a non-nullable property will be initialized at a later point in code.
    // Coordinator Layout:- A cordinator Layout is a super-powered frame layout which is used when there are multiple
    // interaction between the Views. For example , in our situation, we will use it to manage the interactions between the navigation drawer,toolbar and the frame layout.
// Hamburger icon is known as the action bar drawer toggle. It is used to open and close  the navigation drawer.


    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinateLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout= findViewById(R.id.drawerLayout)
        coordinateLayout=findViewById(R.id.coordinatorLayout)
        toolbar=findViewById(R.id.toolbar)
        frameLayout= findViewById(R.id.frame)
        navigationView= findViewById(R.id.navigationView)
        setUpToolbar();

//Hamburg Icon is now functional.

        val actionBarDrawerToggle= ActionBarDrawerToggle(this@MainActivity,drawerLayout,R.string.open_drawer,R.string.close_drawer)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()


        //we cant add clickListner in ActionBar so we have another way for adding the  ClickListner
        //onOptionsItemSelected will work here





    }

    fun setUpToolbar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title= "Toolbar Tittle"
        supportActionBar?.setHomeButtonEnabled(true)

        //This line enables the "home" button, which is often represented as an arrow pointing to the left (commonly used for navigation back to the previous screen).
        // If you enable the home button, it makes the button clickable and responsive to user actions.


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //This line sets whether the "home" button should be displayed as an "up" indicator (a left-facing arrow) in the app bar.
    // When you set this to true, it shows the "up" indicator, indicating that the current screen is a subpage or detail view that can be navigated back to the parent or main screen.
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id= item.itemId;
        if(id==android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START)

        }

        return super.onOptionsItemSelected(item)
    }

}