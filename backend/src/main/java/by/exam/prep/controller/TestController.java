package by.exam.prep.controller;

import by.exam.prep.dto.request.CreateTestRequest;
import by.exam.prep.dto.response.TestResponse;
import by.exam.prep.entity.User;
import by.exam.prep.security.CurrentUser;
import by.exam.prep.service.TestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tests")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping
    public ResponseEntity<Page<TestResponse>> getTests(
            @RequestParam(required = false) Long subjectId,
            Pageable pageable) {
        return ResponseEntity.ok(testService.getTests(subjectId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestResponse> getTestById(@PathVariable Long id) {
        return ResponseEntity.ok(testService.getTestById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<TestResponse> createTest(
            @Valid @RequestBody CreateTestRequest request,
            @CurrentUser User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(testService.createTest(request, user));
    }
}
