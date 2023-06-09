package com.example.demo.domain.group.usecase

import com.example.demo.domain.group.Group
import com.example.demo.domain.group.GroupRepository
import com.example.demo.domain.group.UserGroup
import com.example.demo.domain.group.UserGroupRepository
import com.example.demo.domain.user.User
import com.example.demo.domain.user.UserRepository
import com.example.demo.global.exception.GroupNotFoundException
import com.example.demo.global.exception.UserNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RequestMapping("/group")
@RestController
class GroupUseCase(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
    private val userGroupRepository: UserGroupRepository
) {

    data class CreateGroupRequest(
        val email: String,
        val title: String,
        val profileImage: String,
        val backgroundImage: String,
        val description: String,
        val posterImage: String,
        val maxPeople: Int,
        val setTime: LocalDateTime
    )

    @PostMapping
    fun createGroup(@RequestBody request: CreateGroupRequest): Group {
        val user = userRepository.findByEmail(request.email) ?: throw UserNotFoundException

        val group = groupRepository.save(
            Group(
                title = request.title,
                profileImage = request.profileImage,
                backgroundImage = request.backgroundImage,
                description = request.description,
                posterImage = request.posterImage,
                maxPeople = request.maxPeople,
                setTime = request.setTime,
                owner = user
            )
        )
        userGroupRepository.save(
            UserGroup(
                id = UserGroup.UserGroupId(user.id, group.id),
                user = user,
                group = group,
                intro = "king",
                member = true
            )
        )

        return group
    }

    data class UpdateGroupRequest(
        val id: Int,
        val title: String,
        val profileImage: String,
        val backgroundImage: String,
        val description: String,
        val posterImage: String,
        val maxPeople: Int,
        val setTime: LocalDateTime
    )

    @PatchMapping
    fun updateGroup(@RequestBody request: UpdateGroupRequest) {
        val group = groupRepository.findByIdOrNull(request.id) ?: throw GroupNotFoundException
        groupRepository.save(
            Group(
                id = group.id,
                title = request.title,
                profileImage = request.profileImage,
                backgroundImage = request.backgroundImage,
                description = request.description,
                posterImage = request.posterImage,
                maxPeople = request.maxPeople,
                setTime = request.setTime,
                owner = group.owner
            )
        )
    }

    @GetMapping("/user")
    fun getGroupUsers(@RequestParam("id") id: Int): List<UserGroup> {
        return userGroupRepository.findByIdGroupIdAndMemberTrue(id)
    }

    @GetMapping("/all")
    fun getAllGroup(): List<Group> {
        return groupRepository.findAll()
    }

    @GetMapping
    fun getGroupDetail(@RequestParam("id") id: Int): Group {
        return groupRepository.findByIdOrNull(id) ?: throw GroupNotFoundException
    }

    @GetMapping("/mygroup")
    fun getMyGroup(@RequestParam("email") email: String): List<Group> {
        val user = userRepository.findByEmail(email) ?: throw UserNotFoundException
        return userGroupRepository.findByIdUserIdAndMemberTrue(user.id).map { userGroup ->
            userGroup.group
        }
    }

    @GetMapping("/member")
    fun getMember(@RequestParam("id") id: Int): List<User> {
        return userGroupRepository.findByIdGroupIdAndMemberTrue(id).map { userGroup ->
            userGroup.user
        }
    }

    data class GroupRequest(
        val email: String,
        val groupId: Int,
        val intro: String
    )

    @PostMapping("/request")
    fun groupRequest(@RequestBody request: GroupRequest) {
        val user = userRepository.findByEmail(request.email) ?: throw UserNotFoundException
        val group = groupRepository.findByIdOrNull(request.groupId) ?: throw GroupNotFoundException

        userGroupRepository.save(
            UserGroup(
                id = UserGroup.UserGroupId(user.id, group.id),
                user = user,
                group = group,
                intro = request.intro
            )
        )
    }

    @DeleteMapping("/request")
    fun deleteRequest(@RequestParam("email") email: String, @RequestParam("id") id: Int) {
        val user = userRepository.findByEmail(email) ?: throw UserNotFoundException
        val userGroup =
            userGroupRepository.findByIdOrNull(UserGroup.UserGroupId(id, user.id)) ?: throw UserNotFoundException

        userGroupRepository.delete(userGroup)
    }

    @GetMapping("/request")
    fun getRequests(@RequestParam("id") id: Int): List<UserGroup> {
        return userGroupRepository.findByIdGroupIdAndMemberFalse(id)
    }

    @PatchMapping("/request")
    fun allowRequest(@RequestParam("email") email: String, @RequestParam("id") id: Int) {
        val user = userRepository.findByEmail(email) ?: throw UserNotFoundException
        val userGroup =
            userGroupRepository.findByIdOrNull(UserGroup.UserGroupId(id, user.id)) ?: throw UserNotFoundException

        userGroupRepository.save(
            UserGroup(
                id = userGroup.id,
                group = userGroup.group,
                user = userGroup.user,
                intro = userGroup.intro,
                member = true
            )
        )
    }
}