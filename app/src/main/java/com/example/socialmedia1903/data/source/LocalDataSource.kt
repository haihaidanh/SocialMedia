package com.example.socialmedia1903.data.source

import com.example.socialmedia1903.data.local.ImageDao
import com.example.socialmedia1903.data.local.ImageEntity
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