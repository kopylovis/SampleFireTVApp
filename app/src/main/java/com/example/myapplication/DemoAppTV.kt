package com.example.myapplication

import android.app.Application
import ch.iagentur.loggeroverlay.LoggerOverlay
import ch.iagentur.loggeroverlay.internal.AlviOptions
import com.example.myapplication.misc.Logger
import com.ope.mobile.android.Ope
import com.ope.mobile.android.internal.model.QueueDispatchConfig
import com.ope.mobile.android.public.RemoteConfigDefaults
import timber.log.Timber

class DemoAppTV: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Logger)
        }
        val config = RemoteConfigDefaults(
            queueDispatchConfig = QueueDispatchConfig(
                seconds = 10,
                maxSize = 10L
            ),
            consentType = "tcf2"
        )
        setupLoggerOverlay()
        Ope.start(this, CLIENT_ID, shouldLogToConsole = BuildConfig.DEBUG, config, isPinningEnable = false)
    }


    private fun setupLoggerOverlay() {
        val config = LoggerOverlay.Config(BuildConfig.DEBUG, AlviOptions.Builder().build())
        LoggerOverlay.init(this, config)
        LoggerOverlay.addLogger(Logger.loggerOverlay)
    }

    companion object {
        const val CLIENT_ID = "acme"
    }

}