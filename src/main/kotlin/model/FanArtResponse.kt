package org.example.model

import com.google.gson.annotations.SerializedName

data class FanArtResponse(
    val name: String,
    @SerializedName("thetvdb_id")
    val tvDbId: String,
    @SerializedName("clearlogo")
    val clearLogo: List<Image>,
    @SerializedName("hdtvlogo")
    val hdTvLogo: List<Image>,
    @SerializedName("clearart")
    val clearArt: List<Image>,
    @SerializedName("showbackground")
    val background: List<Image>,
    @SerializedName("tvthumb")
    val tvThumb: List<Image>,
    @SerializedName("seasonposter")
    val seasonPoster: List<Image>,
    @SerializedName("seasonthumb")
    val seasonThumb: List<Image>,
    @SerializedName("hdclearart")
    val hdClearArt: List<Image>,
    @SerializedName("tvbanner")
    val tvBanner: List<Image>,
    @SerializedName("characterart")
    val characterArt: List<Image>,
    @SerializedName("tvposter")
    val tvPoster: List<Image>,
    @SerializedName("seasonbanner")
    val seasonBanner: List<Image>
)