package com.github.sebastianaldi17.cave_explore

import android.app.Application

class MyApplication: Application() {
    // Endings
    companion object {
        var normalEnding = false
        var badEnding = false
        var goodEnding = false
    }
}