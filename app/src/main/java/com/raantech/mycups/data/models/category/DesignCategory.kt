package com.raantech.mycups.data.models.category

import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import com.raantech.mycups.data.models.media.Media
import java.io.Serializable

data class DesignCategory(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("logo")
    val logo: Media? = null,

    @field:SerializedName("has_sub")
    val hasSub: Boolean? = null,

    @field:SerializedName("canSelect")
    var canSelect: Boolean? = null,

    @Transient
    val isSelected: MutableLiveData<Boolean> = MutableLiveData(false)

) : Serializable
