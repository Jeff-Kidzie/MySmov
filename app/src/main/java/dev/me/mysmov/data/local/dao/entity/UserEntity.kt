package dev.me.mysmov.data.local.dao.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
)
