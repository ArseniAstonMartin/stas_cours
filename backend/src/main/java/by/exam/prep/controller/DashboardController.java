package by.exam.prep.controller;

import by.exam.prep.dto.response.DashboardResponse;
import by.exam.prep.entity.User;
import by.exam.prep.security.CurrentUser;
import by.exam.prep.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard(@CurrentUser User user) {
        return ResponseEntity.ok(dashboardService.getDashboard(user.getId()));
    }
}
