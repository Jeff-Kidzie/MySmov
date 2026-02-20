package dev.me.mysmov.data.model.dto

import com.google.gson.annotations.SerializedName
import dev.me.mysmov.core.AppConstant
import dev.me.mysmov.domain.model.ui.Cast

data class CastDto(
    val adult : Boolean,
    val gender : Int,
    val id : Int,
    @SerializedName("known_for_department")
    val knownDepartment : String,
    val name : String,
    @SerializedName("original_name")
    val originalName : String,
    val popularity : Double,
    @SerializedName("profile_path")
    val profilePath : String?,
    @SerializedName("cast_id")
    val castId : Int,
    val character : String,
    @SerializedName("credit_id")
    val creditId : String,
    val order : Int
)

fun CastDto.toCast() = Cast(
    id = id,
    name = name,
    role = character,
    imgUrl = AppConstant.BASE_URL_IMAGE + (profilePath ?: "")
)