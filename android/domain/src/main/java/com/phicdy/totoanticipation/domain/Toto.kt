package com.phicdy.totoanticipation.domain

import java.util.Date

data class Toto(val number: String, val deadline: Date) {
    companion object {
        const val DEFAULT_NUMBER = "0000"
    }
}
