package com.example.socialmedia1903.data.source

import com.example.socialmedia1903.data.local.room.ImageDao
import com.example.socialmedia1903.data.local.room.ImageEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val imageDao: ImageDao
) {
    suspend fun insertImage(uri: String) {
        val imageEntity = ImageEntity(uri)
        imageDao.insertImage(imageEntity)
    }

    suspend fun getImages(): List<ImageEntity> {
        return imageDao.getImages()
    }

    suspend fun deleteAllImages() {
        imageDao.deleteAllImages()
    }
}