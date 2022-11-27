package com.solution.pointapi.web.v1.rewards.point.unit.controller

import com.solution.pointapi.web.v1.member.entity.MemberPoint
import com.solution.pointapi.web.v1.point.service.RewardPointService
import com.solution.pointapi.web.v1.rewards.point.controller.RewardPointController
import com.solution.pointapi.web.v1.rewards.point.dto.RewardPointListResponse
import com.solution.pointapi.web.v1.rewards.point.dto.RewardPointPutRequest
import com.solution.pointapi.web.v1.rewards.point.dto.RewardPointResponse
import com.solution.pointapi.web.v1.rewards.point.entity.RewardPoint
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.net.URI
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SpringBootTest
@AutoConfigureMockMvc
class RewardPointControllerTest @Autowired constructor(
	@MockBean
	private val pointService: RewardPointService
) {
	@BeforeEach
	fun setUp() {
		MockitoAnnotations.initMocks(this)
	}

	@Test
	fun `보상 지급 API를 실행한다`(){
		// given
		val memberPoint = MemberPoint(memberId = 1, point = 1, getCount = 1)
		val putRequest = RewardPointPutRequest(1,1)
		val point = RewardPoint(pointId= 1, memberPoint = memberPoint, point = 100)
		val expected: ResponseEntity<HttpStatus> = ResponseEntity.created(URI.create("/rewards/points/1")).build()
		Mockito.`when`(pointService.rewardPoint(putRequest)).thenReturn(point)

		//when
		val pointController = RewardPointController(pointService)
		val actual = pointController.take(putRequest)

		//then
		Mockito.verify(pointService, Mockito.times(1)).rewardPoint(putRequest)

		Assertions.assertEquals(expected, actual)
		Assertions.assertEquals(expected.statusCode, actual.statusCode)
	}

	@Test
	fun `보상 지급 상세 조회 API를 실행한다`(){
		// given
		val memberPoint = MemberPoint(memberId = 1, point = 1, getCount = 1)
		val putRequest = RewardPointPutRequest(1,1)
		val point = RewardPoint(pointId= 1, memberPoint = memberPoint, point = 100)
		val expected: ResponseEntity<RewardPointResponse> = ResponseEntity.ok(RewardPointResponse.of(point))
		Mockito.`when`(pointService.getDetail(1)).thenReturn(point)

		//when
		val pointController = RewardPointController(pointService)
		val actual = pointController.detail(1)

		//then
		Mockito.verify(pointService, Mockito.times(1)).getDetail(1)

		Assertions.assertEquals(expected.body!!.getData(), actual.body!!.getData())
		Assertions.assertEquals(expected.statusCode, actual.statusCode)
	}

	@Test
	fun `보상 지급 리스트 조회 API를 실행한다`(){
		// given
		val memberPoints: List<MemberPoint> = listOf(MemberPoint(memberId = 1, point = 0, getCount = 0),
			MemberPoint(memberId = 2, point = 0, getCount = 0),
			MemberPoint(memberId = 3, point = 0, getCount = 0))
		val points: List<RewardPoint> = listOf(RewardPoint(memberPoint = memberPoints[0], point = 0),
			RewardPoint(memberPoint = memberPoints[1], point = 0),
			RewardPoint(memberPoint = memberPoints[2], point = 0))
		val searchDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
		val expected: ResponseEntity<RewardPointListResponse> = ResponseEntity.ok(RewardPointListResponse.of(points))


		Mockito.`when`(pointService.getList(searchDate)).thenReturn(points)

		//when
		val pointController = RewardPointController(pointService)
		val actual = pointController.list(searchDate)

		//then
		Mockito.verify(pointService, Mockito.times(1)).getList(searchDate)

		Assertions.assertEquals(expected.body!!.getData(), actual.body!!.getData())
		Assertions.assertEquals(expected.statusCode, actual.statusCode)
	}
}