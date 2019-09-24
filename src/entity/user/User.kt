package eu.techwares.demo.entity.user

import org.jdbi.v3.core.mapper.reflect.ColumnName

data class User (
    @ColumnName("id")
    val id: String,

    @ColumnName("email")
    val email: String,

    @ColumnName("display_name")
    val displayName: String,

    @ColumnName("password_hash")
    val passwordHash: String
)