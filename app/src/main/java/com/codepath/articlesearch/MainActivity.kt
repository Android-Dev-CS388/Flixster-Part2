package com.codepath.articlesearch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.articlesearch.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val SEARCH_API_KEY = BuildConfig.API_KEY
private const val MOVIE_SEARCH_URL =
    "https://api.themoviedb.org/3/movie/popular?api_key=${SEARCH_API_KEY}"

class MainActivity : AppCompatActivity() {
    private val movies = mutableListOf<Movie>()
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        moviesRecyclerView = findViewById(R.id.movies)
        // TODO: Set up ArticleAdapter with articles
        val movieAdapter = MovieAdapter(this, movies)
        moviesRecyclerView.adapter = movieAdapter

        moviesRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            moviesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        val client = AsyncHttpClient()
        client.get(MOVIE_SEARCH_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json")
                try {
                    // TODO: Create the parsedJSON

                    // TODO: Do something with the returned json (contains article information)
                    val parsedJson = createJson().decodeFromString(
                        SearchNewsResponse.serializer(),
                        json.jsonObject.toString()
                    )
                    // TODO: Save the articles and reload the screen
                    parsedJson.results?.let { list ->
                        Log.i(TAG, list.toString())
                        movies.addAll(list)
                        movieAdapter.notifyDataSetChanged()
                    }
                    Log.i(TAG, "Successfully fetched articles: $parsedJson")

//                    val resultsJSON : JSONArray = json.jsonObject.get("results") as JSONArray
//                    val moviesRawJSON : String = resultsJSON.toString()
//                    val gson = Gson()
//                    val arrayMovieType = object : TypeToken<List<Movie>>() {}.type
//                    val models : List<Movie> = gson.fromJson(moviesRawJSON, arrayMovieType)
//                    recyclerView.adapter = MoviesRecyclerViewAdapter(models, this@MoviesFragment)



                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }

        })

    }
}