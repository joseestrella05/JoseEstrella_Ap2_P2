package edu.ucne.jose_estrella_ap2_p2.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.jose_estrella_ap2_p2.domain.repository.GastoRepository
import edu.ucne.jose_estrella_ap2_p2.domain.use_case.GetGastosUseCase
import edu.ucne.jose_estrella_ap2_p2.domain.use_case.PostGastoUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGetGastosUseCase(repository: GastoRepository): GetGastosUseCase {
        return GetGastosUseCase(repository)
    }

    @Provides
    @Singleton
    fun providePostGastoUseCase(repository: GastoRepository): PostGastoUseCase {
        return PostGastoUseCase(repository)
    }
}
