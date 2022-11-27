package com.solution.pointapi.web.v1.rewards.reward.repository

import com.solution.pointapi.web.v1.rewards.reward.entity.RewardDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import javax.persistence.LockModeType

interface RewardDetailRepository: JpaRepository<RewardDetail, Int> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select t from RewardDetail t where t.rewardId = :rewardId and t.delFlg = false ")
	fun findByRewardIdForUpdate(rewardId: Int): RewardDetail
}