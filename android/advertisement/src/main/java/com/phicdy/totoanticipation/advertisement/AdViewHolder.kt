package com.phicdy.totoanticipation.advertisement

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind()
}