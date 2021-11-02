package com.example.myapplication

import android.app.Application
import com.ope.mobile.android.Ope
import com.ope.mobile.android.internal.model.QueueDispatchConfig
import com.ope.mobile.android.public.RemoteConfigDefaults

class DemoAppTv: Application() {

    override fun onCreate() {
        super.onCreate()
        val config = RemoteConfigDefaults(
            queueDispatchConfig = QueueDispatchConfig(
                seconds = 10,
                maxSize = 10L
            ),
            consentType = "tcf2"
        )
        Ope.start(this, CLIENT_ID, shouldLogToConsole = BuildConfig.DEBUG, config, isPinningEnable = false)
    }

    companion object {
        const val CLIENT_ID = "acme"
    }

}