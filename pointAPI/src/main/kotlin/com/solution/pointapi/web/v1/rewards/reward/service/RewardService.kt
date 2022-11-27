package com.solution.pointapi.web.v1.rewards.reward.service

import com.solution.pointapi.web.v1.rewards.reward.entity.Reward
import com.solution.pointapi.web.v1.rewards.reward.entity.RewardAdditional
import com.solution.pointapi.web.v1.rewards.reward.entity.RewardDetail
import com.solution.pointapi.web.v1.rewards.reward.repository.RewardAdditionalRepository
import com.solution.pointapi.web.v1.rewards.reward.repository.RewardDetailRepository
import com.solution.pointapi.web.v1.rewards.reward.repository.RewardRepository
import org.springframework.stereotype.Service

@Service
class RewardService(
	private val rewardRepository: RewardRepository,
	private val rewardDetailRepository: RewardDetailRepository,
	private val rewardAdditionalRepository: RewardAdditionalRepository
) {
	fun getDetail(id: Int): Reward{
		return rewardRepository.findByRewardId(id)
	}
	fun getRewardDetail(id: Int): RewardDetail{
		return rewardDetailRepository.findByRewardIdForUpdate(id)
	}

	fun saveRewardDetail(rewardDetail: RewardDetail): RewardDetail{
		return rewardDetailRepository.save(rewardDetail)
	}

	fun getRewardAdditional(id: Int): List<RewardAdditional>{
		return rewardAdditionalRepository.findByRewardId(id)
	}
}