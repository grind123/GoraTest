package com.grind.goratest.repositories

import android.util.Log
import com.grind.goratest.models.Album
import com.grind.goratest.models.Picture
import com.grind.goratest.models.User
import com.grind.goratest.utils.MySSLVerifier
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.nio.charset.Charset
import javax.net.ssl.HttpsURLConnection

class DataRepository {

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }

    private fun configureHttpsConnection(url: String): HttpsURLConnection{
        return (URL(url).openConnection() as HttpsURLConnection).apply {
            hostnameVerifier = MySSLVerifier()
        }
    }




    private fun parseUsers(jsonData: String): List<User>{
        val resultList = mutableListOf<User>()
        val usersJsonArray = JSONArray(jsonData)
        for(i in 0 until usersJsonArray.length()){
            val userJsonObject = usersJsonArray.get(i) as JSONObject
            val user = User(
                id = userJsonObject.getInt("id"),
                name = userJsonObject.getString("name"),
                userName = userJsonObject.getString("username"),
                email = userJsonObject.getString("email"),
                phone = userJsonObject.getString("phone"),
                website = userJsonObject.getString("website")
            )
            resultList.add(user)
        }
        return resultList
    }

    private fun parseAlbums(jsonData: String): List<Album>{
        val resultList = mutableListOf<Album>()
        val usersJsonArray = JSONArray(jsonData)
        for(i in 0 until usersJsonArray.length()){
            val albumJsonObject = usersJsonArray.get(i) as JSONObject
            val album = Album(
                id = albumJsonObject.getInt("id"),
                title = albumJsonObject.getString("title"),
                userId = albumJsonObject.getInt("userId")
            )
            resultList.add(album)
        }
        return resultList
    }

    private fun parsePhotos(jsonData: String): List<Picture>{
        val resultList = mutableListOf<Picture>()
        val usersJsonArray = JSONArray(jsonData)
        for(i in 0 until usersJsonArray.length()){
            val albumJsonObject = usersJsonArray.get(i) as JSONObject
            val album = Picture(
                id = albumJsonObject.getInt("id"),
                title = albumJsonObject.getString("title"),
                url = albumJsonObject.getString("url"),
                thumbnailUrl = albumJsonObject.getString("thumbnailUrl"),
                albumId = albumJsonObject.getInt("albumId")
            )
            resultList.add(album)
        }
        return resultList
    }


    fun getUsersList(): List<User>? {
        var resultList: List<User>? = null
        val thread = Thread(Runnable {
            val connection = configureHttpsConnection("$BASE_URL/users")
            connection.connect()
            if (connection.responseCode == HttpsURLConnection.HTTP_OK) {
                val response = connection.inputStream.readBytes().toString(Charset.defaultCharset())
                Log.i("URL", "$response")
                resultList = parseUsers(response)
            }

        })
        thread.start()
        thread.join()
        return resultList
    }

    fun getAlbumsListByUserId(userId: Int): List<Album>? {
        var resultList: List<Album>? = null
        val thread = Thread(Runnable {
            val connection = configureHttpsConnection("$BASE_URL/albums?userId=$userId")
            connection.connect()
            if (connection.responseCode == HttpsURLConnection.HTTP_OK) {
                val response = connection.inputStream.readBytes().toString(Charset.defaultCharset())
                Log.i("URL", "$response")
                resultList = parseAlbums(response)
            }
        })
        thread.start()
        thread.join()
        return resultList
    }

    fun getPhotosListByAlbumId(albumId: Int): List<Picture>? {
        var resultList: List<Picture>? = null
        val thread = Thread(Runnable {
            val connection = configureHttpsConnection("$BASE_URL/photos?albumId=$albumId")
            connection.connect()
            if (connection.responseCode == HttpsURLConnection.HTTP_OK) {
                val response = connection.inputStream.readBytes().toString(Charset.defaultCharset())
                Log.i("URL", "$response")
                resultList = parsePhotos(response)
            }
        })
        thread.start()
        thread.join()
        return resultList
    }



}