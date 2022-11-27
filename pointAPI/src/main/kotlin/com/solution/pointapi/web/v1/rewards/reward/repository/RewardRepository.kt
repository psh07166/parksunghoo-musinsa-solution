package com.solution.pointapi.web.v1.rewards.reward.repository

import com.solution.pointapi.web.v1.rewards.reward.entity.Reward
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RewardRepository: JpaRepository<Reward, Int> {

	@Query("select t from Reward t where t.rewardId = :rewardId and t.delFlg = false ")
	fun findByRewardId(rewardId: Int): Reward
}