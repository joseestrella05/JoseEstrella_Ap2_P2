package edu.ucne.jose_estrella_ap2_p2.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.jose_estrella_ap2_p2.data.remote.api.GastoApiServices
import edu.ucne.jose_estrella_ap2_p2.data.repository.GastoRepositoryImpl
import edu.ucne.jose_estrella_ap2_p2.domain.repository.GastoRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "https://gestionhuacalesapi.azurewebsites.net/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideGastoApiService(retrofit: Retrofit): GastoApiServices =
        retrofit.create(GastoApiServices::class.java)

    @Provides
    @Singleton
    fun provideGastoRepository(apiService: GastoApiServices): GastoRepository =
        GastoRepositoryImpl(apiService)
}