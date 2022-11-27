package com.solution.pointapi.web.v1.member.dto

import com.solution.pointapi.web.v1.member.entity.MemberPoint
import org.springframework.data.domain.Page
import java.util.stream.Collectors

class MemberPointListResponse (
	private val memberPoints: Map<String, Any>
) {
	fun getData() = memberPoints

	companion object {
		fun of(memberPoint: List<MemberPoint>) : MemberPointListResponse {

			val result: Map<String, Any> = mapOf(
				"content" to memberPoint.stream().map {
					mapOf(

						"memberId" to it.memberId,
						"point" to it.point,
						"getCount" to it.getCount,
						"getDate" to it.updatedAt
					)
				}.collect(Collectors.toList())
			)
			return MemberPointListResponse(result)
		}
	}
}