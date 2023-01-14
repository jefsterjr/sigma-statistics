package org.sigma.sigmastatistics.controller;

import org.sigma.sigmastatistics.model.dto.TransactionDTO;
import org.sigma.sigmastatistics.service.TransactionsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionsService service;

    public TransactionController(TransactionsService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void transactions(@RequestBody TransactionDTO transaction) {
        service.add(transaction);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransactions() {
        service.deleteAll();
    }
}
