package com.solution.pointapi.web.v1.member.unit.service

import com.solution.pointapi.common.exception.ResourceNotFoundException
import com.solution.pointapi.web.v1.member.entity.MemberPoint
import com.solution.pointapi.web.v1.member.repository.MemberPointRepository
import com.solution.pointapi.web.v1.member.service.MemberPointService
import com.solution.pointapi.web.v1.point.service.RewardPointService
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootTest
@AutoConfigureMockMvc
class MemberPointServiceTest @Autowired constructor(
	@MockBean
	private val memberPointRepository: MemberPointRepository
) {
	@BeforeEach
	fun setUp() {
		MockitoAnnotations.initMocks(this)
	}

	@Test
	fun `사용자 포인트 상세 데이터를 조회한다`(){
		//given
		val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
		val setdate: LocalDateTime = LocalDateTime.parse("2022-11-05 13:47:13.248", formatter)
		val memberId = 1
		val expected = MemberPoint(memberId = memberId, point = 0, getCount = 0, createdAt = setdate, updatedAt = setdate, delFlg = false)

		Mockito.`when`(memberPointRepository.findByMemberIdForUpDate(1)).thenReturn(expected)

		//when
		val memberPointService = MemberPointService(memberPointRepository)
		val actual = memberPointService.detail(1)

		//then
		Mockito.verify(memberPointRepository, Mockito.times(1)).findByMemberIdForUpDate(1)
		Assertions.assertEquals(expected, actual)
	}
}