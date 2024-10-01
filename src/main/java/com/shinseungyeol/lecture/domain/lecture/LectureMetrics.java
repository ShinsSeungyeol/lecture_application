package com.shinseungyeol.lecture.domain.lecture;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class LectureMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 등록 수
     */
    private Integer registeredCount = 0;

    /**
     * 강의 통계 정보 객체
     */
    @OneToOne
    @JoinColumn(nullable = false)
    private Lecture lecture;

    public static LectureMetrics create(Lecture lecture) {
        LectureMetrics metrics = new LectureMetrics();
        metrics.setLecture(lecture);

        return metrics;
    }


    /**
     * 등록수를 + 1 하는 함수
     */
    public void incrementRegistrationCountByOne() {
        setRegisteredCount(getRegisteredCount() + 1);
    }

}
