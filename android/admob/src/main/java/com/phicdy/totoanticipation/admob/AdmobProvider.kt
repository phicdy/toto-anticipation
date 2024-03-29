package com.phicdy.totoanticipation.admob

import android.content.Context
import android.view.ViewGroup
import com.google.android.gms.ads.MobileAds
import com.phicdy.totoanticipation.advertisement.AdFragment
import com.phicdy.totoanticipation.advertisement.AdProvider
import com.phicdy.totoanticipation.advertisement.AdViewHolder

class AdmobProvider : AdProvider {

    override fun init(context: Context) {
        MobileAds.initialize(context) {}
    }

    override fun newViewHolderInstance(parent: ViewGroup): AdViewHolder = AdmobViewHolder(parent)

    override fun newFragmentInstance(): AdFragment = AdmobFragment.newInstance()
}