package dev.me.mysmov.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.me.mysmov.data.local.dao.MediaItemDao
import dev.me.mysmov.data.local.dao.entity.MediaItemEntity

@Database(entities = [MediaItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mediaItemDao(): MediaItemDao
}