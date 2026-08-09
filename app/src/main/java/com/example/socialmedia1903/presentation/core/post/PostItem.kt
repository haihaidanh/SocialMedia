package com.example.socialmedia1903.presentation.core.post

import android.media.MediaMetadataRetriever
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialmedia1903.R
import com.example.socialmedia1903.data.utils.AppUtils.fontOpenSansMediumHelper
import com.example.socialmedia1903.domain.enums.PostType
import com.example.socialmedia1903.domain.enums.PostVisibility
import com.example.socialmedia1903.domain.enums.ReactionType
import com.example.socialmedia1903.domain.model.Media
import com.example.socialmedia1903.domain.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.graphics.toColorInt

@Composable
fun PostItemView(
    post: Post,
    userId: String,
    modifier: Modifier,
    onCommentClick: () -> Unit,
    showReactions: Boolean,
    onLikePost: (postId: String, type: String) -> Unit,
    onShowReact: (postId: String?, show: Boolean) -> Unit,
    onGroupClick: () -> Unit = {},
    onPostClick: () -> Unit = {},
    onUserClick: () -> Unit = {}
) {

    var visibility by remember { mutableStateOf(post.visibility) }

    when (visibility) {
        PostVisibility.PUBLIC -> {
            PostItem(
                post = post,
                userId = userId,
                deleteClick = {
                    visibility = PostVisibility.DELETED
                },
                onShareClick = { postId ->

                },
                showReactions = showReactions,
                onLikePost = { postId, type ->
                    onLikePost(postId, type)
                },
                onCommentClick = {
                    onCommentClick()
                },
                onShowReact = { postId, show ->
                    onShowReact(postId, show)
                },
                modifier = modifier,
                onUserClick = onUserClick,
                onPostClick = onPostClick,
                onGroupClick = onGroupClick
            )
        }

        PostVisibility.FRIENDS -> {
            PostItem(
                post = post,
                userId = userId,
                deleteClick = {
                    visibility = PostVisibility.DELETED
                },
                onShareClick = { postId ->

                },
                showReactions = showReactions,
                onLikePost = { postId, type ->
                    onLikePost(postId, type)
                },
                onCommentClick = {
                    onCommentClick()
                },
                onShowReact = { postId, show ->
                    onShowReact(postId, show)
                },
                modifier = modifier,
                onUserClick = onUserClick,
                onPostClick = onPostClick,
                onGroupClick = onGroupClick
            )
        }

        PostVisibility.PRIVATE -> {

        }

        PostVisibility.DELETED -> {
            UndoItem(
                text = stringResource(R.string.delete_successfully),
                onUndoClick = {
                    visibility = PostVisibility.PUBLIC
                }
            )
        }
    }
}

