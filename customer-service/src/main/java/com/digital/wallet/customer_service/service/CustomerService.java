package com.digital.wallet.customer_service.service;

import com.digital.wallet.customer_service.dto.CustomerDto;
import com.digital.wallet.customer_service.entity.Customer;
import com.digital.wallet.customer_service.repository.CustomerRepository;
import com.digital.wallet.customer_service.util.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public CustomerDto getUser(String id) {
        log.info("Search customer repo for {}", id);
        Optional<Customer> user = customerRepository.findById(id);
        log.info("Customer {} was found: {}", id, user.isPresent());
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
        Customer c = customerRepository.save(customerMapper.toEntity(customerDto));
        log.info("Customer {} was added", c.getUserId());
    }

    public void updateUser(CustomerDto customerDto) {
        Optional<Customer> result = customerRepository.findById(customerDto.getUserId());
        log.info("Customer {} found: {}", customerDto.getUserId(), result.isPresent());
        Customer customer = result.orElse(null);
        if (Objects.nonNull(customer)) {
            log.info("Update customer {} details", customerDto.getUserId());
            customer = customerMapper.toEntity(customerDto);
            customerRepository.save(customer);
            log.info("Succesfully updated customer {} details", customer.getUserId());
        }
    }

    public void addFriends(String userId, String friendId) {
        log.info("Search if user {} and firend {} exists", userId, friendId);
        List<Customer> customers = customerRepository.findByUserIdIn(userId, friendId);
        if (customers.size() == 2) {
            log.info("Both user {} and friend {} were found", userId, friendId);
            List<Customer> updatedList = customers.stream()
                    .map(customer -> {
                        List<String> friends = customer.getFriends() != null
                                ? new ArrayList<>(customer.getFriends())
                                : new ArrayList<>();
                        friends.add(customer.getUserId().equals(userId) ? friendId : userId);
                        customer.setFriends(friends.parallelStream().distinct().toList());
                        return customer;
                    }).toList();
            log.info("Mapped user {} to friend {}, proceeding with save", userId, friendId);
            customerRepository.saveAll(updatedList);
            log.info("User {} and friend {} relationship has been saved", userId, friendId);
        } else {
            log.error("User {} or Friend {} does not exist", userId, friendId);
            throw new RuntimeException("Friend is not real");
        }
    }

}
