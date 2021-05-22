package com.example.corev2

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.corev2.database.HairAppDatabase
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class HairAppDatabaseTestRule : TestWatcher() {
    lateinit var db: HairAppDatabase

    override fun starting(description: Description?) {
        super.starting(description)
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, HairAppDatabase::class.java).build()
    }

    override fun finished(description: Description?) {
        super.finished(description)
        db.close()
    }
}