package com.wtduyuwnt.overrecipes

import android.app.Application
import com.wtduyuwnt.overrecipes.di.AppGraph

class OverRecipesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppGraph.init(this)
    }
}
