package com.digital.wallet.customer_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private String userId;
    @NotBlank
    private String name;
    @NotEmpty
    private Date dob;
    private String address;
    private List<String> contactNumbers;
    private List<String> friends;
}
