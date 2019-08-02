package com.phicdy.totoanticipation.admob

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.phicdy.totoanticipation.advertisement.AdViewHolder

class AdmobViewHolder(
        parent: ViewGroup,
        itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.game_list_ad, parent, false)
) : AdViewHolder(itemView) {

    private val adView: AdView = itemView.findViewById(R.id.adView)

    override fun bind() {
        adView.loadAd(AdRequest.Builder().build())
    }
}