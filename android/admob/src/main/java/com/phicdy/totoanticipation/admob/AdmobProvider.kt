package com.phicdy.totoanticipation.admob

import android.content.Context
import com.google.android.gms.ads.MobileAds
import com.phicdy.totoanticipation.advertisement.AdProvider

class AdmobProvider : AdProvider {
   
    override fun init(context: Context) {
        MobileAds.initialize(context, BuildConfig.ADD_APP_ID)
    }
}