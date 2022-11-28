package com.solution.pointapi.web.v1.rewards.reward.unit.service

import com.solution.pointapi.web.v1.rewards.reward.entity.Reward
import com.solution.pointapi.web.v1.rewards.reward.entity.RewardAdditional
import com.solution.pointapi.web.v1.rewards.reward.entity.RewardDetail
import com.solution.pointapi.web.v1.rewards.reward.repository.RewardAdditionalRepository
import com.solution.pointapi.web.v1.rewards.reward.repository.RewardDetailRepository
import com.solution.pointapi.web.v1.rewards.reward.repository.RewardRepository
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

@SpringBootTest
@AutoConfigureMockMvc
class RewardServiceTest @Autowired constructor(
	@MockBean
	private val rewardRepository: RewardRepository,
	@MockBean
	private val rewardDetailRepository: RewardDetailRepository,
	@MockBean
	private val rewardAdditionalRepository: RewardAdditionalRepository
) {
	@BeforeEach
	fun setUp() {
		MockitoAnnotations.initMocks(this)
	}

	@Test
	fun `입력받은 아이디로 보상데이터를 조회한다`(){
		//given
		val expected = Reward(rewardId = 1, subject = "test", content = "test", rewardType = 1, rewardTypeName = "point")

		Mockito.`when`(rewardRepository.findByRewardId(1)).thenReturn(expected)

		//when
		val rewardService = RewardService(rewardRepository,rewardDetailRepository,rewardAdditionalRepository)
		val actual = rewardService.getDetail(1)

		//then
		Mockito.verify(rewardRepository, Mockito.times(1)).findByRewardId(1)
		Assertions.assertEquals(expected, actual)
	}

	@Test
	fun `입력받은 아이디로 보상 상세 데이터를 조회한다`(){
		//given
		val expected = RewardDetail(rewardDetailId = 1, rewardId = 1, rewardCount = 10, quantity = 10, maxContinue = 10)

		Mockito.`when`(rewardDetailRepository.findByRewardIdForUpdate(1)).thenReturn(expected)

		//when
		val rewardService = RewardService(rewardRepository,rewardDetailRepository,rewardAdditionalRepository)
		val actual = rewardService.getRewardDetail(1)

		//then
		Mockito.verify(rewardDetailRepository, Mockito.times(1)).findByRewardIdForUpdate(1)
		Assertions.assertEquals(expected, actual)
	}

	@Test
	fun `입력받은 아이디로 보상 상세 데이터를 수정한다`(){
		//given
		val expected = RewardDetail(rewardDetailId = 1, rewardId = 1, rewardCount = 10, quantity = 10, maxContinue = 10)

		Mockito.`when`(rewardDetailRepository.save(expected)).thenReturn(expected)

		//when
		val rewardService = RewardService(rewardRepository,rewardDetailRepository,rewardAdditionalRepository)
		val actual = rewardService.saveRewardDetail(expected)

		//then
		Mockito.verify(rewardDetailRepository, Mockito.times(1)).save(expected)
		Assertions.assertEquals(expected, actual)
	}

	@Test
	fun `입력받은 아이디로 추가 보상 데이터를 조회한다`(){
		//given
		val reward = Reward(rewardId = 1, subject = "test", content = "test", rewardType = 1, rewardTypeName = "point")
		val expected: List<RewardAdditional> = listOf(RewardAdditional(rewardAdditionalId = 1, reward = reward, continueDay = 10, addReward = 10))

		Mockito.`when`(rewardAdditionalRepository.findByRewardId(1)).thenReturn(expected)

		//when
		val rewardService = RewardService(rewardRepository,rewardDetailRepository,rewardAdditionalRepository)
		val actual = rewardService.getRewardAdditional(1)

		//then
		Mockito.verify(rewardAdditionalRepository, Mockito.times(1)).findByRewardId(1)
		Assertions.assertEquals(expected, actual)
	}
}