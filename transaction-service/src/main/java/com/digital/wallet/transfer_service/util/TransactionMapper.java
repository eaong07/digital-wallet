package com.digital.wallet.transfer_service.util;

import com.digital.wallet.transfer_service.dto.TransactionDto;
import com.digital.wallet.transfer_service.entity.Transaction;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TransactionDto toDto(Transaction user);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Transaction toEntity(TransactionDto userDto);
}
