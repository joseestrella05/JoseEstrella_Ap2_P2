package edu.ucne.jose_estrella_ap2_p2.remote.worker

sealed class Resource<T>(val data: T? = null, val message: String? = null){
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String, data: T? = null ): Resource<T>(data,message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}