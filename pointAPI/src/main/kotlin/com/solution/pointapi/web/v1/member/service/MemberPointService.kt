package com.solution.pointapi.web.v1.member.service

import com.solution.pointapi.web.v1.member.entity.MemberPoint
import com.solution.pointapi.web.v1.member.repository.MemberPointRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class MemberPointService (
	private val memberPointRepository: MemberPointRepository
	) {
	@Transactional
	fun detail(id: Int): MemberPoint{
		return memberPointRepository.findByMemberIdForUpDate(id)
	}
}