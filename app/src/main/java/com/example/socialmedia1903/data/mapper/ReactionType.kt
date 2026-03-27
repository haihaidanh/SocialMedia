package com.example.socialmedia1903.data.mapper

import com.example.socialmedia1903.R

enum class ReactionType(val title: String, val icon: Int) {
    UNLIKE("unlike", R.drawable.like),
    LIKE("like", R.drawable.like_done),
    LOVE("love", R.drawable.love),
    HAHA("haha", R.drawable.haha),
    WOW("wow", R.drawable.wow),
    SAD("sad", R.drawable.sad),
    ANGRY("angry", R.drawable.angry)
}