package com.solution.pointapi.web.v1.member.controller

import com.solution.pointapi.web.v1.member.dto.MemberPointListResponse
import com.solution.pointapi.web.v1.member.service.MemberPointService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/memberPoints", "/v1/memberPoints")
class MemberPointController (
	private val memberPointService: MemberPointService
) {

}