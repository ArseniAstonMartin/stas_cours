package by.exam.prep.controller;

import by.exam.prep.dto.response.StudyMaterialResponse;
import by.exam.prep.service.StudyMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/materials")
@RequiredArgsConstructor
public class StudyMaterialController {

    private final StudyMaterialService studyMaterialService;

    @GetMapping
    public ResponseEntity<List<StudyMaterialResponse>> getMaterialsByTopic(
            @RequestParam Long topicId) {
        return ResponseEntity.ok(studyMaterialService.getMaterialsByTopic(topicId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyMaterialResponse> getMaterialById(@PathVariable Long id) {
        return ResponseEntity.ok(studyMaterialService.getMaterialById(id));
    }
}
