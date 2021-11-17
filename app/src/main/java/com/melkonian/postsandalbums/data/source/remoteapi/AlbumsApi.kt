package com.melkonian.postsandalbums.data.source.remoteapi

import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumsApi {
    @GET("/albums/{id}/photos")
    suspend fun getAlbumsWithPhotosById(@Query("albumId") id: Int)
}