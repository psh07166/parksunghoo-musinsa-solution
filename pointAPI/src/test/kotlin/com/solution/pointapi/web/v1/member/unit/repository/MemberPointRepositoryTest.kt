package com.solution.pointapi.web.v1.member.unit.repository

import com.solution.pointapi.web.v1.member.entity.MemberPoint
import com.solution.pointapi.web.v1.member.repository.MemberPointRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.transaction.annotation.Transactional

@DataJpaTest
class MemberPointRepositoryTest @Autowired constructor (
	private var testEntityManager: TestEntityManager,
	private var memberPointRepository: MemberPointRepository
){
	@Test
	@Transactional
	fun `입력받은 id로 사용자 보상 정보를 조회한다`(){
		//given
		val expected = MemberPoint(memberId = 20, point = 0, getCount = 0)
		testEntityManager.persist(MemberPoint(memberId = 20, point = 0, getCount = 0))

		//when
		val actual = memberPointRepository.findByMemberIdForUpDate(20)

		//then
		Assertions.assertEquals(expected.memberId, actual.memberId)
		Assertions.assertEquals(expected.point, actual.point)
		Assertions.assertEquals(expected.getCount, actual.getCount)
	}
}