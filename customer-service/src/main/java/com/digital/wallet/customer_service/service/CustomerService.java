package com.digital.wallet.customer_service.service;

import com.digital.wallet.customer_service.dto.CustomerDto;
import com.digital.wallet.customer_service.entity.Customer;
import com.digital.wallet.customer_service.repository.CustomerRepository;
import com.digital.wallet.customer_service.util.CustomerMapper;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public CustomerDto getUser(String id) {
        Optional<Customer> user = customerRepository.findById(id);

        return user.
                map(a -> {
                    return CustomerDto
                            .builder()
                            .userId(a.getUserId())
                            .dob(a.getDob())
                            .name(a.getName())
                            .address(a.getAddress())
                            .contactNumbers(a.getContactNumbers())
                            .friends(a.getFriends())
                            .build();
                }).orElse(null);
    }

    public void addUser(CustomerDto customerDto) {
        customerRepository.save(customerMapper.toEntity(customerDto));
    }

    public void updateUser(CustomerDto customerDto) {
        Optional<Customer> result = customerRepository.findById(customerDto.getUserId());
        Customer customer = result.orElse(null);
        if (Objects.nonNull(customer)) {
            customer = customerMapper.toEntity(customerDto);
            customerRepository.save(customer);
        }
    }

    public void addFriends(String userId, String friendId) {
        List<Customer> customers = customerRepository.findByUserIdIn(userId, friendId);
        if (customers.size() == 2) {
            List<Customer> updatedList = customers.stream()
                    .map(customer -> {
                        List<String> friends = customer.getFriends() != null
                                ? new ArrayList<>(customer.getFriends())
                                : new ArrayList<>();
                        friends.add(customer.getUserId().equals(userId) ? friendId : userId);
                        customer.setFriends(friends.parallelStream().distinct().toList());
                        return customer;
                    }).toList();
            customerRepository.saveAll(updatedList);
        } else {
            throw new RuntimeException("Friend is not real");
        }
    }

}
