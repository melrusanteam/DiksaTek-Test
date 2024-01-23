package com.example.dikshatest

import com.example.dikshatest.data.remote.apiservice.MovieService
import com.example.dikshatest.data.remote.model.DetailResponse
import com.example.dikshatest.data.remote.model.GenreResponse
import com.example.dikshatest.data.remote.model.MovieModel
import com.example.dikshatest.data.remote.model.MovieResponse
import com.example.dikshatest.data.remote.model.ReviewResponse
import com.example.dikshatest.data.remote.model.TrailerResponse
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@RunWith(MockitoJUnitRunner::class)
class MovieServiceUnitTest {


    @Mock
    lateinit var movieService: MovieService

    val apiKey = "12345"
    var withGenres = 1

    @Test
    fun popularMoviePositiveCase() = runBlocking {

        val movieResponse: Response<MovieResponse> = Response.success(
            MovieResponse(
                page = 1, listOf(
                    MovieModel(
                        title = "title",
                        posterPath = "poster",
                        backdropPath = "backdrop",
                        voteAverage = 1.0,
                        voteCount = 1.2,
                        id = 1323,
                        releaseDate = "12/12/12"
                    )
                )
            )
        )
        `when`(movieService.popularMovie(1, apiKey)).thenReturn(movieResponse)

        val response = movieService.popularMovie(1, apiKey)

        assertEquals(true, response.isSuccessful)
        assertEquals(1, response.body()?.page)
        assertEquals(1, response.body()?.results?.size)
        assertEquals(movieResponse, response)

    }

    @Test
    fun popularMovieNegativeCase() = runBlocking {

        val response: Response<MovieResponse> = Response.error(
            404,
            "Not Found".toResponseBody("text/plain".toMediaTypeOrNull())
        )
        `when`(movieService.popularMovie(1, apiKey)).thenReturn(response)

        val result = movieService.popularMovie(1, apiKey)

        assertEquals(404, result.code())
        assertEquals(false, result.isSuccessful)

    }

    @Test
    fun discoverMoviePositiveCase() = runBlocking {

        val movieResponse: Response<MovieResponse> = Response.success(
            MovieResponse(
                page = 1, listOf(
                    MovieModel(
                        title = "title",
                        posterPath = "poster",
                        backdropPath = "backdrop",
                        voteAverage = 1.0,
                        voteCount = 1.2,
                        id = 1323,
                        releaseDate = "12/12/12"
                    )
                )
            )
        )
        `when`(movieService.discoverMovie(1, withGenres, apiKey)).thenReturn(movieResponse)

        val response = movieService.discoverMovie(1, withGenres, apiKey)

        assertEquals(true, response.isSuccessful)
        assertEquals(movieResponse, response)

    }

    @Test
    fun discoverMovieNegativeCase() = runBlocking {

        val response: Response<MovieResponse> = Response.error(
            404,
            "Not Found".toResponseBody("text/plain".toMediaTypeOrNull())
        )
        `when`(movieService.discoverMovie(1, withGenres, apiKey)).thenReturn(response)

        val result = movieService.discoverMovie(1, withGenres, apiKey)

        assertEquals(404, result.code())
        assertEquals(false, result.isSuccessful)

    }


    @Test
    fun detailMoviePositiveCase() = runBlocking {

        val detailResponse: Response<DetailResponse> = Response.success(
            DetailResponse(
                id = 123456,
                title = "title"
            )
        )
        `when`(movieService.detailMovie("1", apiKey)).thenReturn(detailResponse)

        val result = movieService.detailMovie("1", apiKey)

        assertEquals(true, result.isSuccessful)
        assertEquals(detailResponse, result)
        assertEquals(detailResponse.code(), result.code())

    }

    @Test
    fun detailMovieNegativeCase() = runBlocking {

        val detailResponse: Response<DetailResponse> = Response.error(
            404,
            "Not Found".toResponseBody("text/plain".toMediaTypeOrNull())
        )
        `when`(movieService.detailMovie("1", apiKey)).thenReturn(detailResponse)

        val result = movieService.detailMovie("1", apiKey)

        assertEquals(404, result.code())
        assertEquals(false, result.isSuccessful)
        assertEquals(detailResponse.message(), result.message())
    }


    @Test
    fun reviewMoviePositiveCase() = runBlocking {

        val reviewResponse: Response<ReviewResponse> = Response.success(
            ReviewResponse(
                id = "1",
                page = 1
            )
        )
        `when`(movieService.reviewMovie("1", 1, apiKey)).thenReturn(reviewResponse)

        val result = movieService.reviewMovie("1", 1, apiKey)

        assertEquals(true, result.isSuccessful)
        assertEquals(reviewResponse, result)
        assertEquals(reviewResponse.code(), result.code())

    }

    @Test
    fun reviewMovieNegativeCase() = runBlocking {

        val reviewResponse: Response<ReviewResponse> = Response.error(
            404,
            "Not Found".toResponseBody("text/plain".toMediaTypeOrNull())
        )
        `when`(movieService.reviewMovie("1", 1, apiKey)).thenReturn(reviewResponse)

        val result = movieService.reviewMovie("1", 1, apiKey)

        assertEquals(404, result.code())
        assertEquals(false, result.isSuccessful)
        assertEquals(reviewResponse.message(), result.message())
    }


    @Test
    fun trailerMoviePositiveCase() = runBlocking {

        val trailerResponse: Response<TrailerResponse> = Response.success(
            TrailerResponse(1)
        )
        `when`(movieService.trailerMovie("1",  apiKey)).thenReturn(trailerResponse)

        val result = movieService.trailerMovie("1", apiKey)

        assertEquals(true, result.isSuccessful)
        assertEquals(trailerResponse, result)
        assertEquals(trailerResponse.code(), result.code())

    }

    @Test
    fun trailerMovieNegativeCase() = runBlocking {

        val trailerResponse: Response<TrailerResponse> = Response.error(
            404,
            "Not Found".toResponseBody("text/plain".toMediaTypeOrNull())
        )
        `when`(movieService.trailerMovie("1",apiKey)).thenReturn(trailerResponse)

        val result = movieService.trailerMovie("1", apiKey)

        assertEquals(404, result.code())
        assertEquals(false, result.isSuccessful)
        assertEquals(trailerResponse.message(), result.message())
    }


    @Test
    fun genreMoviePositiveCase() = runBlocking {

        val genreResponse : Response<GenreResponse> = Response.success(
            GenreResponse()
        )
        `when`(movieService.genreMovie( apiKey)).thenReturn(genreResponse)

        val result = movieService.genreMovie(apiKey)

        assertEquals(true, result.isSuccessful)
        assertEquals(genreResponse, result)
        assertEquals(genreResponse.code(), result.code())

    }

    @Test
    fun genreMovieNegativeCase() = runBlocking {

        val genresponse : Response<GenreResponse> = Response.error(
            404,
            "Not Found".toResponseBody("text/plain".toMediaTypeOrNull())
        )
        `when`(movieService.genreMovie(apiKey)).thenReturn(genresponse)

        val result = movieService.genreMovie(apiKey)

        assertEquals(404, result.code())
        assertEquals(false, result.isSuccessful)
        assertEquals(genresponse.message(), result.message())
    }

}