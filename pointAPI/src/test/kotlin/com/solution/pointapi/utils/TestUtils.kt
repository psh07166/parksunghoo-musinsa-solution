package com.solution.pointapi.utils

import org.mockito.Mockito

class TestUtils private constructor() {

	companion object {
		fun <T> isA(type: Class<T>): T {
			Mockito.isA<T>(type)
			return nullReturn()
		}

		fun <T> any(): T {
			Mockito.any<T>()
			return null as T
		}

		private fun <T> nullReturn(): T = null as T
	}
}