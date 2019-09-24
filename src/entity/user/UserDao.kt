package eu.techwares.demo.entity.user

import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface UserDao {
    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS app_user
          (
             id             VARCHAR(50) PRIMARY KEY NOT NULL,
             email          VARCHAR(100) NOT NULL,
             display_name   VARCHAR(100) NOT NULL,
             password_hash  VARCHAR(100) NOT NULL
          )
    """)
    fun createTable()

    @SqlUpdate("""
        INSERT INTO app_user (
             id,
             email,
             display_name,
             password_hash
        ) VALUES (
            :user.id,
            :user.email,
            :user.displayName,
            :user.passwordHash
        )
    """)
    fun insert(user: User)

    @SqlQuery("SELECT * from app_user WHERE id=:userId")
    fun selectForUser(userId: String): List<User>
}
