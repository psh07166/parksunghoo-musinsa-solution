package com.solution.pointapi.web.v1.rewards.reward.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "REWARD_ADDITIONAL")
@EntityListeners(AuditingEntityListener::class)
data class RewardAdditional(
	@Id
	@Column(name = "REWARD_ADDITIONAL_ID")
	val rewardAdditionalId: Int? = null,

	@ManyToOne
	@JoinColumn(name = "REWARD_ID")
	val reward: Reward,

	@Column(name = "CONTINUE_DAY")
	val continueDay: Int,

	@Column(name = "ADD_REWARD")
	val addReward: Int,

	@Column(name = "CREATE_DATE", updatable = false)
	@CreationTimestamp
	val createdAt: LocalDateTime? = null,

	@Column(name = "UPDATE_DATE")
	@UpdateTimestamp
	val updatedAt: LocalDateTime? = null,

	@Column(name = "DELETE_FLAG")
	val delFlg: Boolean = false
)
