package dev.me.mysmov.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.me.mysmov.data.local.dao.entity.MediaItemEntity

@Database(entities = [MediaItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase () {
    abstract fun mediaItemDao(): MediaItemDao
}