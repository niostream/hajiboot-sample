package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Customer;
import com.example.domain.User;
import com.example.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;
	
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}
	
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}
	
	public Optional<Customer> findById(Integer id) {
		return customerRepository.findById(id);
	}
	
//	public Customer create(Customer customer) {
//		return customerRepository.save(customer);
//	}
//	
//	public Customer update(Customer customer) {
//		return customerRepository.save(customer);
//	}
	
	public Customer create(Customer customer, User user) {
		customer.setUser(user);
		return customerRepository.save(customer);
	}
	
	public Customer update(Customer customer, User user) {
		customer.setUser(user);
		return customerRepository.save(customer);
	}
	
	public void delete(Integer id) {
		customerRepository.deleteById(id);
	}

}
