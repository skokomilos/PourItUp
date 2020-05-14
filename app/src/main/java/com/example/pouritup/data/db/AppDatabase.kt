package com.example.pouritup.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pouritup.data.db.dao.BasicCoctailDao
import com.example.pouritup.data.network.entity.CoctailBasic
import com.example.pouritup.utilities.DATABASE_NAME

@Database(entities = [CoctailBasic::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun coctailBasicDao(): BasicCoctailDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java, DATABASE_NAME)
                .build()
    }
}