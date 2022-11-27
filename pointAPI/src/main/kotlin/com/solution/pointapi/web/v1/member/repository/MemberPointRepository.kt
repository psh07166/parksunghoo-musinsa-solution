package com.solution.pointapi.web.v1.member.repository

import com.solution.pointapi.web.v1.member.entity.MemberPoint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import javax.persistence.LockModeType

interface MemberPointRepository: JpaRepository<MemberPoint, Int> {
	@Query("select m from MemberPoint m where m.memberId = :id and m.delFlg = false")
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	fun findByMemberIdForUpDate(id: Int): MemberPoint
}