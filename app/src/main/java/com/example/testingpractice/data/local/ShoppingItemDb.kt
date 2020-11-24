package com.example.testingpractice.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ShoppingItem::class], version = 1)
abstract class ShoppingItemDb : RoomDatabase() {
    abstract fun shoppingDao(): ShoppingDao
}