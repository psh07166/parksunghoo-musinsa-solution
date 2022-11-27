package com.solution.pointapi.common.handler

import com.solution.pointapi.common.dto.BaseExceptionResponse
import com.solution.pointapi.common.exception.*
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice()
class RestExceptionHandler : ResponseEntityExceptionHandler() {

	override fun handleBindException(ex: BindException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
		val fieldErrors = ex.bindingResult.fieldErrors
		val objectErrors = ex.bindingResult.globalErrors

		return ResponseEntity(BaseExceptionResponse("invalid_arguments", "invalid_arguments"), headers, status)
	}

	@ExceptionHandler(ResourceNotFoundException::class)
	fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<BaseExceptionResponse> {
		return ResponseEntity(BaseExceptionResponse("resource_not_found", ex.message), HttpStatus.NOT_FOUND)
	}

	@ExceptionHandler(IllegalArgumentException::class)
	fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<BaseExceptionResponse> {
		return ResponseEntity(BaseExceptionResponse("illegal_argument", ex.message), HttpStatus.BAD_REQUEST)
	}

	@ExceptionHandler(FailedUpdateException::class)
	fun handleFailedUpdateException(ex: FailedUpdateException): ResponseEntity<BaseExceptionResponse> {
		return ResponseEntity(BaseExceptionResponse("update_failed", ex.message), HttpStatus.INTERNAL_SERVER_ERROR)
	}

	@ExceptionHandler(ResourceConflictException::class)
	fun handleFailedUpdateException(ex: ResourceConflictException): ResponseEntity<BaseExceptionResponse> {
		return ResponseEntity(BaseExceptionResponse("resource_conflict", ex.message), HttpStatus.CONFLICT)
	}

}