package com.solution.pointapi.web.v1.point.service

import com.solution.pointapi.common.exception.IllegalArgumentException
import com.solution.pointapi.common.exception.ResourceNotFoundException
import com.solution.pointapi.web.v1.member.service.MemberPointService
import com.solution.pointapi.web.v1.rewards.point.dto.RewardPointPutRequest
import com.solution.pointapi.web.v1.rewards.point.entity.RewardPoint
import com.solution.pointapi.web.v1.rewards.point.repository.RewardPointRepository
import com.solution.pointapi.web.v1.rewards.reward.service.RewardService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Service
class RewardPointService(
	private val pointRepository: RewardPointRepository,
	private val memberPointService: MemberPointService,
	private val rewardService: RewardService
) {
	@Transactional
	fun rewardPoint(request: RewardPointPutRequest): RewardPoint {
		var point = 100
		val date: LocalDate = LocalDate.now()
		val fromTime: LocalTime = LocalTime.of(0,0,0)
		val toTime: LocalTime = LocalTime.of(23,59,59)
		var fromDateTime: LocalDateTime = LocalDateTime.of(date, fromTime)
		var toDateTime: LocalDateTime = LocalDateTime.of(date, toTime)

		val checkRewardQuantity = rewardService.getRewardDetail(request.rewardId)
		val compareDay = ChronoUnit.DAYS.between(checkRewardQuantity.updatedAt, fromDateTime)
		if (compareDay != 0L) {
			checkRewardQuantity.quantity = checkRewardQuantity.rewardCount
		} else {
			if (checkRewardQuantity.quantity == 0) {
				throw IllegalArgumentException("the end event")
			}
		}
		checkRewardQuantity.quantity = checkRewardQuantity.quantity - 1

		val memberPoint =  memberPointService.detail(request.memberId)
		val rewardAdditional = rewardService.getRewardAdditional(request.rewardId)
		for (additional in rewardAdditional){
			if (memberPoint.getCount == additional.continueDay - 1){
				point += additional.addReward
			}
		}

		var checkPoint = pointRepository.findByMemberIdAndUpdatedAtForUpdate(request.memberId, fromDateTime, toDateTime)
		if (checkPoint != null) {
			throw IllegalArgumentException("already get point")
		}

		fromDateTime = fromDateTime.plusDays(-1)
		toDateTime = toDateTime.plusDays(-1)
		checkPoint = pointRepository.findByMemberIdAndUpdatedAtForUpdate(request.memberId, fromDateTime, toDateTime)

		memberPoint.point = memberPoint.point + point
		memberPoint.getCount = if(memberPoint.getCount == checkRewardQuantity.maxContinue - 1 || checkPoint == null) 0 else memberPoint.getCount + 1

		val postPoint = RewardPoint(
			memberPoint = memberPoint,
			point = point
		)
		pointRepository.save(postPoint)
		rewardService.saveRewardDetail(checkRewardQuantity)

		return postPoint
	}

	fun getDetail(id: Int): RewardPoint {
		return pointRepository.findByPointId(id) ?: throw ResourceNotFoundException("Not Found Point")
	}

	fun getList(searchDate: String): List<RewardPoint>{
		val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
		val date: LocalDate = LocalDate.parse(searchDate, formatter)
		val fromTime: LocalTime = LocalTime.of(0,0,0)
		val toTime: LocalTime = LocalTime.of(23,59,59)
		val fromDateTime: LocalDateTime = LocalDateTime.of(date, fromTime)
		val toDateTime: LocalDateTime = LocalDateTime.of(date, toTime)
		return pointRepository.findByUpdatedAt(fromDateTime, toDateTime)
	}
}