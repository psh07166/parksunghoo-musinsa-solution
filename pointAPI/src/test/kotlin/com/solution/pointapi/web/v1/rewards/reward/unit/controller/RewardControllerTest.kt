package com.solution.pointapi.web.v1.rewards.reward.unit.controller

import com.solution.pointapi.web.v1.rewards.reward.controller.RewardController
import com.solution.pointapi.web.v1.rewards.reward.dto.RewardResponse
import com.solution.pointapi.web.v1.rewards.reward.entity.Reward
import com.solution.pointapi.web.v1.rewards.reward.service.RewardService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.ResponseEntity

@SpringBootTest
@AutoConfigureMockMvc
class RewardControllerTest @Autowired constructor(
	@MockBean
	private val rewardService: RewardService
){
	@BeforeEach
	fun setUp() {
		MockitoAnnotations.initMocks(this)
	}

	@Test
	fun `보상 조회 API를 실행한다`(){
		// given
		val reward = Reward(rewardId = 1, subject = "test", content = "test", rewardType = 1, rewardTypeName = "point")
		val expected: ResponseEntity<RewardResponse> = ResponseEntity.ok(RewardResponse.of(reward))
		Mockito.`when`(rewardService.getDetail(1)).thenReturn(reward)

		//when
		val rewardController = RewardController(rewardService)
		val actual = rewardController.detail(1)

		//then
		Mockito.verify(rewardService, Mockito.times(1)).getDetail(1)

		Assertions.assertEquals(expected.body!!.getData(), actual.body!!.getData())
		Assertions.assertEquals(expected.statusCode, actual.statusCode)
	}
}