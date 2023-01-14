package org.sigma.sigmastatistics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sigma.sigmastatistics.model.dto.TransactionDTO;
import org.sigma.sigmastatistics.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TransactionsService transactionsService;

    @Test
    @DisplayName("Add Transaction - Success")
    void addTransaction_success() throws Exception {
        TransactionDTO transaction = new TransactionDTO(BigDecimal.ONE, LocalDateTime.now(ZoneId.of("UTC")));
        doNothing().when(transactionsService).add(any(TransactionDTO.class));
        mockMvc.perform(post("/transactions").content(mapper.writeValueAsString(transaction)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Add Transaction - Empty payload")
    void addTransaction_success_empty() throws Exception {
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Delete Transaction - Success")
    void delete_success() throws Exception {
        doNothing().when(transactionsService).deleteAll();
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


}
