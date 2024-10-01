package com.shinseungyeol.lecture.controller.registration;

import com.shinseungyeol.lecture.service.registration.MemberLectureRegistrationService;
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

    @PostMapping("/member-lecture")
    public ResponseEntity createMemberLectureRegistration(
        @RequestBody CreateMemberLectureRegistrationDto dto) {
        memberLectureRegistrationService.register(dto.getMemberId(), dto.getLectureId());

        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/member-lecture")
    public ResponseEntity getMemberLectureRegistration(@RequestParam Long memberId) {
        return ResponseEntity.ok()
            .body(memberLectureRegistrationService.findAllByMemberId(memberId).stream()
                .map(MemberLectureRegistrationResponseDto::of).toList());
    }
}
