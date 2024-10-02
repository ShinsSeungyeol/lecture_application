package com.shinseungyeol.lecture.api.registration;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shinseungyeol.lecture.domain.lecture.Lecture;
import com.shinseungyeol.lecture.domain.lecture.LectureMetrics;
import com.shinseungyeol.lecture.domain.lecture.LectureMetricsRepository;
import com.shinseungyeol.lecture.domain.lecture.LectureRepository;
import com.shinseungyeol.lecture.domain.member.Member;
import com.shinseungyeol.lecture.domain.member.MemberRepository;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LectureMetricsRepository lectureMetricsRepository;

    private Member savedMember;
    private Lecture savedFirstLecture;
    private Lecture savedSecondLecture;
    private Lecture savedThirdLecture;

    @BeforeEach
    void setUp() {
        savedMember = memberRepository.save(Member.create("shinseungyeol"));

        savedFirstLecture = lectureRepository.save(
            Lecture.create("opp", "toby", LocalDateTime.now(),
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3),
                LocalDateTime.now().plusDays(3).plusHours(2)));

        lectureMetricsRepository.save(LectureMetrics.create(savedFirstLecture));

        savedSecondLecture = lectureRepository.save(
            Lecture.create("tdd", "tom", LocalDateTime.now(),
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3),
                LocalDateTime.now().plusDays(3).plusHours(2)));
        lectureMetricsRepository.save(LectureMetrics.create(savedSecondLecture));

        savedThirdLecture = lectureRepository.save(
            Lecture.create("clean", "jery", LocalDateTime.now(),
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3),
                LocalDateTime.now().plusDays(3).plusHours(2)));
        lectureMetricsRepository.save(LectureMetrics.create(savedThirdLecture));
    }

    /**
     * 수강 신청과 목록 조회 테스트 등록 후, 조회할 때마다 목록 사이즈 +1 되는지 테스트
     *
     * @throws Exception
     */
    @Test
    public void 수강_신청_정상_목록_조회_통합_테스트() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Long memberId = savedMember.getId();

        mockMvc.perform(post("/registration/member-lecture")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    Map.of("memberId", memberId, "lectureId", savedFirstLecture.getId()))))
            .andExpect(status().isOk())
            .andExpect(content().string("success"));

        mockMvc.perform(get("/registration/member-lecture")
                .param("memberId", String.valueOf(memberId))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(post("/registration/member-lecture")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    Map.of("memberId", memberId, "lectureId", savedSecondLecture.getId()))))
            .andExpect(status().isOk())
            .andExpect(content().string("success"));

        mockMvc.perform(get("/registration/member-lecture")
                .param("memberId", String.valueOf(memberId))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(post("/registration/member-lecture")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    Map.of("memberId", memberId, "lectureId", savedThirdLecture.getId()))))
            .andExpect(status().isOk())
            .andExpect(content().string("success"));

        mockMvc.perform(get("/registration/member-lecture")
                .param("memberId", String.valueOf(memberId))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)));
    }


}