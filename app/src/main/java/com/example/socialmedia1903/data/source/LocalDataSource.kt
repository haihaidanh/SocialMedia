package com.example.socialmedia1903.data.source

import com.example.socialmedia1903.data.local.ImageDao
import com.example.socialmedia1903.data.local.ImageEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val imageDao: ImageDao
) {
    suspend fun insertImage(uri: String, postId: String) {
        val imageEntity = ImageEntity(uri, postId)
        imageDao.insertImage(imageEntity)
    }

    suspend fun getImagesByPostId(postId: String): List<ImageEntity> {
        return imageDao.getImagesByPostId(postId)
    }

    suspend fun deleteAllImages() {
        imageDao.deleteAllImages()
    }
}