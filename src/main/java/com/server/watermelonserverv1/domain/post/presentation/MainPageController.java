package com.server.watermelonserverv1.domain.post.presentation;

import com.server.watermelonserverv1.domain.post.presentation.dto.response.MainPageResponse;
import com.server.watermelonserverv1.domain.post.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping("/main-page")
    public MainPageResponse mainPage() { return mainPageService.mainPage(); }
}
