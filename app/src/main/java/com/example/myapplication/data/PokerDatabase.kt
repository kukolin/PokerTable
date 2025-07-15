package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [PokerSession::class, PokerPlayer::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PokerDatabase : RoomDatabase() {
    abstract fun pokerSessionDao(): PokerSessionDao
    abstract fun pokerPlayerDao(): PokerPlayerDao

    companion object {
        @Volatile
        private var INSTANCE: PokerDatabase? = null

        fun getDatabase(context: Context): PokerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokerDatabase::class.java,
                    "poker_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 