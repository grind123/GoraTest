package com.grind.goratest.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import android.webkit.WebView
import androidx.core.content.ContextCompat
import com.grind.goratest.App
import com.grind.goratest.db.SqlDbHelper
import com.grind.goratest.models.Photo
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class PictureLoader(private val activity: Activity) {

    private fun loadPictureFromUrl(url: String): ByteArray?{
        var result: ByteArray? = null
        val userAgentString = WebView(activity).settings.userAgentString
        val thread = Thread(Runnable {
            val connection = URL(url).openConnection() as HttpsURLConnection
            connection.setRequestProperty("User-Agent",
            userAgentString)
            connection.connect()
            if (connection.responseCode == HttpsURLConnection.HTTP_OK) {
                result = connection.inputStream.readBytes()
            }
        })
        thread.start()
        thread.join()
        return result
    }

    private fun decodeBitmapImage(byteArray: ByteArray): Bitmap{
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
    private fun decodeBitmapImage(file: File): Bitmap{
        return BitmapFactory.decodeFile(file.absolutePath)
    }

    private fun savePictureToCache(byteArray: ByteArray): File {
        val fileName = "${System.currentTimeMillis()}_image.jpg"
        val folder = File("${activity.externalCacheDir?.absolutePath}/photos")
        folder.mkdirs()
        val file = File("${folder.absolutePath}/${fileName}")
        file.createNewFile()
        file.writeBytes(byteArray)
        return file
    }


    private fun saveInfoToDb(photoId: Int, title: String, filePath: String) : Long{
        val dbHelper = SqlDbHelper(activity)
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put("_id", photoId)
            put("title", title)
            put("imageFilePath", filePath)
        }

        val photoId = db.insert(SqlDbHelper.MAIN_TABLE_NAME, null, contentValues)
        db.close()

        return photoId
    }

    private fun getPictureFileFromCache(photoId: Int): File?{
        val dbHelper = SqlDbHelper(activity)
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "select * from ${SqlDbHelper.MAIN_TABLE_NAME} where _id = ?",
            arrayOf(photoId.toString())
        )
        if(cursor.count > 0){
            cursor.moveToFirst()
            val filePath = cursor.getString(2)
            cursor.close()
            db.close()
            return File(filePath)
        } else {
            cursor.close()
            db.close()
            return null
        }
    }



    fun getPicture(photo: Photo): Bitmap?{
        val fromCache = getPictureFileFromCache(photo.id)
        if(fromCache != null){
            Log.i("PictureLoader", "FROM CACHE")
            return decodeBitmapImage(fromCache)
        }
        val pictureByteArray = loadPictureFromUrl(photo.url)
        if(pictureByteArray != null){
            val pictureFile = savePictureToCache(pictureByteArray)
            saveInfoToDb(photo.id, photo.title, pictureFile.absolutePath)
            Log.i("PictureLoader", "FROM WEB")
            return decodeBitmapImage(pictureByteArray)
        } else {
            return null
        }
    }


}