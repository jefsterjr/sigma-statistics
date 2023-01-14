package org.sigma.sigmastatistics.controller;

import org.sigma.sigmastatistics.model.dto.StatisticsDTO;
import org.sigma.sigmastatistics.service.TransactionsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final TransactionsService transactionsService;

    public StatisticsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @GetMapping
    public StatisticsDTO statistics() {
        return transactionsService.getStatistics();
    }


}
