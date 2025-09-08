package com.example.cinema.model

import java.io.Serializable

class TimeFirebase : Serializable {
    var id = 0
    var title: String? = null
    var seats: List<Seat>? = null

    constructor() {}
    constructor(id: Int, title: String?, seats: List<Seat>?) {
        this.id = id
        this.title = title
        this.seats = seats
    }
}