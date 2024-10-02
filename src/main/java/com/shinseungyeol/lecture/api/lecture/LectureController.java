package com.shinseungyeol.lecture.api.lecture;

import com.shinseungyeol.lecture.domain.lecture.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lecture")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @GetMapping("/possible")
    public ResponseEntity getPossibleLectures() {
        return ResponseEntity.ok().body(lectureService.findAllPossibleRegistration().stream().map(
            LectureResponseDto::of).toList());
    }

}
