package com.example.socialmedia1903.data.mapper

import com.example.socialmedia1903.data.dto.request.PostRequest
import com.example.socialmedia1903.data.dto.response.CommentResponse
import com.example.socialmedia1903.data.dto.response.FriendshipResponse
import com.example.socialmedia1903.data.dto.response.GroupInfoResponse
import com.example.socialmedia1903.data.dto.response.GroupResponse
import com.example.socialmedia1903.data.dto.response.LikeResponse
import com.example.socialmedia1903.data.dto.response.MediaResponse
import com.example.socialmedia1903.data.dto.response.MemberResponse
import com.example.socialmedia1903.data.dto.response.NotificationResponse
import com.example.socialmedia1903.data.dto.response.PostResponse
import com.example.socialmedia1903.data.dto.response.ProfileInfoResponse
import com.example.socialmedia1903.data.dto.response.ProfileResponse
import com.example.socialmedia1903.data.dto.response.SearchItemResponse
import com.example.socialmedia1903.data.dto.response.SearchResultResponse
import com.example.socialmedia1903.data.dto.response.UserResponse
import com.example.socialmedia1903.data.mapper.HaiMapper.toGroupInfo
import com.example.socialmedia1903.domain.model.Comment
import com.example.socialmedia1903.domain.model.Friendship
import com.example.socialmedia1903.domain.model.Group
import com.example.socialmedia1903.domain.model.GroupInfo
import com.example.socialmedia1903.domain.model.Like
import com.example.socialmedia1903.domain.model.Media
import com.example.socialmedia1903.domain.model.Member
import com.example.socialmedia1903.domain.model.Notification
import com.example.socialmedia1903.domain.model.Post
import com.example.socialmedia1903.domain.model.Profile
import com.example.socialmedia1903.domain.model.ProfileInfo
import com.example.socialmedia1903.domain.model.SearchItem
import com.example.socialmedia1903.domain.model.User

object HaiMapper {
    fun Post.toPostRequest(): PostRequest {
        return PostRequest(
            id = this.id,
            groupId = this.groupId,
            content = this.content,
            type = this.type,
            contentType = this.contentType,
            anonymous = this.anonymous,
            visibility = this.visibility
        )
    }

    fun GroupResponse.toGroup(): Group {
        return Group(
            groupInfo = this.group.toGroupInfo(),
            posts = this.posts.toPostList()
        )
    }

    fun GroupInfoResponse.toGroupInfo(): GroupInfo {
        return GroupInfo(
            id = this.id,
            name = this.name,
            status = this.status,
            imageUrl = this.imageUrl,
            creatorId = this.creatorId,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            isOwner = this.isOwner,
            memberCount = this.memberCount,
            memberGroups = this.MemberGroups.toMemberList(),
            statusRequest = this.statusRequest
        )
    }

    fun SearchItemResponse.toSearchItem(): SearchItem {
        return SearchItem(
            id = this.id,
            name = this.name,
            avatarUrl = this.avatarUrl,
            status = this.status
        )
    }

    fun List<SearchItemResponse>.toSearchItemList(): List<SearchItem> {
        return this.map { it.toSearchItem() }
    }

    fun SearchResultResponse.toSearchResult(): com.example.socialmedia1903.domain.model.SearchResult {
        return com.example.socialmedia1903.domain.model.SearchResult(
            users = this.users.toSearchItemList(),
            posts = this.posts.toPostList()
        )
    }

    fun ProfileResponse.ToProfile(): Profile {
        return Profile(
            profile = this.profile.toProfileInfo(),
            status = this.status,
            friends = this.friends.toUserList(),
            posts = this.posts.toPostList()
        )
    }

    fun List<UserResponse>.toUserList(): List<User> {
        return this.map { it.toUser() }
    }

    private fun ProfileInfoResponse.toProfileInfo(): ProfileInfo {
        return ProfileInfo(
            id = this.id,
            username = this.username,
            name = this.name,
            avatarUrl = this.avatarUrl,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }


    private fun List<MemberResponse>.toMemberList(): List<Member> {
        return this.map { it.toMember() }
    }

    private fun MemberResponse.toMember(): Member {
        return Member(
            userId = this.userId
        )
    }

    fun NotificationResponse.toNotification(): Notification {
        return Notification(
            id = this.id,
            userId = this.userId,
            destinationId = this.destinationId,
            resourceId = this.resourceId,
            from = this.from,
            type = this.type,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            user = this.User.toUser(),
            groupId = this.groupId,
            groupAvatar = this.groupAvatar,
            groupName = this.groupName,
            likeType = this.likeType,
            postContent = this.postContent,
            commentContent = this.commentContent
        )
    }

    fun List<NotificationResponse>.toNotificationList(): List<Notification> {
        return this.map { it.toNotification() }
    }

    fun PostResponse.toPost(): Post {
        return Post(
            id = this.id,
            authorId = this.authorId,
            groupId = this.groupId,
            content = this.content,
            type = this.type,
            contentType = this.contentType,
            anonymous = this.anonymous,
            visibility = this.visibility,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            media = this.Media.toMediaList(),
            likes = this.Likes.toLikeList(),
            comments = this.Comments.toCommentList(),
            user = this.User.toUser(),
            likeCount = this.likeCount,
            commentCount = this.commentCount,
            sharedCount = this.sharedCount,
            group = this.Group?.toGroupInfo(),
            isOwnPost = this.isOwnPost
        )
    }

    private fun List<LikeResponse>.toLikeList(): List<Like> {
        return this.map { it.toLike() }
    }

    private fun LikeResponse.toLike(): Like {
        return Like(
            message = this.message,
            type = this.type
        )
    }

    private fun List<CommentResponse>.toCommentList(): List<Comment> {
        return this.map { it.toComment() }
    }

    private fun CommentResponse.toComment(): Comment {
        return Comment(
            id = this.id,
            userId = this.userId,
            postId = this.postId,
            parentId = this.parentId,
            content = this.content,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            user = this.User.toUser()
        )
    }

    private fun MediaResponse.toMedia(): Media {
        return Media(
            url = this.url,
            type = this.type
        )
    }

    private fun List<MediaResponse>.toMediaList(): List<Media> {
        return this.map { it.toMedia() }
    }

    private fun UserResponse.toUser(): User {
        return User(
            username = this.username,
            name = this.name,
            avatarUrl = this.avatarUrl,
            id = this.id,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            friendships = this.Friendships.toFriendshipList(),
            role = this.role,
            status = this.status
        )
    }

    private fun List<FriendshipResponse>.toFriendshipList(): List<Friendship> {
        return this.map { it.toFriendship() }
    }

    private fun FriendshipResponse.toFriendship(): Friendship {
        return Friendship(
            userId = this.userId,
            friendId = this.friendId,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }

    fun List<PostResponse>.toPostList(): List<Post> {
        return this.map { it.toPost() }
    }

}