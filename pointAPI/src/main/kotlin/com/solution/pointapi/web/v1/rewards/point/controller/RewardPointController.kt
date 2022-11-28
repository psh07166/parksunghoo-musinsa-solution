package com.solution.pointapi.web.v1.rewards.point.controller

import com.solution.pointapi.web.v1.point.service.RewardPointService
import com.solution.pointapi.web.v1.rewards.point.dto.RewardPointListRequest
import com.solution.pointapi.web.v1.rewards.point.dto.RewardPointListResponse
import com.solution.pointapi.web.v1.rewards.point.dto.RewardPointPutRequest
import com.solution.pointapi.web.v1.rewards.point.dto.RewardPointResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/rewards/points", "/v1/rewards/points")
class RewardPointController(
	private val rewardPointService: RewardPointService
) {
	@PutMapping
	fun rewardPoint(
		@RequestBody requestBody: RewardPointPutRequest
	): ResponseEntity<HttpStatus> {
		val result = rewardPointService.rewardPoint(requestBody)
		return ResponseEntity.created(URI.create("/rewards/points/${result.pointId}")).build()
	}

	@GetMapping("/detail/{pointId}")
	fun detail(
		@PathVariable pointId: Int
	):ResponseEntity<RewardPointResponse>{
		return ResponseEntity.ok(RewardPointResponse.of(rewardPointService.getDetail(pointId)))
	}

	@GetMapping("/list")
	fun list(@PageableDefault(sort = ["createdAt"], direction = Sort.Direction.ASC) pageable: Pageable,
	         conditions: RewardPointListRequest
	): ResponseEntity<RewardPointListResponse> {
		return ResponseEntity.ok(RewardPointListResponse.of(rewardPointService.getList(conditions, pageable)))
	}
}