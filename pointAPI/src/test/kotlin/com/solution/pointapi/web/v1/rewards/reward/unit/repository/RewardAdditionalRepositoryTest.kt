package com.solution.pointapi.web.v1.rewards.reward.unit.repository

import com.solution.pointapi.web.v1.rewards.reward.entity.Reward
import com.solution.pointapi.web.v1.rewards.reward.entity.RewardAdditional
import com.solution.pointapi.web.v1.rewards.reward.repository.RewardAdditionalRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional

@DataJpaTest
class RewardAdditionalRepositoryTest @Autowired constructor(
	private var rewardAdditionalRepository: RewardAdditionalRepository
) {
	@Test
	@Transactional
	fun `입력받은 id로 추가 보상 상세 데이터 조회를 조회한다`(){
		//given
		val reward = Reward(rewardId = 1, subject = "test", content = "test", rewardType = 1, rewardTypeName = "point")
		val expected: List<RewardAdditional> = listOf(RewardAdditional(rewardAdditionalId = 1, reward = reward, continueDay = 3, addReward = 300))

		//when
		val actual = rewardAdditionalRepository.findByRewardId(1)

		//then
		Assertions.assertEquals(expected[0].rewardAdditionalId, actual[0].rewardAdditionalId)
		Assertions.assertEquals(expected[0].reward.rewardId, actual[0].reward.rewardId)
		Assertions.assertEquals(expected[0].continueDay, actual[0].continueDay)
		Assertions.assertEquals(expected[0].addReward, actual[0].addReward)
	}
}