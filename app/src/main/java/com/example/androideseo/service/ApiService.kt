package com.example.androideseo.service

import android.net.Uri
import com.example.androideseo.BuildConfig
import com.example.androideseo.data.LocalPreferences
import com.example.androideseo.data.models.Client
import com.example.androideseo.data.models.Information
import com.example.androideseo.data.models.LocalUser
import com.example.androideseo.data.models.remote.User
import com.example.androideseo.ui.app.MyApp
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.File
import java.io.InputStream
import java.util.concurrent.TimeUnit


/**
 * ApiService permettant de créer la connexion avec l'api
 */
interface ApiService {

    data class Post(
        val user: String,
        val password: String
    )

    @Multipart
    @POST("/uploadFile")
    suspend fun upload(@Part filePart: File?): String
    // You can add other parameters too


    /**
     * TODO
     *
     * @param userData
     * @return
     */
    @POST("/client/connexion")
    suspend fun postconnexion(@Body userData: ServiceClient.UserInfo) : Client

    /**
     * TODO
     *
     * @param userData
     * @return
     */
    @POST("/client/inscription")
    suspend fun postinscription(@Body userData: ServiceClient.UserInfo) : Client


    /**
     * TODO
     *
     * @return
     */
    @GET("/client/liste")
    suspend fun getListeClient(): List<Client>

    /**
     * TODO
     *
     * @return
     */
    @GET("client/liste")
    suspend fun getUsers(): List<User>


    /**
     * TODO
     *
     * @param id
     * @return
     */
    @GET("/client/{id}")
    suspend fun getUser(@Path("id") id: Int): List<User>

    /**
     * TODO
     *
     * @return
     */
    @GET("/information/liste")
    suspend fun getInfos(): List<Information>

    /**
     * TODO
     *
     * @param id
     * @return
     */
    @DELETE("client/delete/{id}")
    suspend fun deleteUser(@Path("id") id: Int): String

    /**
     * TODO
     *
     * @param id
     * @param userData
     * @return
     */
    @PUT("client/edit/{id}")
    suspend fun editUser(@Path("id") id: Int,@Body userData: ServiceClient.UserInfo): List<User>


    /**
     * TODO
     *
     * @param id_info
     * @return
     */
    @GET("/information/{id_info}")
    suspend fun getUneInfo(@Path("id_info") id_info: Int): List<Information>

    /**
     * TODO
     *
     * @param id
     * @return
     */
    @DELETE("information/delete/{id}")
    suspend fun deleteInfo(@Path("id") id: Int): String

    /**
     * TODO
     *
     * @param infoData
     * @return
     */
    @POST("/information/new")
    suspend fun postinformation(@Body infoData: ServiceInformation.EnregInfo) : Information

    /**
     * TODO
     *
     * @param id
     * @param info
     * @return
     */
    @PUT("information/edit/{id}")
    suspend fun editInfo(@Path("id") id: Int,@Body info: Information): List<Information>

    companion object {
        /**
         * Création d'un singleton pour la simplicité, mais normalement nous utilisons plutôt un
         * injecteur de dépendances.
         */
        val instance = build()

        /**
         * Création de l'objet qui nous permettra de faire les appels d'API
         */
        private fun build(): ApiService {
            val gson = GsonBuilder().create() // JSON deserializer/serializer

            // Create the OkHttp Instance
            val okHttpClient = OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
                    .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                        val request =
                            chain.request().newBuilder().addHeader("Accept", "application/json")
                                .build()

                        chain.proceed(request)
                    })
                    .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                        val request =
                            chain.request().newBuilder().addHeader(
                                "Authorization", LocalPreferences.getInstance(
                                    MyApp.context!!
                                ).getToken().toString()
                            ).build()
                        chain.proceed(request)
                    })

                    .build()


            return Retrofit.Builder()
                    .baseUrl("http://localhost:8083")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(ApiService::class.java)
        }
    }
}