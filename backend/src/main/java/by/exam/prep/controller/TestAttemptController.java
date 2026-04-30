package by.exam.prep.controller;

import by.exam.prep.dto.request.SubmitAnswerRequest;
import by.exam.prep.dto.response.TestAttemptResponse;
import by.exam.prep.entity.User;
import by.exam.prep.security.CurrentUser;
import by.exam.prep.service.TestAttemptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/attempts")
@RequiredArgsConstructor
public class TestAttemptController {

    private final TestAttemptService testAttemptService;

    @PostMapping("/start/{testId}")
    public ResponseEntity<TestAttemptResponse> startAttempt(
            @PathVariable Long testId,
            @CurrentUser User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(testAttemptService.startAttempt(testId, user));
    }

    @PostMapping("/{attemptId}/answer")
    public ResponseEntity<TestAttemptResponse> submitAnswer(
            @PathVariable Long attemptId,
            @Valid @RequestBody SubmitAnswerRequest request,
            @CurrentUser User user) {
        return ResponseEntity.ok(testAttemptService.submitAnswer(attemptId, request, user));
    }

    @PostMapping("/{attemptId}/complete")
    public ResponseEntity<TestAttemptResponse> completeAttempt(
            @PathVariable Long attemptId,
            @CurrentUser User user) {
        return ResponseEntity.ok(testAttemptService.completeAttempt(attemptId, user));
    }

    @GetMapping("/my")
    public ResponseEntity<Page<TestAttemptResponse>> getMyAttempts(
            @CurrentUser User user,
            Pageable pageable) {
        return ResponseEntity.ok(testAttemptService.getUserAttempts(user.getId(), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestAttemptResponse> getAttemptById(@PathVariable Long id) {
        return ResponseEntity.ok(testAttemptService.getAttemptById(id));
    }
}
