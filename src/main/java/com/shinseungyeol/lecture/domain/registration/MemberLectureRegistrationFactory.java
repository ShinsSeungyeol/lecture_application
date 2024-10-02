package com.shinseungyeol.lecture.domain.registration;

import com.shinseungyeol.lecture.domain.lecture.Lecture;
import com.shinseungyeol.lecture.domain.lecture.LectureMetrics;
import com.shinseungyeol.lecture.domain.lecture.LectureMetricsRepository;
import com.shinseungyeol.lecture.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberLectureRegistrationFactory {

    private final MemberLectureRegistrationValidator memberLectureRegistrationValidator;
    private final LectureMetricsRepository lectureMetricsRepository;

    /**
     * UserLectureRegistration 팩토리 함수
     *
     * @param lecture
     * @param member
     * @return
     */
    public MemberLectureRegistration newInstance(Lecture lecture, Member member) {
        LectureMetrics lectureMetrics = lectureMetricsRepository.findByLecture(lecture)
            .orElse(null);

        memberLectureRegistrationValidator.validate(lecture, lectureMetrics);

        lectureMetrics.incrementRegistrationCountByOne();

        return MemberLectureRegistration.create(lecture, member);
    }
}
