package com.example.androideseo.service

import android.net.Uri
import com.google.gson.annotations.SerializedName


class ServicePhoto {

    data class photoInfo(
        @SerializedName("mdp") val mdp: String,
        @SerializedName("login") val login: String
    )


   //suspend fun postPhoto(photo: Uri?): String {
     //   val api = ApiService.instance.upload(photo)
       // return api
    //}


    // ompanion object {
    //    val instance = build()

      //  private fun build(): ServicePhoto {
        //    return ServicePhoto()
        //}
    //}


}