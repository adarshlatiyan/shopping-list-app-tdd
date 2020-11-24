package com.example.testingpractice.di

import android.content.Context
import androidx.room.Room
import com.example.testingpractice.data.local.ShoppingItemDb
import com.example.testingpractice.data.remote.PixabayApi
import com.example.testingpractice.util.DB_NAME
import com.example.testingpractice.util.PIXABAY_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideShoppingItemDb(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDb::class.java, DB_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingDao(
        db: ShoppingItemDb
    ) = db.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayApi() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(PIXABAY_BASE_URL)
        .build()
        .create(PixabayApi::class.java) as PixabayApi
}