package com.solution.pointapi.web.v1.rewards.point.unit.service

import com.solution.pointapi.web.v1.member.repository.MemberPointRepository
import com.solution.pointapi.web.v1.point.service.RewardPointService
import com.solution.pointapi.web.v1.rewards.point.dto.RewardPointListRequest
import com.solution.pointapi.web.v1.rewards.point.dto.RewardPointPutRequest
import com.solution.pointapi.web.v1.rewards.point.repository.RewardPointRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
@AutoConfigureMockMvc
class RewardPointConcurrencyTest @Autowired constructor(
	private val rewardPointRepository: RewardPointRepository,
	private val memberPointRepository: MemberPointRepository,
	private val rewardPointService: RewardPointService
) {
	@Test
	fun `동시에 여러번 보상을 지급한다`(){
		val successCount = AtomicInteger()
		val numberOfExcute = 60
		val service = Executors.newFixedThreadPool(20)
		val latch = CountDownLatch(numberOfExcute)
		val pageRequest = PageRequest.of(0, 10, Sort.by("createdAt").ascending())
		for (i in 0 until numberOfExcute) {
			service.execute {
				try {
					val pointRequest = RewardPointPutRequest(i,1)
					val result = rewardPointService.rewardPoint(pointRequest)
					successCount.getAndIncrement()

				} catch (e: Exception) {
					println(e.message)
				}
				latch.countDown()
			}
		}
		latch.await();
		val searchDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
		val request = RewardPointListRequest(searchDate)
		val pointResult = rewardPointService.getList(request, pageRequest)
		Assertions.assertEquals(10, pointResult.size)
		Assertions.assertEquals(10, successCount.get())

		rewardPointRepository.deleteAll()
		memberPointRepository.deleteAll()
	}
}
