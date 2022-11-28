package com.solution.pointapi.web.v1.rewards.reward.unit.repository

import com.solution.pointapi.web.v1.rewards.reward.entity.Reward
import com.solution.pointapi.web.v1.rewards.reward.repository.RewardRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.transaction.annotation.Transactional

@DataJpaTest
class RewardRepositoryTest @Autowired constructor(
	private var testEntityManager: TestEntityManager,
	private var rewardRepository: RewardRepository
) {
	@Test
	@Transactional
	fun `입력받은 id로 보상 데이터 조회를 조회한다`(){
		//given
		val expected = Reward(rewardId = 3, subject = "test", content = "test", rewardType = 1, rewardTypeName = "point")
		testEntityManager.persist(expected)

		//when
		val actual = rewardRepository.findByRewardId(3)

		//then
		Assertions.assertEquals(expected.rewardId, actual.rewardId)
		Assertions.assertEquals(expected.rewardType, actual.rewardType)
		Assertions.assertEquals(expected.rewardTypeName, actual.rewardTypeName)
	}
}