@Composable
fun PostItem(
    post: Post,
    userId: String,
    deleteClick: () -> Unit,
    onShareClick: (postId: String) -> Unit,
    showReactions: Boolean,
    onLikePost: (postId: String, type: String) -> Unit,
    onCommentClick: () -> Unit,
    onShowReact: (postId: String?, show: Boolean) -> Unit,
    modifier: Modifier,
    onGroupClick: () -> Unit = {},
    onPostClick: () -> Unit = {},
    onUserClick: () -> Unit = {}
) {

    var likeCount by remember { mutableIntStateOf(post.likeCount) }

    val context = LocalContext.current

    val icon = if (post.likes.isNotEmpty()) {
        getIcon(post.likes[0].type)
    } else {
        R.drawable.like
    }
    var likeIcon by remember { mutableIntStateOf(icon) }
    var isLike by remember { mutableStateOf(post.likes.isNotEmpty()) }
    val shareCount by remember { mutableIntStateOf(post.sharedCount) }

    var likeJob by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            PostHeader(
                post = post,
                modifier = Modifier
                    .padding(10.dp),
                userId = userId,
                onEdit = {

                },
                onDelete = {
                },
                onStore = {

                },
                onPostClick = {
                    onPostClick()
                },
                onUserClick = {
                    onUserClick()
                },
                onGroupClick = {
                    onGroupClick()
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                when (post.type) {
                    PostType.MEDIA -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {
                            post.content?.let {
                                Text(
                                    text = it,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    color = Color.Black,
                                    fontFamily = fontOpenSansMediumHelper()
                                )
                            }
                            ImageSlider(
                                media = post.media,
                                modifier = Modifier
                            )
                        }
                    }

                    PostType.TEXT -> {

                        Text(
                            text = post.content ?: "",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .then(
                                    if (post.background != null) {
                                        Modifier
                                            .height(300.dp)
                                            .background(
                                                color = Color(post.background.toColorInt())
                                            )
                                            .wrapContentHeight(Alignment.CenterVertically)
                                    } else {
                                        Modifier.wrapContentHeight()
                                    }
                                )
                                .padding(start = 10.dp),
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = if (post.background != null) TextAlign.Center else TextAlign.Start
                        )
                    }

                    PostType.VIDEO -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {
                            post.content?.let {
                                Text(
                                    text = it,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    color = Color.Black,
                                    fontFamily = fontOpenSansMediumHelper()
                                )
                            }
                            VideoContent(
                                video = post.media[0],
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                            )
                        }
                    }

                    PostType.IMAGE -> {
                        // Handle image post type
                    }
                }

            }

        }
        // like, comment, share
        Row(
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                isLike = !isLike
                                likeCount += if (isLike) 1 else -1
                                likeIcon =
                                    if (isLike) R.drawable.like_done else R.drawable.like
                                likeJob?.cancel()
                                likeJob = scope.launch {
                                    delay(1000)
                                    if (isLike) {
                                        onLikePost(post.id, "like")
                                    } else {
                                        onLikePost(post.id, "unlike")
                                    }
                                }
                            },
                            onLongPress = {
                                onShowReact(post.id, true)
                            }
                        )
                    }
                    .padding(4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (likeIcon == R.drawable.like) {
                        Icon(
                            painter = painterResource(R.drawable.like),
                            contentDescription = "Like",
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    } else {
                        Image(
                            painter = painterResource(likeIcon),
                            contentDescription = "Like",
                            modifier = Modifier.size(18.dp),
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$likeCount",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            TextButton(onClick = {
                if (post.id.isNotBlank()) {
                    onCommentClick()
                } else {
                    Toast.makeText(
                        context,
                        R.string.error_post_toast,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                Icon(
                    painter = painterResource(R.drawable.comment_icon),
                    contentDescription = "Comment",
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${post.commentCount}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            TextButton(onClick = {
                showBottomSheet = true

            }) {
                Icon(
                    painter = painterResource(R.drawable.share),
                    contentDescription = "Share",
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$shareCount",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        if (showReactions) {
            ReactionBar(
                modifier = Modifier
                    .background(
                        Color.LightGray,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .padding(10.dp),
                onClick = { type ->
                    onLikePost(post.id, type.title)
                    likeIcon = getIcon(type.title)
                    likeCount += 1
                    isLike = true
                },
                onClose = {
                    onShowReact(post.id, false)
                }
            )
        }
    }
}

@Composable
fun ReactionBar(
    modifier: Modifier,
    onClick: (ReactionType) -> Unit,
    onClose: () -> Unit
) {

    Box(
        modifier = modifier
    ) {

        Box(
            modifier = modifier
                .background(
                    color = Color.Transparent
                )
                .clickable(
                    onClick = onClose
                )
        )

        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .background(
                    Color.LightGray,
                    shape = RoundedCornerShape(1000.dp)
                ).padding(horizontal = 12.dp, vertical = 8.dp)
            ,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            ReactionType.entries.forEach { type ->
                Image(
                    painter = painterResource(type.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onClick(type)
                            onClose()
                        },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

fun getIcon(
    type: String
): Int {
    return when (type) {
        "like" -> R.drawable.like_done
        "love" -> R.drawable.love
        "haha" -> R.drawable.haha
        "wow" -> R.drawable.wow
        "sad" -> R.drawable.sad
        "angry" -> R.drawable.angry
        else -> R.drawable.like
    }
}


@Composable
fun VideoContent(
    video: Media,
    modifier: Modifier = Modifier
) {
    var videoWidth by remember { mutableIntStateOf(0) }
    var videoHeight by remember { mutableIntStateOf(0) }

    LaunchedEffect(video.url) {
        withContext(Dispatchers.IO) {
            val retriever = MediaMetadataRetriever()
            try {
                retriever.setDataSource(video.url, HashMap<String, String>())
                val w = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
                    ?.toIntOrNull() ?: 0
                val h = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
                    ?.toIntOrNull() ?: 0
                val rotation =
                    retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)
                        ?.toIntOrNull() ?: 0

                if (rotation == 90 || rotation == 270) {
                    videoWidth = h
                    videoHeight = w
                } else {
                    videoWidth = w
                    videoHeight = h
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                retriever.release()
            }
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        val containerWidth = maxWidth
        val containerHeight = maxHeight

        if (videoWidth > 0 && videoHeight > 0) {
            val videoAspectRatio = videoWidth.toFloat() / videoHeight.toFloat()
            val containerAspectRatio = containerWidth / containerHeight

            val (finalWidth, finalHeight) = if (videoAspectRatio > containerAspectRatio) {
                val h = containerWidth / videoAspectRatio
                containerWidth to h
            } else {
                val w = containerHeight * videoAspectRatio
                w to containerHeight
            }

            Box(
                modifier = Modifier
                    .size(width = finalWidth, height = finalHeight)
            ) {
                VideoPlayer(
                    videoUrl = video.url
                )
            }
        }
    }
}