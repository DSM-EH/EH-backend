package com.example.demo.domain.group

import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository: JpaRepository<Group, Int> {
    fun findByTitle(title: String): Group?
}