package com.example.dikshatest

import com.example.dikshatest.data.remote.apiservice.MovieService
import com.example.dikshatest.data.remote.model.MovieModel
import com.example.dikshatest.data.remote.model.MovieResponse
import com.example.dikshatest.data.remote.repository.MovieRepository
import com.example.dikshatest.data.remote.repository.MovieRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {


    @Mock
    lateinit var movieService: MovieService

    lateinit var movieRepository: MovieRepository

    @Before
    fun setup() {
        movieRepository = MovieRepositoryImpl(movieService)
    }
    @Test
    fun movieList() = runBlocking {

        val movieResponse: Response<MovieResponse> = Response.success(MovieResponse(page = 1, listOf(
            MovieModel(
                title = "title",
                posterPath = "poster",
                backdropPath = "backdrop",
                voteAverage = 1.0,
                voteCount = 1.2
            )
        )))
        `when`(movieService.popularMovie("123")).thenReturn(movieResponse)


        val response = movieService.popularMovie("123")

        assertEquals(true, response.isSuccessful)
        assertEquals(1, response.body()?.page)


    }
}