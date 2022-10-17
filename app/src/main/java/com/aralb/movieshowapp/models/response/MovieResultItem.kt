package com.aralb.movieshowapp.models.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MovieResultItem(
    val id: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
) : Parcelable