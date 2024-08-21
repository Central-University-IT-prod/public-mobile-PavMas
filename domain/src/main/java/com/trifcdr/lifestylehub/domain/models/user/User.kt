package com.trifcdr.lifestylehub.domain.models.user

import java.io.FileReader

/**
 * Created by trifcdr.
 */
data class User(
    val gender: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val phone: String
)