package com.phicdy.totoanticipation.admob

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.phicdy.totoanticipation.advertisement.AdViewHolder
import javax.inject.Inject

class AdmobViewHolder @Inject constructor(
        parent: ViewGroup,
        itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.game_list_ad, parent, false)
) : AdViewHolder(itemView) {

    val adView: AdView = itemView.findViewById(R.id.adView)

    override fun bind() {
        val id = itemView.context.getString(R.string.ad_unit_id)
        adView.loadAd(AdRequest.Builder().build())
    }
}