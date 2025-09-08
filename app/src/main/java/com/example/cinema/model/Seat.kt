package com.example.cinema.model

import java.io.Serializable

class Seat : Serializable {
    var id = 0
    var title: String? = null
    var isSelected = false
    var isVip = false

    constructor() {}
    constructor(id: Int, title: String?, selected: Boolean) {
        this.id = id
        this.title = title
        isSelected = selected
    }    constructor(id: Int, title: String?, selected: Boolean, isVip: Boolean) {
        this.id = id
        this.title = title
        isSelected = selected
        this.isVip = isVip
    }
}