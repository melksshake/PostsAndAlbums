package com.melkonian.postsandalbums.presentation.mapper

import com.melkonian.postsandalbums.domain.entity.PostEntity
import com.melkonian.postsandalbums.presentation.models.PostModel
import com.melkonian.postsandalbums.utils.Mapper
import javax.inject.Inject

class PostsListMapper @Inject constructor() : Mapper<PostEntity, PostModel> {
    override fun map(input: PostEntity): PostModel {
        return PostModel(
            userId = input.userId,
            id = input.id,
            title = input.title,
            body = input.body
        )
    }
}