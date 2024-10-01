package com.shinseungyeol.lecture.domain.registration;

import com.shinseungyeol.lecture.domain.base.BaseEntity;
import com.shinseungyeol.lecture.domain.lecture.Lecture;
import com.shinseungyeol.lecture.domain.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "user_lecture_registration",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"lecture_id", "member_id"})
    }
)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLectureRegistration extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Lecture lecture;

    @ManyToOne
    @JoinColumn
    private Member member;


    /**
     * 등록 정적 팩토리 함수
     *
     * @param lecture 강의
     * @param member  신청한 사용자
     * @return
     */
    public static MemberLectureRegistration create(Lecture lecture, Member member) {
        MemberLectureRegistration memberLectureRegistration = new MemberLectureRegistration();
        memberLectureRegistration.setLecture(lecture);
        memberLectureRegistration.setMember(member);

        return memberLectureRegistration;
    }

}
