package com.melkonian.postsandalbums.di

import javax.inject.Qualifier

/**
 * Qualifier to inject real api
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RealApi

/**
 * Qualifier to inject mock api
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MockApi

/**
 * Qualifier to inject Api Service (router between real and mock api)
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiService