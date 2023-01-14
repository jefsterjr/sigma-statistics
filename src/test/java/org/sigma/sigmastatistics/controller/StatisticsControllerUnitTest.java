package org.sigma.sigmastatistics.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sigma.sigmastatistics.model.dto.StatisticsDTO;
import org.sigma.sigmastatistics.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatisticsController.class)
class StatisticsControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionsService transactionsService;

    @Test
    @DisplayName("Get Statistics - Success")
    void getStatistics_success() throws Exception {
        when(transactionsService.getStatistics()).thenReturn(new StatisticsDTO(BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, 1));
        mockMvc.perform(get("/statistics").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.sum").value("10.0"));
    }

    @Test
    @DisplayName("Get Statistics - Success - No Transactions")
    void getStatistics_success_empty() throws Exception {
        when(transactionsService.getStatistics()).thenReturn(new StatisticsDTO(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0));
        mockMvc.perform(get("/statistics").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.sum").value("0.0"));
    }
}
