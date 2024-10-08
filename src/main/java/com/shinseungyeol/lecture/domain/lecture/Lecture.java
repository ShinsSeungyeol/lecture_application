package com.shinseungyeol.lecture.domain.lecture;

import com.shinseungyeol.lecture.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String speakerName;

    /**
     * 강의 이름
     */
    @Column(nullable = false)
    private String name;

    /**
     * 신청 시작 시간
     */
    @Column(nullable = false)
    private LocalDateTime registrationStartAt;

    /**
     * 신청 종료 시간
     */
    @Column(nullable = false)
    private LocalDateTime registrationEndAt;

    /**
     * 강의 시작 시간
     */
    private LocalDateTime startAt;

    /**
     * 강의 종료 시간
     */
    private LocalDateTime endAt;


    /**
     * @param name
     * @param speakerName
     * @param registrationStartTime
     * @param registrationEndTime
     * @param startTime
     * @param endTime
     * @return
     */
    public static Lecture create(String name, String speakerName,
        LocalDateTime registrationStartTime, LocalDateTime registrationEndTime,
        LocalDateTime startTime, LocalDateTime endTime) {

        Lecture lecture = new Lecture();

        lecture.setName(name);
        lecture.setSpeakerName(speakerName);
        lecture.setRegistrationStartAt(registrationStartTime);
        lecture.setRegistrationEndAt(registrationEndTime);
        lecture.setStartAt(startTime);
        lecture.setEndAt(endTime);

        return lecture;
    }
}
