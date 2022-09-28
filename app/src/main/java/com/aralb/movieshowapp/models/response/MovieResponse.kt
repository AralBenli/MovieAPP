package com.aralb.movieshowapp.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse (
    @SerializedName(value = "results")
        val movies : List<MovieResultItem>,
    @SerializedName (value = "page")
        val page :Int,
    @SerializedName (value = "total_pages")
        val totalPages : Int,
    @SerializedName (value = "total_results")
        val totalResults :Int
        ) : Parcelable