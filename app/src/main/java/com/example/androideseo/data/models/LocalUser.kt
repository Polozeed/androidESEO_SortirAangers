package com.example.androideseo.data.models

data class LocalUser(
        var id_client: Int,
        var login: String,
        var mdp: String,
        var token: String

) {
    fun identity(): String {
        return "$login $mdp"
    }

    fun identity_user(): String {
        return "$login $mdp"
    }

    fun identity_password(): String {
        return "$login $mdp"
    }
}