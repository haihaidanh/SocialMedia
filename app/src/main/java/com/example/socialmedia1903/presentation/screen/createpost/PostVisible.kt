package com.example.socialmedia1903.presentation.screen.createpost

import com.example.socialmedia1903.R
import com.example.socialmedia1903.domain.enums.PostVisibility

data class PostVisible(
    val label: Int,
    val icon: Int,
    val enum: PostVisibility
)

val postVisibleList = listOf(
    PostVisible(
        label = R.string.publics,
        icon = R.drawable.public_icon,
        enum = PostVisibility.PUBLIC
    ),
    PostVisible(
        label = R.string.friends,
        icon = R.drawable.people_icon,
        enum = PostVisibility.FRIENDS
    ),
    PostVisible(
        label = R.string.privates,
        icon = R.drawable.password,
        enum = PostVisibility.PRIVATE
    )
)