package com.example.calefit.data

sealed class Aggregate<out R> {

    data class Success<out T>(val data: T) : Aggregate<T>()
    data class Error(val exception: Exception) : Aggregate<Nothing>()
    object Loading : Aggregate<Nothing>()
}