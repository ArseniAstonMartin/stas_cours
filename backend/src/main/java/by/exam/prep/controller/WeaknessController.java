package by.exam.prep.controller;

import by.exam.prep.dto.response.WeaknessResponse;
import by.exam.prep.entity.User;
import by.exam.prep.security.CurrentUser;
import by.exam.prep.service.WeaknessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/weaknesses")
@RequiredArgsConstructor
public class WeaknessController {

    private final WeaknessService weaknessService;

    @GetMapping
    public ResponseEntity<List<WeaknessResponse>> getWeaknesses(@CurrentUser User user) {
        return ResponseEntity.ok(weaknessService.getUserWeaknesses(user.getId()));
    }

    @GetMapping("/critical")
    public ResponseEntity<List<WeaknessResponse>> getCritical(@CurrentUser User user) {
        return ResponseEntity.ok(weaknessService.getCriticalWeaknesses(user.getId()));
    }
}
