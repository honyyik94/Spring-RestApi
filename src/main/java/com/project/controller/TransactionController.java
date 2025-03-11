package com.project.controller;

import com.project.dto.TransactionsDTO;
import com.project.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    TransactionServices transactionServices;

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint!";
    }

    @GetMapping("/transaction/{pageNo}/{pageSize}")
    public ResponseEntity<PagedModel<?>> getAllTransactionsByPage(@PathVariable int pageNo,
                                                                  @PathVariable int pageSize,
                                                                  @RequestParam(value= "accountNumber", required = false) Long accountNumber,
                                                                  @RequestParam(value= "customerId", required = false) Long customerId,
                                                                  @RequestParam(value= "description", required = false) String description) {
        try {
            return transactionServices.getTransactionsRecords(pageNo, pageSize,
                    accountNumber, customerId, description);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/transaction/update/{id}")
    public ResponseEntity<String> updateTransaction(@PathVariable("id") String id,
                                                @RequestBody TransactionsDTO transactionsDTO) {
        return transactionServices.updateTransaction(id, transactionsDTO);
    }


}
