package com.shinseungyeol.lecture.infrastructure.registration;

import com.shinseungyeol.lecture.domain.member.Member;
import com.shinseungyeol.lecture.domain.registration.MemberLectureRegistration;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberLectureRegistrationRepository extends
    JpaRepository<MemberLectureRegistration, Long> {

    List<MemberLectureRegistration> findAllByMember(Member member);

}
