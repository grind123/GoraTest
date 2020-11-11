package com.grind.goratest

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

//    @Test
//    fun pictureLoaderTest(){
//        val loader = PictureLoader()
//        val stubPhoto = Photo(1, 1, "title", "mock", "https://via.placeholder.com/600/d32776")
//        val byteArray = loader.getPicture(stubPhoto)
////        print("Picture size = ${byteArray?.size}")
//    }
}