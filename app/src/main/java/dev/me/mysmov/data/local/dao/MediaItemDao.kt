package dev.me.mysmov.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.me.mysmov.data.local.dao.entity.MediaItemEntity

@Dao
interface MediaItemDao {

    @Query("SELECT * FROM MediaItemEntity")
    suspend fun getAllMediaItems(): List<MediaItemEntity>

    @Delete
    suspend fun deleteMediaItem(mediaItem: MediaItemEntity)

    @Insert
    suspend fun insertMediaItem(vararg mediaItem: MediaItemEntity)
}