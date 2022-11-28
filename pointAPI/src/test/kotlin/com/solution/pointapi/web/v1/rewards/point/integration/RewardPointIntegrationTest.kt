package com.solution.pointapi.web.v1.rewards.point.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.solution.pointapi.web.v1.member.entity.MemberPoint
import com.solution.pointapi.web.v1.member.repository.MemberPointRepository
import com.solution.pointapi.web.v1.rewards.point.dto.RewardPointPutRequest
import com.solution.pointapi.web.v1.rewards.point.entity.RewardPoint
import com.solution.pointapi.web.v1.rewards.point.repository.RewardPointRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.persistence.EntityManager

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RewardPointIntegrationTest @Autowired constructor(
	private val mockMvc: MockMvc,
	private val	objectMapper: ObjectMapper,
	private val rewardPointRepository: RewardPointRepository,
	private val memberPointRepository: MemberPointRepository,
	private val entityManager: EntityManager,
) {
	@Test
	fun `보상을 지급한다`(){
		//given
		val pointRequest = RewardPointPutRequest(1,1)
		val memberPoint = MemberPoint(memberId = 1, point = 0, getCount = 0, updatedAt = null)
		entityManager.createNativeQuery("ALTER TABLE POINT ALTER COLUMN POINT_ID RESTART WITH 1").executeUpdate();
		memberPointRepository.save(memberPoint)

		//when
		val result: ResultActions = mockMvc.perform(
			MockMvcRequestBuilders.put("/rewards/points")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(pointRequest))
				.accept(MediaType.APPLICATION_JSON))

		//then
		result.andExpect(MockMvcResultMatchers.status().isCreated)
		val firstResult: String? = result.andReturn().response.getHeader(HttpHeaders.LOCATION)
		Assertions.assertEquals(firstResult, "/rewards/points/1")
		//pointRepository.deleteAll()
	}

	@Test
	fun `보상을 지급하지 않는다-2회째`(){
		//given
		val pointRequest = RewardPointPutRequest(1,1)
		val memberPoint = MemberPoint(memberId = 1, point = 0, getCount = 0, updatedAt = null)
		val point = RewardPoint(memberPoint = memberPoint, point = 100)
		entityManager.createNativeQuery("ALTER TABLE POINT ALTER COLUMN POINT_ID RESTART WITH 1").executeUpdate();
		rewardPointRepository.save(point)

		//when
		val result: ResultActions = mockMvc.perform(
			MockMvcRequestBuilders.put("/rewards/points")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(pointRequest))
				.accept(MediaType.APPLICATION_JSON))

		//then
		result
			.andExpect(MockMvcResultMatchers.jsonPath("message").value("already get point"))
			.andExpect(MockMvcResultMatchers.status().isBadRequest)
		//pointRepository.deleteAll()
	}

	@Test
	fun `보상을 지급하지 않는다-11번째 이후 사용자`(){
		//given
		val pointRequest = RewardPointPutRequest(11,2)
		val memberPoint: List<MemberPoint> = listOf(MemberPoint(memberId = 1, point = 0, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 2, point = 0, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 3, point = 0, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 4, point = 0, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 5, point = 0, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 6, point = 0, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 7, point = 0, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 8, point = 0, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 9, point = 0, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 10, point = 0, getCount = 0, updatedAt = null))
		val points: List<RewardPoint> = listOf(RewardPoint(memberPoint = memberPoint[0], point = 100),
			RewardPoint(memberPoint = memberPoint[1], point = 100),
			RewardPoint(memberPoint = memberPoint[2], point = 100),
			RewardPoint(memberPoint = memberPoint[3], point = 100),
			RewardPoint(memberPoint = memberPoint[4], point = 100),
			RewardPoint(memberPoint = memberPoint[5], point = 100),
			RewardPoint(memberPoint = memberPoint[6], point = 100),
			RewardPoint(memberPoint = memberPoint[7], point = 100),
			RewardPoint(memberPoint = memberPoint[8], point = 100),
			RewardPoint(memberPoint = memberPoint[9], point = 100))
		entityManager.createNativeQuery("ALTER TABLE POINT ALTER COLUMN POINT_ID RESTART WITH 1").executeUpdate();
		rewardPointRepository.saveAll(points)

		//when
		val result: ResultActions = mockMvc.perform(
			MockMvcRequestBuilders.put("/rewards/points")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(pointRequest))
				.accept(MediaType.APPLICATION_JSON))

		//then
		result
			.andExpect(MockMvcResultMatchers.jsonPath("message").value("the end event"))
			.andExpect(MockMvcResultMatchers.status().isBadRequest)
		//pointRepository.deleteAll()
	}

	@Test
	fun `지급보상 상세 데이터를 조회를 실행한다`(){
		//given
		val pointRequest = RewardPointPutRequest(1,1)
		val memberPoint = MemberPoint(memberId = 1, point = 10, getCount = 0, updatedAt = null)
		val point = RewardPoint(memberPoint = memberPoint, point = 100)
		entityManager.createNativeQuery("ALTER TABLE POINT ALTER COLUMN POINT_ID RESTART WITH 1").executeUpdate();
		rewardPointRepository.save(point)

		//when
		val result: ResultActions = mockMvc.perform(
			MockMvcRequestBuilders.get("/rewards/points/detail/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(pointRequest))
				.accept(MediaType.APPLICATION_JSON))

		//then
		result
			.andExpect(MockMvcResultMatchers.jsonPath("data.id").value(point.pointId))
			.andExpect(MockMvcResultMatchers.jsonPath("data.memberId").value(point.memberPoint.memberId))
			.andExpect(MockMvcResultMatchers.jsonPath("data.point").value(point.point))
			.andExpect(MockMvcResultMatchers.status().isOk)
		//pointRepository.deleteAll()
	}

	@Test
	fun `특정 날짜 지급보상을 데이터를 조회한다`(){
		//given
		val pointRequest = RewardPointPutRequest(11,1)
		val searchDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
		val memberPoint: List<MemberPoint> = listOf(MemberPoint(memberId = 1, point = 0, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 2, point = 10, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 3, point = 20, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 4, point = 30, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 5, point = 0, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 6, point = 0, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 7, point = 0, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 8, point = 0, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 9, point = 0, getCount = 0, updatedAt = null),
			MemberPoint(memberId = 10, point = 0, getCount = 0, updatedAt = null))
		val points: List<RewardPoint> = listOf(RewardPoint(memberPoint = memberPoint[0], point = 100),
			RewardPoint(memberPoint = memberPoint[1], point = 100),
			RewardPoint(memberPoint = memberPoint[2], point = 100),
			RewardPoint(memberPoint = memberPoint[3], point = 100),
			RewardPoint(memberPoint = memberPoint[4], point = 100),
			RewardPoint(memberPoint = memberPoint[5], point = 100),
			RewardPoint(memberPoint = memberPoint[6], point = 100),
			RewardPoint(memberPoint = memberPoint[7], point = 100),
			RewardPoint(memberPoint = memberPoint[8], point = 100),
			RewardPoint(memberPoint = memberPoint[9], point = 100))
		entityManager.createNativeQuery("ALTER TABLE POINT ALTER COLUMN POINT_ID RESTART WITH 1").executeUpdate();
		rewardPointRepository.saveAll(points)

		//when
		val result: ResultActions = mockMvc.perform(
			MockMvcRequestBuilders.get("/rewards/points/$searchDate")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))

		//then
		result
			.andExpect(MockMvcResultMatchers.jsonPath("data.content[0].memberId").value(1))
			.andExpect(MockMvcResultMatchers.jsonPath("data.content[1].memberId").value(2))
			.andExpect(MockMvcResultMatchers.jsonPath("data.content[2].memberId").value(3))
			.andExpect(MockMvcResultMatchers.jsonPath("data.content[1].totalPoint").value(10))
			.andExpect(MockMvcResultMatchers.jsonPath("data.content[2].totalPoint").value(20))
			.andExpect(MockMvcResultMatchers.jsonPath("data.content[3].totalPoint").value(30))
			.andExpect(MockMvcResultMatchers.status().isOk)
		//pointRepository.deleteAll()
	}


}