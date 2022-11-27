package com.solution.pointapi.web.v1.rewards.point.entity

import com.solution.pointapi.web.v1.member.entity.MemberPoint
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "POINT")
@EntityListeners(AuditingEntityListener::class)
data class RewardPoint(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POINT_ID")
	val pointId: Int? = null,

	//@Column(name = "MEMBER_ID")
	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	val memberPoint: MemberPoint,

	@Column(name = "POINT")
	val point: Int,

	@Column(name = "CREATE_DATE", updatable = false)
	@CreationTimestamp
	val createdAt: LocalDateTime? = null,

	@Column(name = "UPDATE_DATE")
	@UpdateTimestamp
	val updatedAt: LocalDateTime? = null,

	@Column(name = "DELETE_FLAG")
	val delFlg: Boolean = false
)
