package com.example.dikshatest.di

import com.example.dikshatest.data.remote.apiservice.MovieService
import com.example.dikshatest.data.remote.repository.MovieRepository
import com.example.dikshatest.data.remote.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class MovieModule {

    @Provides
    @Singleton
    @Named("movie_base_url")
    fun provideMovieBaseUrl() : String {
        return "https://api.themoviedb.org/"
    }


    @Provides
    @Singleton
    fun provideHttpLogging() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("movie_base_url") baseUrl: String,
        okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit) : MovieService {
        return retrofit.create(MovieService::class.java)
    }


    @Provides
    @Singleton
    fun provideMovieRepository(apiService: MovieService) : MovieRepository {
        return MovieRepositoryImpl(apiService)
    }



}