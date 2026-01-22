package com.smashing.app.core.extension


fun Int.formatCount(): String {
    return if (this > 99) "99+" else this.toString()
}
