package com.solution.pointapi.web.v1.rewards.reward.controller

import com.solution.pointapi.web.v1.rewards.reward.dto.RewardResponse
import com.solution.pointapi.web.v1.rewards.reward.service.RewardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rewards", "/v1/rewards")
class RewardController(
	private val rewardService: RewardService
) {
	@GetMapping("/detail/{rewardId}")
	fun detail(
		@PathVariable rewardId: Int
	): ResponseEntity<RewardResponse> {
		return ResponseEntity.ok(RewardResponse.of(rewardService.getDetail(rewardId)))
	}

}