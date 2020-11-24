package com.example.testingpractice.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.testingpractice.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDb
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDb::class.java
        ).allowMainThreadQueries().build()

        dao = database.shoppingDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val item = ShoppingItem(
            "name",
            1,
            1f,
            "url",
            id = 1
        )

        dao.insertItem(item)

        val allShoppingItems = dao.getAllItems().getOrAwaitValue()
        assertThat(allShoppingItems).contains(item)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val item = ShoppingItem(
            "name",
            1,
            1f,
            "url",
            id = 1
        )
        dao.insertItem(item)
        dao.deleteItem(item)

        val allShoppingItems = dao.getAllItems().getOrAwaitValue()

        assertThat(allShoppingItems).isEmpty()
    }

    @Test
    fun getTotalPrice() = runBlockingTest {
        val item1 = ShoppingItem("name1", 5, 2f, "url", id = 1)
        val item2 = ShoppingItem("name2", 12, 6.25f, "url", id = 2)
        val item3 = ShoppingItem("name3", 4, 7f, "url", id = 3)

        val list = listOf(item1, item2, item3)
        var expectedPrice = 0f

        for (item in list) {
            dao.insertItem(item)
            expectedPrice += item.amount * item.price
        }

        val totalPrice = dao.getTotalPrice().getOrAwaitValue()

        assertThat(totalPrice).isEqualTo(expectedPrice)
    }
}