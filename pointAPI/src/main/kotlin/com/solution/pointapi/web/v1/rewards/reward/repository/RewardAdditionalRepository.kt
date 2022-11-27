package com.solution.pointapi.web.v1.rewards.reward.repository

import com.solution.pointapi.web.v1.rewards.reward.entity.RewardAdditional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RewardAdditionalRepository: JpaRepository<RewardAdditional, Int> {

	@Query("select t from RewardAdditional t join fetch t.reward where t.reward.rewardId = :rewardId and t.delFlg = false ")
	fun findByRewardId(rewardId: Int): List<RewardAdditional>
}