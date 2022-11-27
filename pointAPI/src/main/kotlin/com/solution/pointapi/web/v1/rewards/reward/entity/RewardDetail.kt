package com.solution.pointapi.web.v1.rewards.reward.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "REWARD_DETAIL")
@EntityListeners(AuditingEntityListener::class)
data class RewardDetail(
	@Id
	@Column(name = "REWARD_DETAIL_ID")
	val rewardDetailId: Int? = null,

	@Column(name = "REWARD_ID")
	val rewardId: Int,

	@Column(name = "REWARD_COUNT")
	val rewardCount: Int,

	@Column(name = "QUANTITY")
	var quantity: Int,

	@Column(name = "MAX_CONTINUE")
	val maxContinue: Int,

	@Column(name = "CREATE_DATE", updatable = false)
	@CreationTimestamp
	val createdAt: LocalDateTime? = null,

	@Column(name = "UPDATE_DATE")
	@UpdateTimestamp
	val updatedAt: LocalDateTime? = null,

	@Column(name = "DELETE_FLAG")
	val delFlg: Boolean = false
)
