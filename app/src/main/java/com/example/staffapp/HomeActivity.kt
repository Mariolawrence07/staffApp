package com.example.staffapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.recyclerview.adapter.DotIndicatorAdapter
import com.example.recyclerview.adapter.ImageAdvertAdapter
import com.example.staffapp.adapter.GridAdapter
import com.example.staffapp.databinding.ActivityHomeBinding
import java.util.Timer
import java.util.TimerTask
import android.widget.GridView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var viewPager: ViewPager2
    private lateinit var imageAdvertAdapter: ImageAdvertAdapter
    private val imageAdverts = listOf(
        "https://www.staffs.ac.uk/image-library/homepage/hero-images/university-of-the-year-home-hero.jpg",
        "https://firebasestorage.googleapis.com/v0/b/weather-app-31da1.appspot.com/o/stoke-campus-zebra.jpg?alt=media&token=81413192-7f17-4fb0-9a89-18627697d672",
        )

    private var currentPage = 0
    private var timer: Timer? = null
    private lateinit var dotIndicatorAdapter: DotIndicatorAdapter

    private lateinit var toolbar: Toolbar
    private lateinit var gridView: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        gridView = binding.gridView

        val dataList = listOf(
            arrayOf("image1", "Lectures"),
            arrayOf("weather", "Weather Guide"),
//            arrayOf("image4", "Note"),
            arrayOf("image5", "Phone Book")
        )

        val adapter = GridAdapter(this, dataList)
        gridView.adapter = adapter

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.navigationIcon = null
        toolbar.title= null
        toolbar.elevation = 0f

        viewPager = binding.viewPager
        imageAdvertAdapter = ImageAdvertAdapter(imageAdverts)
        viewPager.adapter = imageAdvertAdapter

        val recyclerViewDots = binding.recyclerViewDots
        dotIndicatorAdapter = DotIndicatorAdapter(imageAdverts.size)
        recyclerViewDots.adapter = dotIndicatorAdapter
        recyclerViewDots.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Set orientation to horizontal
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // Optionally, set page transformer for sliding animations
        val pageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(resources.getDimensionPixelOffset(R.dimen.viewpager_page_margin)))
            addTransformer(ScalePageTransformer())
        }
        viewPager.setPageTransformer(pageTransformer)

//        val recyclerView = binding.recyclerFacts;
//        recyclerView.adapter = ItemAdapter(this, theData)

        // Set up page change listener to update the current page
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPage = position
                dotIndicatorAdapter.setSelectedPosition(position)
            }
        })

        // Start auto-scrolling after a delay
        startAutoScroll()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Handle navigation icon (profile icon) click
                true
            }
            R.id.action_profile -> {
                // Handle menu dropdown click
                true
            }
            R.id.logout -> {
                Firebase.auth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                // Handle menu dropdown click
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }

    private fun startAutoScroll() {
        val handler = Handler()
        val updateRunnable = Runnable {
            if (currentPage == imageAdverts.size) {
                currentPage = 0
            }
            viewPager.setCurrentItem(currentPage++, true)
        }

        // Delay before starting auto-scrolling
        val delay = 3000L // 3 seconds

        // Schedule the runnable to be executed periodically
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                handler.post(updateRunnable)
            }
        }, delay, delay)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }


}
