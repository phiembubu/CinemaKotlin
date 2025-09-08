package com.example.cinema.model

import java.io.Serializable

class RoomFirebase : Serializable {
    var id = 0
    var title: String? = null
    var times: List<TimeFirebase>? = null

    constructor() {}
    constructor(id: Int, title: String?, times: List<TimeFirebase>?) {
        this.id = id
        this.title = title
        this.times = times
    }
}