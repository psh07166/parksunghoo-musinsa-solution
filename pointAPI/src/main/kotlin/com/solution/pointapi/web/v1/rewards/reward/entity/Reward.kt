package com.solution.pointapi.web.v1.rewards.reward.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "REWARD")
@EntityListeners(AuditingEntityListener::class)
data class Reward(
	@Id
	@Column(name = "REWARD_ID")
	val rewardId: Int? = null,

	@Column(name = "SUBJECT")
	val subject: String,

	@Column(name = "CONTENT")
	val content: String,

	@Column(name = "REWARD_TYPE")
	val rewardType: Int,

	@Column(name = "REWARD_TYPE_NAME")
	val rewardTypeName: String,

	@Column(name = "CREATE_DATE", updatable = false)
	@CreationTimestamp
	val createdAt: LocalDateTime? = null,

	@Column(name = "UPDATE_DATE")
	@UpdateTimestamp
	val updatedAt: LocalDateTime? = null,

	@Column(name = "DELETE_FLAG")
	val delFlg: Boolean = false
)
