package com.melkonian.postsandalbums.utils

interface Mapper<InputType, out OutputType> {
    fun map(input: InputType): OutputType
}