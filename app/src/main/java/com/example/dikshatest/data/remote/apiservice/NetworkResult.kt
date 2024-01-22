package com.example.dikshatest.data.remote.apiservice

sealed class NetworkResult<out T>

class SuccessResult<out T>(val data: T?) : NetworkResult<T>()

class ErrorResult(val message: String?) : NetworkResult<Nothing>()