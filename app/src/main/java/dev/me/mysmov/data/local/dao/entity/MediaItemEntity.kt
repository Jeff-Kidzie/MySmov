package dev.me.mysmov.data.local.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MediaItemEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo val title: String,
    @ColumnInfo("poster_path") val posterPath: String?,
    @ColumnInfo("release_date") val releaseDate: String?,
    @ColumnInfo("media_type") val mediaType: String
)