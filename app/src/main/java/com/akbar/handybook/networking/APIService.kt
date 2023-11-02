package com.akbar.handybook.networking

import com.akbar.handybook.model.Book
import com.akbar.handybook.model.Category
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/book-api")
    fun getAllBooks(): Call<List<Book>>

    @GET("/book-api/all-category")
    fun getCategories():Call<List<Category>>

    @GET("/book-api/category")
    fun getBookByCategory(@Query("name") name:String):Call<List<Book>>

    @GET("/book-api/category")
    fun getBooksByCategory(@Query("name") categoryName: String):Call<List<Book>>

    @GET("/book-api/search-name")
    fun search(@Query("name") name: String):Call<List<Book>>

    @GET("/book-api/main-book")
    fun getMainBook():Call<Book>
}