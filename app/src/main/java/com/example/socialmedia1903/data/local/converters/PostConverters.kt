package com.example.socialmedia1903.data.local.converters

import androidx.room.TypeConverter
import com.example.socialmedia1903.domain.enums.PostType
import com.example.socialmedia1903.domain.enums.PostVisibility
import com.example.socialmedia1903.domain.model.Comment
import com.example.socialmedia1903.domain.model.GroupInfo
import com.example.socialmedia1903.domain.model.Like
import com.example.socialmedia1903.domain.model.Media
import com.example.socialmedia1903.domain.model.User
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.util.Date

class PostConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromDate(date: Date): Long = date.time
    @TypeConverter
    fun toDate(millis: Long): Date = Date(millis)

    @TypeConverter
    fun fromPostType(type: PostType): String = type.name
    @TypeConverter
    fun toPostType(value: String): PostType = PostType.valueOf(value)

    @TypeConverter
    fun fromVisibility(v: PostVisibility): String = v.name
    @TypeConverter
    fun toVisibility(value: String): PostVisibility = PostVisibility.valueOf(value)

    @TypeConverter
    fun fromUser(user: User): String = gson.toJson(user)
    @TypeConverter
    fun toUser(json: String): User = gson.fromJson(json, User::class.java)

    @TypeConverter
    fun fromComments(list: List<Comment>): String = gson.toJson(list)
    @TypeConverter
    fun toComments(json: String): List<Comment> =
        gson.fromJson(json, object : TypeToken<List<Comment>>() {}.type)

    @TypeConverter
    fun fromLikes(list: List<Like>): String = gson.toJson(list)
    @TypeConverter
    fun toLikes(json: String): List<Like> =
        gson.fromJson(json, object : TypeToken<List<Like>>() {}.type)

    @TypeConverter
    fun fromMedia(list: List<Media>): String = gson.toJson(list)
    @TypeConverter
    fun toMedia(json: String): List<Media> =
        gson.fromJson(json, object : TypeToken<List<Media>>() {}.type)

    @TypeConverter
    fun fromGroupInfo(group: GroupInfo?): String? = group?.let { gson.toJson(it) }
    @TypeConverter
    fun toGroupInfo(json: String?): GroupInfo? =
        json?.let { gson.fromJson(it, GroupInfo::class.java) }
}