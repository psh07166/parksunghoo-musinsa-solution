package com.solution.pointapi.web.v1.rewards.point.controller

import com.solution.pointapi.web.v1.point.service.RewardPointService
import com.solution.pointapi.web.v1.rewards.point.dto.RewardPointListResponse
import com.solution.pointapi.web.v1.rewards.point.dto.RewardPointPutRequest
import com.solution.pointapi.web.v1.rewards.point.dto.RewardPointResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/rewards/points", "/v1/rewards/points")
class RewardPointController(
	private val pointService: RewardPointService
) {
	@PutMapping
	fun take(
		@RequestBody requestBody: RewardPointPutRequest
	): ResponseEntity<HttpStatus> {
		val result = pointService.rewardPoint(requestBody)
		return ResponseEntity.created(URI.create("/rewards/points/${result.pointId}")).build()
	}

	@GetMapping("/detail/{pointId}")
	fun detail(
		@PathVariable pointId: Int
	):ResponseEntity<RewardPointResponse>{
		return ResponseEntity.ok(RewardPointResponse.of(pointService.getDetail(pointId)))
	}

	@GetMapping("/{date}")
	fun list(@PathVariable date: String
	): ResponseEntity<RewardPointListResponse> {
		return ResponseEntity.ok(RewardPointListResponse.of(pointService.getList(date)))
	}
}