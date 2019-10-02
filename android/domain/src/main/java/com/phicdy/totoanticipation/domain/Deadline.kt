package com.phicdy.totoanticipation.domain

data class Deadline(
        private val time: String
) {
    override fun toString() = time
}
