package com.shinseungyeol.lecture.domain.lecture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.shinseungyeol.lecture.domain.registration.MemberLectureRegistrationValidator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

    private final List<Lecture> lectures = new ArrayList<>();
    @InjectMocks
    private LectureService lectureService;
    @Mock
    private LectureRepository lectureRepository;
    @Mock
    private LectureMetricsRepository lectureMetricsRepository;
    @Mock
    private MemberLectureRegistrationValidator memberLectureRegistrationValidator;

    @BeforeEach
    public void init() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);

        for (int i = 0; i < 10; i++) {
            lectures.add(
                Lecture.create("name" + i, "speaker" + i, today, tomorrow, tomorrow, tomorrow));
        }
    }

    /**
     * 등록 가능 10개를 정상 반환한다.
     */
    @Test
    public void 목록_반환_테스트() {

        when(lectureRepository.findAllPossibleLectures(any(), any())).thenReturn(lectures);

        List<Lecture> actualPossibleLectures = lectureService.findAllPossibleRegistration();

        Assertions.assertEquals(lectures.size(), actualPossibleLectures.size());
    }
}