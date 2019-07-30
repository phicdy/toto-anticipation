package com.phicdy.totoanticipation.advertisement

import android.content.Context
import android.view.ViewGroup

interface AdProvider {
    fun init(context: Context)
    fun newViewHolderInstance(parent: ViewGroup): AdViewHolder
}