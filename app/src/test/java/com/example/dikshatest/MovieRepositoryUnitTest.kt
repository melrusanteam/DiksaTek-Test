package com.example.dikshatest

import com.example.dikshatest.data.remote.apiservice.ErrorResult
import com.example.dikshatest.data.remote.apiservice.MovieService
import com.example.dikshatest.data.remote.apiservice.SuccessResult
import com.example.dikshatest.data.remote.model.DetailResponse
import com.example.dikshatest.data.remote.model.GenreModel
import com.example.dikshatest.data.remote.model.GenreResponse
import com.example.dikshatest.data.remote.model.MovieModel
import com.example.dikshatest.data.remote.model.MovieResponse
import com.example.dikshatest.data.remote.model.ReviewResponse
import com.example.dikshatest.data.remote.model.TrailerResponse
import com.example.dikshatest.data.remote.repository.MovieRepository
import com.example.dikshatest.data.remote.repository.MovieRepositoryImpl
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryUnitTest {


    @Mock
    lateinit var movieService: MovieService

    lateinit var movieRespository: MovieRepository

    val apiKey = "12345"
    var withGenres = 1


    @Before
    fun setup() {
        movieRespository = MovieRepositoryImpl(movieService, apiKey)
    }


    @Test
    fun testPopularMovies() = runBlocking {
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

        movieRespository.popularMovies(1).collect { result ->
            when (result) {
                is SuccessResult -> {
                    val movies = result.data
                    assertEquals(movieResponse.body()?.results, movies)
                }

                is ErrorResult -> {

                }
            }
        }
    }

    @Test
    fun popularMovieNegativeCase() = runBlocking {

        val response: Response<MovieResponse> = Response.error(
            404,
            "Not Found".toResponseBody("text/plain".toMediaTypeOrNull())
        )
        `when`(movieService.popularMovie(1, apiKey)).thenReturn(response)

        movieRespository.popularMovies(1).collect { result ->
            when (result) {
                is SuccessResult -> {
                }

                is ErrorResult -> {
                    val errorMessage = result.message ?: "Unknown error"
                    assertEquals("Failed load movies", errorMessage)
                }
            }
        }
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

        movieRespository.discoverMovie(1, withGenres).collect { result ->
            when (result) {
                is SuccessResult -> {
                    val movies = result.data
                    assertEquals(movieResponse.body()?.results, movies)
                }

                is ErrorResult -> {

                }
            }
        }

    }

    @Test
    fun discoverMovieNegativeCase() = runBlocking {

        val response: Response<MovieResponse> = Response.error(
            404,
            "Not Found".toResponseBody("text/plain".toMediaTypeOrNull())
        )
        `when`(movieService.discoverMovie(1, withGenres, apiKey)).thenReturn(response)

        movieRespository.discoverMovie(1, withGenres).collect { result ->
            when (result) {
                is SuccessResult -> {
                }

                is ErrorResult -> {
                    val errorMessage = result.message ?: "Unknown error"
                    assertEquals("Failed load movies", errorMessage)

                }
            }
        }

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

        movieRespository.detailMovie("1").collect { result ->
            when (result) {
                is SuccessResult -> {
                    assertEquals(detailResponse.body()?.id, 123456)
                }

                is ErrorResult -> {
                }
            }
        }
    }

    @Test
    fun detailMovieNegativeCase() = runBlocking {

        val detailResponse: Response<DetailResponse> = Response.error(
            404,
            "Not Found".toResponseBody("text/plain".toMediaTypeOrNull())
        )
        `when`(movieService.detailMovie("1", apiKey)).thenReturn(detailResponse)

        movieRespository.detailMovie("1").collect { result ->
            when (result) {
                is SuccessResult -> {
                }

                is ErrorResult -> {
                    val errorMessage = result.message ?: "Unknown error"
                    assertEquals("Failed load detail", errorMessage)
                }
            }
        }
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

        movieRespository.reviewMovie("1", 1).collect { result ->
            when (result) {
                is SuccessResult -> {
                    assertEquals(reviewResponse.body()?.id, "1")
                }

                is ErrorResult -> {

                }
            }
        }

    }

    @Test
    fun reviewMovieNegativeCase() = runBlocking {

        val reviewResponse: Response<ReviewResponse> = Response.error(
            404,
            "Not Found".toResponseBody("text/plain".toMediaTypeOrNull())
        )
        `when`(movieService.reviewMovie("1", 1, apiKey)).thenReturn(reviewResponse)

        movieRespository.reviewMovie("1", 1).collect { result ->
            when (result) {
                is SuccessResult -> {
                }

                is ErrorResult -> {
                    val errorMessage = result.message ?: "Unknown error"
                    assertEquals("Failed load review", errorMessage)
                }
            }
        }
    }


    @Test
    fun trailerMoviePositiveCase() = runBlocking {

        val trailerResponse: Response<TrailerResponse> = Response.success(
            TrailerResponse(1)
        )
        `when`(movieService.trailerMovie("1",  apiKey)).thenReturn(trailerResponse)

        movieRespository.trailerMovie("1").collect { result ->
            when (result) {
                is SuccessResult -> {
                    assertEquals(trailerResponse.body()?.id, 1)
                }

                is ErrorResult -> {

                }
            }
        }

    }

    @Test
    fun trailerMovieNegativeCase() = runBlocking {

        val trailerResponse: Response<TrailerResponse> = Response.error(
            404,
            "Not Found".toResponseBody("text/plain".toMediaTypeOrNull())
        )
        `when`(movieService.trailerMovie("1",apiKey)).thenReturn(trailerResponse)

        movieRespository.trailerMovie("1").collect { result ->
            when (result) {
                is SuccessResult -> {
                }

                is ErrorResult -> {
                    val errorMessage = result.message ?: "Unknown error"
                    assertEquals("Failed load trailer", errorMessage)
                }
            }
        }
    }


    @Test
    fun genreMoviePositiveCase() = runBlocking {

        val genreResponse : Response<GenreResponse> = Response.success(
            GenreResponse(
                listOf(
                    GenreModel(
                        1,
                        "action"
                    )
                )
            )
        )
        `when`(movieService.genreMovie( apiKey)).thenReturn(genreResponse)

        movieRespository.getGenreMovie().collect { result ->
            when (result) {
                is SuccessResult -> {
                    assertEquals(genreResponse.body()?.genres?.first()?.id, 1)
                }

                is ErrorResult -> {

                }
            }
        }
    }

    @Test
    fun genreMovieNegativeCase() = runBlocking {

        val genresponse : Response<GenreResponse> = Response.error(
            404,
            "Not Found".toResponseBody("text/plain".toMediaTypeOrNull())
        )
        `when`(movieService.genreMovie(apiKey)).thenReturn(genresponse)

        movieRespository.getGenreMovie().collect { result ->
            when (result) {
                is SuccessResult -> {
                }

                is ErrorResult -> {
                    val errorMessage = result.message ?: "Unknown error"
                    assertEquals("Failed load genre", errorMessage)
                }
            }
        }
    }
}
