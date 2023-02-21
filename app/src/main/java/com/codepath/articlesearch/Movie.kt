package com.codepath.articlesearch
import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class SearchNewsResponse(
    @SerialName("results")
    val results: List<Movie>?
)

@Keep
@Serializable
data class Movie(
    @SerialName("title")
    val title: String? = null,
    @SerialName("release_date")
    val release_date: String? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("poster_path")
    val poster_path: String? = null,
) : java.io.Serializable
