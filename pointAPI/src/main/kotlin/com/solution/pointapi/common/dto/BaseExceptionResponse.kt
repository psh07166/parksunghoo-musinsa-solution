package com.solution.pointapi.common.dto

import java.util.*

data class BaseExceptionResponse(
	val code: String?,
	val message: String?,
	val timestamp: Long = Date().toInstant().toEpochMilli()
)
