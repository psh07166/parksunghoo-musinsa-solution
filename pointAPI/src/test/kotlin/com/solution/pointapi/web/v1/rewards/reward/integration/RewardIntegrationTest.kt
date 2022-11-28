package com.solution.pointapi.web.v1.rewards.reward.integration

import com.solution.pointapi.web.v1.rewards.reward.entity.Reward
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RewardIntegrationTest @Autowired constructor(
	private val mockMvc: MockMvc
) {

	@Test
	fun `보상 상세 데이터를 조회를 실행한다`(){
		//given
		val expected = Reward(rewardId = 1, subject = "포인트 지급", content = "받기를 누르면 포인트를 지급합니다", rewardType = 1, rewardTypeName = "포인트")

		//when
		val result: ResultActions = mockMvc.perform(
			MockMvcRequestBuilders.get("/rewards/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))

		//then
		result
			.andExpect(MockMvcResultMatchers.jsonPath("data.id").value(expected.rewardId))
			.andExpect(MockMvcResultMatchers.jsonPath("data.rewardTypeName").value(expected.rewardTypeName))
			.andExpect(MockMvcResultMatchers.jsonPath("data.content").value(expected.content))
			.andExpect(MockMvcResultMatchers.jsonPath("data.subject").value(expected.subject))
			.andExpect(MockMvcResultMatchers.status().isOk)
	}
}