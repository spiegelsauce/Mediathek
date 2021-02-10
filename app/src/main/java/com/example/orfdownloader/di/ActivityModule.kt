package com.example.orfdownloader.di

import com.example.orfdownloader.data.RadioStations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    @ActivityScoped
    fun stationListProvider(): Array<RadioStations> {
        return RadioStations.values()
    }
}