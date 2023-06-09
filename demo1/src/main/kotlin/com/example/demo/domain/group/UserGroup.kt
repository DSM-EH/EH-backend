package com.example.demo.domain.group

import com.example.demo.domain.user.User
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table
import java.io.Serializable
import java.time.LocalDateTime

@Table(name = "tbl_user_group")
@Entity
class UserGroup(

    @field:EmbeddedId
    val id: UserGroupId,

    @field:MapsId("groupId")
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "group_id", nullable = false)
    val group: Group,

    @field:MapsId("userId")
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "user_id", nullable = false)
    val user: User,

    val intro: String,

    val member: Boolean = false
) {
    @Embeddable
    data class UserGroupId(
        @field:Column(nullable = false)
        val groupId: Int,

        @field:Column(nullable = false)
        val userId: Int
    ) : Serializable
}