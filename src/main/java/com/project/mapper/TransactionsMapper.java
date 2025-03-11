package com.project.mapper;

import com.project.dto.TransactionsDTO;
import com.project.entity.Transactions;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionsMapper {

    TransactionsMapper INSTANCE = Mappers.getMapper( TransactionsMapper.class );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    @Mapping(target = "transactionAmount", ignore = true)
    @Mapping(target = "transactionDate", ignore = true)
    @Mapping(target = "transactionTime", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "version", ignore = true)
    Transactions transactionDTOToTransaction(TransactionsDTO transactionsDTO,
                                             @MappingTarget Transactions transactions);
}
