package com.project.services;

import com.project.dto.TransactionsDTO;
import com.project.entity.Transactions;
import com.project.mapper.TransactionsMapper;
import com.project.repository.TransactionsRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TransactionServices {

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    PagedResourcesAssembler<Transactions> pagedAssembler;

    public ResponseEntity<PagedModel<?>> getTransactionsRecords(int pageNo, int pageSize,
                                                                Long accountNumber, Long customerId,
                                                                String description) {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        Page<Transactions>  pagedTransactionList = transactionsRepository.
                    findTransactionByKeywords(accountNumber, customerId, description, paging);

        if(pagedTransactionList.getTotalElements() <=0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(pagedAssembler.toModel(pagedTransactionList), HttpStatus.OK);

        }
    }

    public ResponseEntity<String> updateTransaction(String id, TransactionsDTO transactionDTO) {

        if(Strings.isBlank(transactionDTO.getDescription())) {
            return new ResponseEntity<>("Description cannot be blank", HttpStatus.BAD_REQUEST);
        }

        Optional<Transactions> transactionResult  = transactionsRepository.findById(id);
        if (transactionResult.isPresent()) {

            Transactions transaction = TransactionsMapper.INSTANCE
                    .transactionDTOToTransaction(transactionDTO, transactionResult.get());
            transactionsRepository.save(transaction);

            return new ResponseEntity<>(" Transactions was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find  Transactions with id=" + id, HttpStatus.NOT_FOUND);
        }
    }
}
