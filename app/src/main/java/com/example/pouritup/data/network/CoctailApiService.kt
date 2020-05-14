package com.example.pouritup.data.network

import com.example.pouritup.data.network.response.CategoryResponse
import com.example.pouritup.data.network.response.CoctailBasicResponse
import com.example.pouritup.data.network.response.CoctailDetailResponse
import com.example.pouritup.data.network.response.IngredientResponse
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "7e2eb8442amsh252f718bbb71d0fp130cfdjsnee4b388c0881"

interface CoctailApiService {

    @GET("list.php?i=list")
    suspend fun getIngridients(): IngredientResponse

    @GET("filter.php")
    suspend fun getBasicCoctailByIngridientName(@Query("i") ingredientName: String): CoctailBasicResponse

    @GET("list.php?c=list")
    suspend fun getAllCagegories(): CategoryResponse

    @GET("filter.php")
    suspend fun getBasicCoctailByCategoryName(@Query("c") categoryName: String): CoctailBasicResponse

    @GET("lookup.php")
    suspend fun getDrinkByID(@Query("i") drinkID : String): CoctailDetailResponse

    //todo ovde getdrink by id basic

    //similar to static methods in Java
    companion object{
        //invoke is method that can have any other name but with "invoke" we can can call this method as easy just to write class name in this case CoctailApiService() like instatiation class. We can can it CoctailApiService.invoke() but
        //but first way is much nicier sintatically
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): CoctailApiService{
            //when we have some value that is pass in query string every time when is called (something like API_KEY) so better way to do this is requestInterceptor
            val requestInterceptor = Interceptor{chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()

                val request: Request = Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("x-rapidapi-host", "the-cocktail-db.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", API_KEY)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return  Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://the-cocktail-db.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(CoctailApiService::class.java)
        }
    }
}