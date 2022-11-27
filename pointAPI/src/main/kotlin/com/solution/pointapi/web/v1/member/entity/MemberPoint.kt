package com.solution.pointapi.web.v1.member.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "MEMBER_POINT")
@EntityListeners(AuditingEntityListener::class)
data class MemberPoint(
	@Id
	@Column(name = "MEMBER_ID")
	val memberId: Int,

	@Column(name = "POINT")
	var point: Int,

	@Column(name = "GET_COUNT")
	var getCount: Int,

	@Column(name = "CREATE_DATE", updatable = false)
	@CreationTimestamp
	val createdAt: LocalDateTime? = null,

	@Column(name = "UPDATE_DATE")
	@UpdateTimestamp
	val updatedAt: LocalDateTime? = null,

	@Column(name = "DELETE_FLAG")
	val delFlg: Boolean = false
)
