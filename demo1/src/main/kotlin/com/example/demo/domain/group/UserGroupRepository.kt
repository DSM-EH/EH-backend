package com.example.demo.domain.group

import org.springframework.data.repository.CrudRepository

interface UserGroupRepository: CrudRepository<UserGroup, UserGroup.UserGroupId> {
    fun findByIdGroupIdAndMemberTrue(groupId: Int): List<UserGroup>
    fun findByIdUserIdAndMemberTrue(userId: Int): List<UserGroup>
    fun findByIdGroupIdAndMemberFalse(groupId: Int): List<UserGroup>
}