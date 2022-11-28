package com.solution.pointapi.web.v1.rewards.point.unit.service

import com.solution.pointapi.web.v1.member.entity.MemberPoint
import com.solution.pointapi.web.v1.member.service.MemberPointService
import com.solution.pointapi.web.v1.point.service.RewardPointService
import com.solution.pointapi.web.v1.rewards.point.dto.RewardPointPutRequest
import com.solution.pointapi.web.v1.rewards.point.entity.RewardPoint
import com.solution.pointapi.web.v1.rewards.point.repository.RewardPointRepository
import com.solution.pointapi.web.v1.rewards.reward.entity.Reward
import com.solution.pointapi.web.v1.rewards.reward.entity.RewardAdditional
import com.solution.pointapi.web.v1.rewards.reward.entity.RewardDetail
import com.solution.pointapi.web.v1.rewards.reward.service.RewardService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.solution.pointapi.common.exception.*

@SpringBootTest
@AutoConfigureMockMvc
class RewardPointServiceTest @Autowired constructor(
	@MockBean
	private val pointRepository: RewardPointRepository,

	@MockBean
	private val memberPointService: MemberPointService,

	@MockBean
	private val rewardService: RewardService
) {

	@BeforeEach
	fun setUp() {
		MockitoAnnotations.initMocks(this)
	}

	@Test
	fun `보상 데이터를 등록한다`(){
		//given
		val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
		val setDate: LocalDateTime = LocalDateTime.parse("2022-11-05 13:47:13.248", formatter)
		val date: LocalDate = LocalDate.now()
		val fromTime: LocalTime = LocalTime.of(0,0,0)
		val toTime: LocalTime = LocalTime.of(23,59,59)
		val fromDateTime: LocalDateTime = LocalDateTime.of(date, fromTime)
		val toDateTime: LocalDateTime = LocalDateTime.of(date, toTime)
		val fromDateTime2 =  fromDateTime.plusDays(-1)
		val toDateTime2 = toDateTime.plusDays(-1)
		val memberId = 1
		val pointRequest = RewardPointPutRequest(memberId = memberId,1)
		val checkPoint: RewardPoint? = null
		val memberPoint = MemberPoint(memberId = memberId, point = 0, getCount = 0, createdAt = setDate, updatedAt = setDate, delFlg = false)
		val reward = Reward(rewardId = 1, subject = "1", content = "1", rewardType = 1, rewardTypeName = "1")
		val rewardDetail = RewardDetail(rewardDetailId = 1, rewardId = 1, rewardCount = 10, quantity = 10, maxContinue = 10, updatedAt = setDate)
		val rewardAdditional: List<RewardAdditional> = listOf(RewardAdditional(rewardAdditionalId = 1, reward = reward, continueDay = 3, addReward = 100))
		val expected= RewardPoint(memberPoint = memberPoint, point = 100, createdAt = null, updatedAt = null, delFlg = false)

		Mockito.`when`(rewardService.getRewardDetail(1)).thenReturn(rewardDetail)
		Mockito.`when`(memberPointService.detail(pointRequest.memberId)).thenReturn(memberPoint)
		Mockito.`when`(rewardService.getRewardAdditional(1)).thenReturn(rewardAdditional)
		Mockito.`when`(pointRepository.findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime, toDateTime)).thenReturn(checkPoint)
		Mockito.`when`(pointRepository.findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime2, toDateTime2)).thenReturn(checkPoint)

		//when
		val pointService = RewardPointService(pointRepository, memberPointService, rewardService)
		val actual = pointService.rewardPoint(pointRequest)

		//then
		Mockito.verify(rewardService, Mockito.times(1)).getRewardDetail(1)
		Mockito.verify(memberPointService, Mockito.times(1)).detail(memberId)
		Mockito.verify(pointRepository, Mockito.times(1)).findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime, toDateTime)
		Mockito.verify(pointRepository, Mockito.times(1)).findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime2, toDateTime2)
		Mockito.verify(pointRepository, Mockito.times(1)).save(any())
		Mockito.verify(rewardService, Mockito.times(1)).saveRewardDetail(rewardDetail)
		assertEquals(expected, actual)
	}

	@Test
	fun `3일 연속 보상 데이터를 등록한다`(){
		//given
		val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
		val setDate: LocalDateTime = LocalDateTime.parse("2022-11-05 13:47:13.248", formatter)
		val date: LocalDate = LocalDate.now()
		val fromTime: LocalTime = LocalTime.of(0,0,0)
		val toTime: LocalTime = LocalTime.of(23,59,59)
		val fromDateTime: LocalDateTime = LocalDateTime.of(date, fromTime)
		val toDateTime: LocalDateTime = LocalDateTime.of(date, toTime)
		val fromDateTime2 =  fromDateTime.plusDays(-1)
		val toDateTime2 = toDateTime.plusDays(-1)
		val memberId = 1
		val pointRequest = RewardPointPutRequest(memberId = memberId,1)
		val checkPoint: RewardPoint? = null
		val memberPoint = MemberPoint(memberId = memberId, point = 0, getCount = 2, createdAt = setDate, updatedAt = setDate, delFlg = false)
		val reward = Reward(rewardId = 1, subject = "1", content = "1", rewardType = 1, rewardTypeName = "1")
		val rewardDetail = RewardDetail(rewardDetailId = 1, rewardId = 1, rewardCount = 10, quantity = 10, maxContinue = 10, updatedAt = setDate)
		val rewardAdditional: List<RewardAdditional> = listOf(RewardAdditional(rewardAdditionalId = 1, reward = reward, continueDay = 3, addReward = 300))
		val expected= RewardPoint(memberPoint = memberPoint, point = 400, createdAt = null, updatedAt = null, delFlg = false)

		Mockito.`when`(rewardService.getRewardDetail(1)).thenReturn(rewardDetail)
		Mockito.`when`(memberPointService.detail(pointRequest.memberId)).thenReturn(memberPoint)
		Mockito.`when`(rewardService.getRewardAdditional(1)).thenReturn(rewardAdditional)
		Mockito.`when`(pointRepository.findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime, toDateTime)).thenReturn(checkPoint)
		Mockito.`when`(pointRepository.findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime2, toDateTime2)).thenReturn(checkPoint)

		//when
		val pointService = RewardPointService(pointRepository, memberPointService, rewardService)
		val actual = pointService.rewardPoint(pointRequest)


		//then
		Mockito.verify(rewardService, Mockito.times(1)).getRewardDetail(1)
		Mockito.verify(memberPointService, Mockito.times(1)).detail(memberId)
		Mockito.verify(pointRepository, Mockito.times(1)).findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime, toDateTime)
		Mockito.verify(pointRepository, Mockito.times(1)).findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime2, toDateTime2)
		Mockito.verify(pointRepository, Mockito.times(1)).save(any())
		Mockito.verify(rewardService, Mockito.times(1)).saveRewardDetail(rewardDetail)
		assertEquals(expected, actual)
	}

	@Test
	fun `5일 연속 보상 데이터를 등록한다`(){
		//given
		val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
		val setDate: LocalDateTime = LocalDateTime.parse("2022-11-05 13:47:13.248", formatter)
		val date: LocalDate = LocalDate.now()
		val fromTime: LocalTime = LocalTime.of(0,0,0)
		val toTime: LocalTime = LocalTime.of(23,59,59)
		val fromDateTime: LocalDateTime = LocalDateTime.of(date, fromTime)
		val toDateTime: LocalDateTime = LocalDateTime.of(date, toTime)
		val fromDateTime2 =  fromDateTime.plusDays(-1)
		val toDateTime2 = toDateTime.plusDays(-1)
		val memberId = 1
		val pointRequest = RewardPointPutRequest(memberId = memberId,1)
		val checkPoint: RewardPoint? = null
		val memberPoint = MemberPoint(memberId = memberId, point = 0, getCount = 4, createdAt = setDate, updatedAt = setDate, delFlg = false)
		val reward = Reward(rewardId = 1, subject = "1", content = "1", rewardType = 1, rewardTypeName = "1")
		val rewardDetail = RewardDetail(rewardDetailId = 1, rewardId = 1, rewardCount = 10, quantity = 10, maxContinue = 10, updatedAt = setDate)
		val rewardAdditional: List<RewardAdditional> = listOf(RewardAdditional(rewardAdditionalId = 1, reward = reward, continueDay = 5, addReward = 500))
		val expected= RewardPoint(memberPoint = memberPoint, point = 600, createdAt = null, updatedAt = null, delFlg = false)

		Mockito.`when`(rewardService.getRewardDetail(1)).thenReturn(rewardDetail)
		Mockito.`when`(memberPointService.detail(pointRequest.memberId)).thenReturn(memberPoint)
		Mockito.`when`(rewardService.getRewardAdditional(1)).thenReturn(rewardAdditional)
		Mockito.`when`(pointRepository.findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime, toDateTime)).thenReturn(checkPoint)
		Mockito.`when`(pointRepository.findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime2, toDateTime2)).thenReturn(checkPoint)

		//when
		val pointService = RewardPointService(pointRepository, memberPointService, rewardService)
		val actual = pointService.rewardPoint(pointRequest)


		//then
		Mockito.verify(rewardService, Mockito.times(1)).getRewardDetail(1)
		Mockito.verify(memberPointService, Mockito.times(1)).detail(memberId)
		Mockito.verify(pointRepository, Mockito.times(1)).findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime, toDateTime)
		Mockito.verify(pointRepository, Mockito.times(1)).findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime2, toDateTime2)
		Mockito.verify(pointRepository, Mockito.times(1)).save(any())
		Mockito.verify(rewardService, Mockito.times(1)).saveRewardDetail(rewardDetail)
		assertEquals(expected, actual)
	}

	@Test
	fun `10일 연속 보상 데이터를 등록한다`(){
		//given
		val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
		val setDate: LocalDateTime = LocalDateTime.parse("2022-11-05 13:47:13.248", formatter)
		val date: LocalDate = LocalDate.now()
		val fromTime: LocalTime = LocalTime.of(0,0,0)
		val toTime: LocalTime = LocalTime.of(23,59,59)
		val fromDateTime: LocalDateTime = LocalDateTime.of(date, fromTime)
		val toDateTime: LocalDateTime = LocalDateTime.of(date, toTime)
		val fromDateTime2 =  fromDateTime.plusDays(-1)
		val toDateTime2 = toDateTime.plusDays(-1)
		val memberId = 1
		val pointRequest = RewardPointPutRequest(memberId = memberId,1)
		val checkPoint: RewardPoint? = null
		val memberPoint = MemberPoint(memberId = memberId, point = 0, getCount = 9, createdAt = setDate, updatedAt = setDate, delFlg = false)
		val reward = Reward(rewardId = 1, subject = "1", content = "1", rewardType = 1, rewardTypeName = "1")
		val rewardDetail = RewardDetail(rewardDetailId = 1, rewardId = 1, rewardCount = 10, quantity = 10, maxContinue = 10, updatedAt = setDate)
		val rewardAdditional: List<RewardAdditional> = listOf(RewardAdditional(rewardAdditionalId = 1, reward = reward, continueDay = 10, addReward = 1000))
		val expected= RewardPoint(memberPoint = memberPoint, point = 1100, createdAt = null, updatedAt = null, delFlg = false)

		Mockito.`when`(rewardService.getRewardDetail(1)).thenReturn(rewardDetail)
		Mockito.`when`(memberPointService.detail(pointRequest.memberId)).thenReturn(memberPoint)
		Mockito.`when`(rewardService.getRewardAdditional(1)).thenReturn(rewardAdditional)
		Mockito.`when`(pointRepository.findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime, toDateTime)).thenReturn(checkPoint)
		Mockito.`when`(pointRepository.findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime2, toDateTime2)).thenReturn(checkPoint)

		//when
		val pointService = RewardPointService(pointRepository, memberPointService, rewardService)
		val actual = pointService.rewardPoint(pointRequest)

		//then
		Mockito.verify(rewardService, Mockito.times(1)).getRewardDetail(1)
		Mockito.verify(memberPointService, Mockito.times(1)).detail(memberId)
		Mockito.verify(pointRepository, Mockito.times(1)).findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime, toDateTime)
		Mockito.verify(pointRepository, Mockito.times(1)).findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime2, toDateTime2)
		Mockito.verify(pointRepository, Mockito.times(1)).save(any())
		Mockito.verify(rewardService, Mockito.times(1)).saveRewardDetail(rewardDetail)
		assertEquals(expected, actual)
	}

	@Test
	fun `10회 이후 보상 데이터를 등록한다`(){
		//given
		val setDate = LocalDateTime.now()
		val date: LocalDate = LocalDate.now()
		val fromTime: LocalTime = LocalTime.of(0,0,0)
		val toTime: LocalTime = LocalTime.of(23,59,59)
		val fromDateTime: LocalDateTime = LocalDateTime.of(date, fromTime)
		val toDateTime: LocalDateTime = LocalDateTime.of(date, toTime)
		val fromDateTime2 =  fromDateTime.plusDays(-1)
		val toDateTime2 = toDateTime.plusDays(-1)
		val memberId = 1
		val pointRequest = RewardPointPutRequest(memberId = memberId, 1)
		val checkPoint: RewardPoint? = null
		val memberPoint = MemberPoint(memberId = memberId, point = 0, getCount = 0, createdAt = setDate, updatedAt = setDate, delFlg = false)
		val reward = Reward(rewardId = 1, subject = "1", content = "1", rewardType = 1, rewardTypeName = "1")
		val rewardDetail = RewardDetail(rewardDetailId = 1, rewardId = 1, rewardCount = 10, quantity = 0, maxContinue = 10, updatedAt = setDate)
		val rewardAdditional: List<RewardAdditional> = listOf(RewardAdditional(rewardAdditionalId = 1, reward = reward, continueDay = 10, addReward = 1000))

		Mockito.`when`(rewardService.getRewardDetail(1)).thenReturn(rewardDetail)
		Mockito.`when`(memberPointService.detail(pointRequest.memberId)).thenReturn(memberPoint)
		Mockito.`when`(rewardService.getRewardAdditional(1)).thenReturn(rewardAdditional)
		Mockito.`when`(pointRepository.findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime, toDateTime)).thenReturn(checkPoint)
		Mockito.`when`(pointRepository.findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime2, toDateTime2)).thenReturn(checkPoint)

		//when
		val pointService = RewardPointService(pointRepository, memberPointService, rewardService)
		Assertions.assertThrows(IllegalArgumentException::class.java) { pointService.rewardPoint(pointRequest) }

		//then
		Mockito.verify(rewardService, Mockito.times(1)).getRewardDetail(1)
		Mockito.verify(memberPointService, Mockito.times(0)).detail(memberId)
		Mockito.verify(pointRepository, Mockito.times(0)).findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime, toDateTime)
		Mockito.verify(pointRepository, Mockito.times(0)).findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime2, toDateTime2)
		Mockito.verify(pointRepository, Mockito.times(0)).save(any())
		Mockito.verify(rewardService, Mockito.times(0)).saveRewardDetail(rewardDetail)

	}

	@Test
	fun `보상 데이터를 같은날 2번 등록한다`(){
		//given
		val setDate: LocalDateTime = LocalDateTime.now()
		val date: LocalDate = LocalDate.now()
		val fromTime: LocalTime = LocalTime.of(0,0,0)
		val toTime: LocalTime = LocalTime.of(23,59,59)
		val fromDateTime: LocalDateTime = LocalDateTime.of(date, fromTime)
		val toDateTime: LocalDateTime = LocalDateTime.of(date, toTime)
		val fromDateTime2 =  fromDateTime.plusDays(-1)
		val toDateTime2 = toDateTime.plusDays(-1)
		val memberId = 1
		val pointRequest = RewardPointPutRequest(memberId = memberId,1)
		val memberPoint = MemberPoint(memberId = memberId, point = 0, getCount = 0, createdAt = setDate, updatedAt = setDate, delFlg = false)
		val checkPoint = RewardPoint(memberPoint = memberPoint, point = 10, createdAt = setDate, updatedAt = setDate, delFlg = false)
		val reward = Reward(rewardId = 1, subject = "1", content = "1", rewardType = 1, rewardTypeName = "1")
		val rewardDetail = RewardDetail(rewardDetailId = 1, rewardId = 1, rewardCount = 10, quantity = 10, maxContinue = 10, updatedAt = setDate)
		val rewardAdditional: List<RewardAdditional> = listOf(RewardAdditional(rewardAdditionalId = 1, reward = reward, continueDay = 10, addReward = 1000))

		Mockito.`when`(rewardService.getRewardDetail(1)).thenReturn(rewardDetail)
		Mockito.`when`(memberPointService.detail(pointRequest.memberId)).thenReturn(memberPoint)
		Mockito.`when`(rewardService.getRewardAdditional(1)).thenReturn(rewardAdditional)
		Mockito.`when`(pointRepository.findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime, toDateTime)).thenReturn(checkPoint)
		Mockito.`when`(pointRepository.findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime2, toDateTime2)).thenReturn(checkPoint)

		//when
		val pointService = RewardPointService(pointRepository, memberPointService, rewardService)
		Assertions.assertThrows(IllegalArgumentException::class.java) { pointService.rewardPoint(pointRequest) }

		//then
		Mockito.verify(rewardService, Mockito.times(1)).getRewardDetail(1)
		Mockito.verify(memberPointService, Mockito.times(1)).detail(memberId)
		Mockito.verify(pointRepository, Mockito.times(1)).findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime, toDateTime)
		Mockito.verify(pointRepository, Mockito.times(0)).findByMemberIdAndUpdatedAtForUpdate(pointRequest.memberId, fromDateTime2, toDateTime2)
		Mockito.verify(pointRepository, Mockito.times(0)).save(any())
		Mockito.verify(rewardService, Mockito.times(0)).saveRewardDetail(rewardDetail)
	}

	@Test
	fun `보상 상세 데이터를 조회한다`(){
		//given
		val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
		val setDate: LocalDateTime = LocalDateTime.parse("2022-11-05 13:47:13.248", formatter)
		val memberId = 1
		val memberPoint = MemberPoint(memberId = memberId, point = 0, getCount = 0, createdAt = setDate, updatedAt = setDate, delFlg = false)
		val point = RewardPoint(memberPoint = memberPoint, point = 10, createdAt = setDate, updatedAt = setDate, delFlg = false)
		val expected= RewardPoint(memberPoint = memberPoint, point = 10, createdAt = setDate, updatedAt = setDate, delFlg = false)

		Mockito.`when`(pointRepository.findByPointId(1)).thenReturn(point)

		//when
		val pointService = RewardPointService(pointRepository, memberPointService, rewardService)
		val actual = pointService.getDetail(1)

		//then
		Mockito.verify(pointRepository, Mockito.times(1)).findByPointId(1)
		assertEquals(expected, actual)
	}

	@Test
	fun `보상 상세 데이터를 조회에 실패한다`(){
		//given
		val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
		val setDate: LocalDateTime = LocalDateTime.parse("2022-11-05 13:47:13.248", formatter)
		val memberId = 1
		val memberPoint = MemberPoint(memberId = memberId, point = 0, getCount = 0, createdAt = setDate, updatedAt = setDate, delFlg = false)
		val point = RewardPoint(memberPoint = memberPoint, point = 10, createdAt = setDate, updatedAt = setDate, delFlg = false)

		Mockito.`when`(pointRepository.findByPointId(2)).thenReturn(point)

		//when
		val pointService = RewardPointService(pointRepository, memberPointService, rewardService)
		Assertions.assertThrows(ResourceNotFoundException::class.java) { pointService.getDetail(1) }

		//then
		Mockito.verify(pointRepository, Mockito.times(1)).findByPointId(1)
	}

	@Test
	fun `지정한 날짜의 보상받은 사용자를 조회한다`(){
		//given
		var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
		val date: LocalDate = LocalDate.parse("2022-11-26", formatter)
		val fromTime: LocalTime = LocalTime.of(0,0,0)
		val toTime: LocalTime = LocalTime.of(23,59,59)
		val fromDateTime: LocalDateTime = LocalDateTime.of(date, fromTime)
		val toDateTime: LocalDateTime = LocalDateTime.of(date, toTime)

		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
		val setDate: LocalDateTime = LocalDateTime.parse("2022-11-05 13:47:13.248", formatter)
		val memberId = 1
		val memberPoint = MemberPoint(memberId = memberId, point = 0, getCount = 0, createdAt = setDate, updatedAt = setDate, delFlg = false)
		val expected : List<RewardPoint>  = listOf(RewardPoint(memberPoint = memberPoint, point = 10, createdAt = setDate, updatedAt = setDate, delFlg = false),
			RewardPoint(memberPoint = memberPoint, point = 10, createdAt = setDate, updatedAt = setDate, delFlg = false),
			RewardPoint(memberPoint = memberPoint, point = 10, createdAt = setDate, updatedAt = setDate, delFlg = false),
			RewardPoint(memberPoint = memberPoint, point = 10, createdAt = setDate, updatedAt = setDate, delFlg = false),
			RewardPoint(memberPoint = memberPoint, point = 10, createdAt = setDate, updatedAt = setDate, delFlg = false),
			RewardPoint(memberPoint = memberPoint, point = 10, createdAt = setDate, updatedAt = setDate, delFlg = false),
			RewardPoint(memberPoint = memberPoint, point = 10, createdAt = setDate, updatedAt = setDate, delFlg = false),
			RewardPoint(memberPoint = memberPoint, point = 10, createdAt = setDate, updatedAt = setDate, delFlg = false),
			RewardPoint(memberPoint = memberPoint, point = 10, createdAt = setDate, updatedAt = setDate, delFlg = false),
			RewardPoint(memberPoint = memberPoint, point = 10, createdAt = setDate, updatedAt = setDate, delFlg = false))

		Mockito.`when`(pointRepository.findByUpdatedAt(fromDateTime,toDateTime)).thenReturn(expected)

		//when
		val pointService = RewardPointService(pointRepository, memberPointService, rewardService)
		val actual = pointService.getList("2022-11-26")

		//then
		Mockito.verify(pointRepository, Mockito.times(1)).findByUpdatedAt(fromDateTime,toDateTime)
		assertEquals(expected, actual)
	}
}