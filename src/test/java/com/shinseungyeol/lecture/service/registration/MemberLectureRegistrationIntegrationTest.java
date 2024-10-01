package com.shinseungyeol.lecture.service.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.shinseungyeol.lecture.domain.lecture.Lecture;
import com.shinseungyeol.lecture.domain.lecture.LectureMetrics;
import com.shinseungyeol.lecture.domain.member.Member;
import com.shinseungyeol.lecture.exception.registration.MaxRegistrationLimitExceededException;
import com.shinseungyeol.lecture.infrastructure.lecture.LectureMetricsRepository;
import com.shinseungyeol.lecture.infrastructure.lecture.LectureRepository;
import com.shinseungyeol.lecture.infrastructure.member.MemberRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberLectureRegistrationIntegrationTest {

    private final List<Member> members = new ArrayList<>();
    @Autowired
    private MemberLectureRegistrationService memberLectureRegistrationService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private LectureMetricsRepository metricsRepository;
    private Lecture lecture;

    @BeforeEach
    public void init() {
        for (int i = 0; i < 40; i++) {
            members.add(memberRepository.save(Member.create("test" + i)));
        }

        lecture = lectureRepository.save(
            Lecture.create("test_lecture", "test_speaker", LocalDateTime.now(),
                LocalDateTime.now().plusDays(1), null, null));

        metricsRepository.save(LectureMetrics.create(lecture));
    }

    /**
     * 40 명이 동시에 신청할 때, 30명만 성공하는 것을 테스트 하는 함수
     *
     * @throws InterruptedException
     */
    @Test
    void testLectureRegistrationLimit() throws InterruptedException {
        final int TOTAL_USERS = 40;
        final int MAX_REGISTRATIONS = 30;
        ExecutorService executorService = Executors.newFixedThreadPool(TOTAL_USERS);
        CountDownLatch latch = new CountDownLatch(TOTAL_USERS);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        for (int i = 0; i < TOTAL_USERS; i++) {
            final long currentUserId = members.get(i).getId();

            executorService.submit(() -> {
                try {
                    memberLectureRegistrationService.register(currentUserId, lecture.getId());
                    successCount.incrementAndGet();

                } catch (MaxRegistrationLimitExceededException ex) {
                    failureCount.incrementAndGet();

                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        assertEquals(MAX_REGISTRATIONS, successCount.get());
        assertEquals(TOTAL_USERS - MAX_REGISTRATIONS, failureCount.get());
    }
}
