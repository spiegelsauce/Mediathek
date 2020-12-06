package com.example.orfdownloader.data

import androidx.annotation.DrawableRes
import com.example.orfdownloader.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class Station @Inject constructor() {
    var current: RadioStations = RadioStations.OE1 // default to OE1, just because
}


enum class RadioStations(
    val humanFriendlyName: String,
    val broadcastUrlKey: String,
    val loopStreamUrlKey: String,
    val programPrefix: String,
    @DrawableRes val icon: Int
) {
    OE1("Ö1", "oe1", "oe1", "6", R.drawable.ic_oe1),
    OE3("Ö3", "oe3", "oe3", "3", R.drawable.ic_oe3),
    FM4("FM4", "fm4", "fm4", "4", R.drawable.ic_fm4),
    KTN("Radio Kärnten", "ktn", "oe2k", "K", R.drawable.ic_ktn),
    NOE("Radio Niederösterreich", "noe", "oe2n", "N", R.drawable.ic_noe),
    SBG("Radio Salzburg", "sbg", "oe2s", "S", R.drawable.ic_sbg),
    WIE("Radio Wien", "wie", "oe2w", "W", R.drawable.ic_wie),
    STM("Radio Steiermark", "stm", "oe2st", "ST", R.drawable.ic_stm),
    OOE("Radio Oberösterreich", "ooe", "oe2o", "O", R.drawable.ic_ooe),
    VBG("Radio Vorarlberg", "vbg", "oe2v", "V", R.drawable.ic_vbg),
    BGL("Radio Burgenland", "bgl", "oe2b", "B", R.drawable.ic_bgl),
    TIR("Radio Tirol", "tir", "oe2t", "T", R.drawable.ic_tir)
}

