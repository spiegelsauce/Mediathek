package com.example.orfdownloader.data

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.example.orfdownloader.R

enum class RadioStations(
    val humanFriendlyName: String,
    val broadcastUrlKey: String,
    val loopStreamUrlKey: String,
    val programPrefix: String,
    @DrawableRes val icon: Int,
    @ColorRes val bgcolor: Int
) {
    OE1("Ö1", "oe1", "oe1", "6", R.drawable.ic_oe1, R.color.oe1bg),
    OE3("Ö3", "oe3", "oe3", "3", R.drawable.ic_oe3, R.color.oe3bg),
    FM4("FM4", "fm4", "fm4", "4", R.drawable.ic_fm4, R.color.fm4bg),
    KTN("Radio Kärnten", "ktn", "oe2k", "K", R.drawable.ic_ktn, R.color.oe2bg),
    NOE("Radio Niederösterreich", "noe", "oe2n", "N", R.drawable.ic_noe, R.color.oe2bg),
    SBG("Radio Salzburg", "sbg", "oe2s", "S", R.drawable.ic_sbg, R.color.oe2bg),
    WIE("Radio Wien", "wie", "oe2w", "W", R.drawable.ic_wie, R.color.oe2bg),
    STM("Radio Steiermark", "stm", "oe2st", "ST", R.drawable.ic_stm, R.color.oe2bg),
    OOE("Radio Oberösterreich", "ooe", "oe2o", "O", R.drawable.ic_ooe, R.color.oe2bg),
    VBG("Radio Vorarlberg", "vbg", "oe2v", "V", R.drawable.ic_vbg, R.color.oe2bg),
    BGL("Radio Burgenland", "bgl", "oe2b", "B", R.drawable.ic_bgl, R.color.oe2bg),
    TIR("Radio Tirol", "tir", "oe2t", "T", R.drawable.ic_tir, R.color.oe2bg)
}

