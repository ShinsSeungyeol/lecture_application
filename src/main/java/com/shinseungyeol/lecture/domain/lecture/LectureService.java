package com.shinseungyeol.lecture.domain.lecture;

import com.shinseungyeol.lecture.domain.registration.MemberLectureRegistrationValidator;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final LectureMetricsRepository lectureMetricsRepository;
    private final MemberLectureRegistrationValidator memberLectureRegistrationValidator;

    /**
     * 등록 가능한 강의 목록 조회
     *
     * @return
     */
    public List<Lecture> findAllPossibleRegistration() {
        return lectureRepository.findAllPossibleLectures(LocalDateTime.now(),
            memberLectureRegistrationValidator.getMaxRegistrationLimit());
    }

}
