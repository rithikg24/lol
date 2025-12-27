package com.xyzbank.crm.web;

import com.xyzbank.crm.service.AnalyticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final AnalyticsService analyticsService;

    public HomeController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalCustomers", analyticsService.totalCustomers());
        model.addAttribute("recentInteractions", analyticsService.interactionsInLastDays(7));
        model.addAttribute("leadStatusCounts", analyticsService.leadStatusCounts());
        model.addAttribute("recommendations", analyticsService.simpleRecommendationsForTopCustomers(5));
        return "dashboard";
    }
}
