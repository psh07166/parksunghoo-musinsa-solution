package com.solution.pointapi.web.v1.rewards.reward.unit.repository

import com.solution.pointapi.web.v1.rewards.reward.entity.RewardDetail
import com.solution.pointapi.web.v1.rewards.reward.repository.RewardDetailRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.transaction.annotation.Transactional

@DataJpaTest
class RewardDetailRepositoryTest @Autowired constructor(
	private var testEntityManager: TestEntityManager,
	private var rewardDetailRepository: RewardDetailRepository
) {
	@Test
	@Transactional
	fun `입력받은 id로 보상 상세 데이터 조회를 조회한다`(){
		//given
		val expected = RewardDetail(rewardDetailId = 4, rewardId = 4, rewardCount = 10, quantity = 10, maxContinue = 10)
		testEntityManager.persist(expected)

		//when
		val actual = rewardDetailRepository.findByRewardIdForUpdate(4)

		//then
		Assertions.assertEquals(expected.rewardDetailId, actual.rewardDetailId)
		Assertions.assertEquals(expected.rewardId, actual.rewardId)
		Assertions.assertEquals(expected.rewardCount, actual.rewardCount)
		Assertions.assertEquals(expected.quantity, actual.quantity)
		Assertions.assertEquals(expected.maxContinue, actual.maxContinue)
	}
}