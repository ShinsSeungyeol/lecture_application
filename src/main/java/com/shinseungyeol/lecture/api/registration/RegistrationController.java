package com.shinseungyeol.lecture.api.registration;

import com.shinseungyeol.lecture.domain.lecture.LectureService;
import com.shinseungyeol.lecture.domain.registration.MemberLectureRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final MemberLectureRegistrationService memberLectureRegistrationService;
    private final LectureService lectureService;

    @PostMapping("/member-lecture")
    public ResponseEntity createMemberLectureRegistration(
        @RequestBody MemberLectureRegistrationRequestDto dto) {
        memberLectureRegistrationService.register(dto.memberId(), dto.lectureId());

        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/member-lecture")
    public ResponseEntity getMemberLectureRegistration(@RequestParam Long memberId) {
        return ResponseEntity.ok()
            .body(memberLectureRegistrationService.findAllByMemberId(memberId).stream()
                .map(MemberLectureRegistrationResponseDto::of).toList());
    }
}
