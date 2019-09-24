package eu.techwares.demo.entity.blog

import org.jdbi.v3.core.mapper.reflect.ColumnName

data class Blog (
    @ColumnName("id")
    val id: String?,

    @ColumnName("message")
    val message: String
)