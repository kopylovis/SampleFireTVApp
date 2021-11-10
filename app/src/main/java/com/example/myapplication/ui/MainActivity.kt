package com.example.myapplication.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.iagentur.loggeroverlay.LoggerOverlay
import com.example.myapplication.databinding.LayoutBinding
import com.example.myapplication.misc.Config
import com.example.myapplication.model.Movie
import com.example.myapplication.ui.adapters.MenuItemsAdapter
import com.example.myapplication.ui.adapters.RowAdapter
import com.ope.mobile.android.Ope
import com.ope.mobile.android.public.OpeKey
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: LayoutBinding
    private lateinit var preferenceManager: SharedPreferences

    private var rowDataSet = listOf(
        "Movie 1",
        "Movie 2",
        "Movie 3",
        "Movie 4",
        "Movie 5",
        "Movie 6",
        "Movie 7",
        "Movie 8",
        "Movie 9",
        "Movie 10"
    )
    private var menuItemsDataSet = listOf("Category 1", "Category 2", "Category 3", SEND_PENDING_EVENTS, LOGGER_OVERLAY_ITEM)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupLoggerOverlay()
        setupMenuItemsAdapter()
        setupRowAdapters()
    }

    private fun setupMenuItemsAdapter() {
        val menuAdapter = MenuItemsAdapter(menuItemsDataSet)
        binding.apply {
            recyclerViewMenu.layoutManager =
                LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            recyclerViewMenu.adapter = menuAdapter
            menuAdapter.clickListener = {
                when (it) {
                    LOGGER_OVERLAY_ITEM -> {
                        if (preferenceManager.getBoolean(PREFERENCE_KEY_LOGGER, false)) {
                            LoggerOverlay.hideLoggerOverlay()
                            preferenceManager.edit().putBoolean(PREFERENCE_KEY_LOGGER, false).apply()
                        } else {
                            LoggerOverlay.showLoggerOverlay(this@MainActivity)
                            preferenceManager.edit().putBoolean(PREFERENCE_KEY_LOGGER, true).apply()
                        }
                        logToast(message = "$it was ${if (preferenceManager.getBoolean(PREFERENCE_KEY_LOGGER, false)) "enabled" else "disabled"}", shouldLog = false)
                    }
                    SEND_PENDING_EVENTS -> {
                        Ope.sendPendingEvents()
                        logToast(message = "Sending pending events", shouldLog = false)
                    }
                }
            }
        }
    }

    private fun setupRowAdapters() {
        val rowAdapter = RowAdapter(rowDataSet)
        rowAdapter.clickListener = { itemName, position ->
            sendCustomEvent(itemName, position + 1)
            logToast(message = "Added $itemName to event queue", true)
        }
        binding.apply {
            //TODO Setup Rows adapter 1
            recyclerView.layoutManager =
                LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
            recyclerView.adapter = rowAdapter
            //TODO Setup Rows adapter 2
            recyclerView2.layoutManager =
                LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
            recyclerView2.adapter = rowAdapter
            //TODO Setup Rows adapter 3
            recyclerView3.layoutManager =
                LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
            recyclerView3.adapter = rowAdapter
            //TODO Setup Rows adapter 4
            recyclerView4.layoutManager =
                LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
            recyclerView4.adapter = rowAdapter
        }
    }

    private fun setupLoggerOverlay() {
        preferenceManager = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        if (preferenceManager.getBoolean(PREFERENCE_KEY_LOGGER, false)) {
            LoggerOverlay.showLoggerOverlay(this)
            Timber.tag(Config.OPE_TAG).d("Fire OS TV Demo app started successfuly")
        }
    }

    private fun logToast(message: String, shouldLog: Boolean) {
        if (shouldLog) {
            Timber.tag(Config.OPE_TAG).e(message)
        }
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun sendCustomEvent(itemName: String, id: Int) {
        val opePayload = mapOf(OpeKey.ITEM_URI to Ope.buildIdentifier("demo-category", id.toString()))
        val movie = Movie(id = id.toString(), title = itemName)
        val customPayload = createMoviePayload(movie)
        Ope.trackEvent("movie", opePayload = opePayload, customPayload = customPayload)
    }

    private fun createMoviePayload(movie: Movie?): Map<String, Any> {
        val customPayload = mutableMapOf<String, Any>()
        val moviePayload = mutableMapOf<String, Any?>()
        moviePayload["id"] = movie?.id
        moviePayload["name"] = movie?.title
        customPayload["custom_data"] = moviePayload
        return customPayload
    }

    companion object {
        private const val PREFERENCE_KEY_LOGGER = "showLoggerOverlay"
        private const val LOGGER_OVERLAY_ITEM = "Logger Overlay"
        private const val SEND_PENDING_EVENTS = "Send Pending Events"
    }
}