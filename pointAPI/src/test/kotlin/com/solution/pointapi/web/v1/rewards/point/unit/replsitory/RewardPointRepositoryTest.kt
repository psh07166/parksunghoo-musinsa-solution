package com.solution.pointapi.web.v1.rewards.point.unit.replsitory

import com.solution.pointapi.web.v1.member.entity.MemberPoint
import com.solution.pointapi.web.v1.rewards.point.entity.RewardPoint
import com.solution.pointapi.web.v1.rewards.point.repository.RewardPointRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@DataJpaTest
class RewardPointRepositoryTest @Autowired constructor(
	private var testEntityManager: TestEntityManager,
	private var pointRepository: RewardPointRepository

) {
	@Test
	@Transactional
	fun `입력받은 id로 보상 데이터 조회를 조회한다`(){
		//given
		val memberPoint = MemberPoint(memberId = 1, point = 0, getCount = 0)
		val expected = RewardPoint(memberPoint = memberPoint, point = 0)
		testEntityManager.entityManager.createNativeQuery("ALTER TABLE POINT ALTER COLUMN POINT_ID RESTART WITH 1").executeUpdate();
		testEntityManager.persist(RewardPoint(memberPoint = memberPoint, point = 0))

		//when
		val actual = pointRepository.findByPointId(1)

		//then
		Assertions.assertEquals(expected.memberPoint, actual!!.memberPoint)
		Assertions.assertEquals(expected.point, actual.point)
	}

	@Test
	@Transactional
	fun `지정한 날짜에 포함되는 보상 데이터를 조회한다`(){
		//given
		val date: LocalDate = LocalDate.now()
		val fromTime: LocalTime = LocalTime.of(0,0,0)
		val toTime: LocalTime = LocalTime.of(23,59,59)
		val fromDateTime: LocalDateTime = LocalDateTime.of(date, fromTime)
		val toDateTime: LocalDateTime = LocalDateTime.of(date, toTime)
		val memberPoints: List<MemberPoint> = listOf(MemberPoint(memberId = 1, point = 0, getCount = 0),
			MemberPoint(memberId = 2, point = 0, getCount = 0),
			MemberPoint(memberId = 3, point = 0, getCount = 0))
		val expected: List<RewardPoint> = listOf(RewardPoint(memberPoint = memberPoints[0], point = 0),
			RewardPoint(memberPoint = memberPoints[1], point = 0),
			RewardPoint(memberPoint = memberPoints[2], point = 0))
		testEntityManager.entityManager.createNativeQuery("ALTER TABLE POINT ALTER COLUMN POINT_ID RESTART WITH 1").executeUpdate();
		testEntityManager.persist(RewardPoint(memberPoint = memberPoints[0], point = 0))
		testEntityManager.persist(RewardPoint(memberPoint = memberPoints[1], point = 0))
		testEntityManager.persist(RewardPoint(memberPoint = memberPoints[2], point = 0))

		//when
		val actual = pointRepository.findByUpdatedAt(fromDateTime, toDateTime)

		//then
		Assertions.assertEquals(expected[0].memberPoint, actual.get(0).memberPoint)
		Assertions.assertEquals(expected[1].point, actual[1].point)
	}

	@Test
	@Transactional
	fun `지정한 사용자와 날짜에 포함되는 보상 데이터를 조회한다`(){
		//given
		val date: LocalDate = LocalDate.now()
		val fromTime: LocalTime = LocalTime.of(0,0,0)
		val toTime: LocalTime = LocalTime.of(23,59,59)
		val fromDateTime: LocalDateTime = LocalDateTime.of(date, fromTime)
		val toDateTime: LocalDateTime = LocalDateTime.of(date, toTime)
		val memberPoints: List<MemberPoint> = listOf(MemberPoint(memberId = 1, point = 0, getCount = 0),
			MemberPoint(memberId = 2, point = 0, getCount = 0),
			MemberPoint(memberId = 3, point = 0, getCount = 0))
		val expected: List<RewardPoint> = listOf(RewardPoint(memberPoint = memberPoints[0], point = 0),
			RewardPoint(memberPoint = memberPoints[1], point = 0),
			RewardPoint(memberPoint = memberPoints[2], point = 0))
		testEntityManager.entityManager.createNativeQuery("ALTER TABLE POINT ALTER COLUMN POINT_ID RESTART WITH 1").executeUpdate();
		testEntityManager.persist(RewardPoint(memberPoint = memberPoints[0], point = 0))
		testEntityManager.persist(RewardPoint(memberPoint = memberPoints[1], point = 0))
		testEntityManager.persist(RewardPoint(memberPoint = memberPoints[2], point = 0))

		//when
		val actual = pointRepository.findByMemberIdAndUpdatedAtForUpdate(1, fromDateTime, toDateTime)

		//then
		Assertions.assertEquals(expected[0].memberPoint, actual?.memberPoint)
		Assertions.assertEquals(expected[1].point, actual?.point)
	}
}