package com.example.orfdownloader.data

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.example.orfdownloader.R

enum class RadioStations(
    val humanFriendlyName: String,
    val broadcastUrlKey: String,
    val loopStreamUrlKey: String,
    @DrawableRes val icon: Int,
    @ColorRes val bgcolor: Int
) {
    OE1("Ö1", "oe1", "oe1", R.drawable.ic_oe1, R.color.oe1bg),
    OE3("Ö3", "oe3", "oe3", R.drawable.ic_oe3, R.color.oe3bg),
    FM4("FM4", "fm4", "fm4", R.drawable.ic_fm4, R.color.fm4bg),
    KTN("Radio Kärnten", "ktn", "oe2k", R.drawable.ic_ktn, R.color.oe2bg),
    NOE("Radio Niederösterreich", "noe", "oe2n", R.drawable.ic_noe, R.color.oe2bg),
    SBG("Radio Salzburg", "sbg", "oe2s", R.drawable.ic_sbg, R.color.oe2bg),
    WIE("Radio Wien", "wie", "oe2w", R.drawable.ic_wie, R.color.oe2bg),
    STM("Radio Steiermark", "stm", "oe2st", R.drawable.ic_stm, R.color.oe2bg),
    OOE("Radio Oberösterreich", "ooe", "oe2o", R.drawable.ic_ooe, R.color.oe2bg),
    VBG("Radio Vorarlberg", "vbg", "oe2v", R.drawable.ic_vbg, R.color.oe2bg),
    BGL("Radio Burgenland", "bgl", "oe2b", R.drawable.ic_bgl, R.color.oe2bg),
    TIR("Radio Tirol", "tir", "oe2t", R.drawable.ic_tir, R.color.oe2bg)
}

