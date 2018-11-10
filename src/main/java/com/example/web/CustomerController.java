package com.example.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.Customer;
import com.example.service.CustomerService;
import com.example.service.LoginUserDetails;

@Controller
@RequestMapping("customers")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@ModelAttribute
	public CustomerForm setUpForm() {
		return new CustomerForm();
	}
	
	@GetMapping
	public String list(Model model) {
		List<Customer> customers = customerService.findAll();
		model.addAttribute("customers", customers);
		return "customers/list";
	}
	
	@PostMapping(path = "create")
	public String create(@Validated CustomerForm form, BindingResult result, Model model,
			@AuthenticationPrincipal LoginUserDetails userDetails) {
		if (result.hasErrors()) {
			return list(model);
		}
		Customer customer = new Customer();
		BeanUtils.copyProperties(form, customer);
//		customerService.create(customer);
		customerService.create(customer, userDetails.getUser());
		return "redirect:/customers";
	}
	
	@GetMapping(path = "edit", params = "form")
	public String editForm(@RequestParam Integer id, CustomerForm form) {
		Optional<Customer> customer = customerService.findById(id);
		if (customer.isPresent()) {
			BeanUtils.copyProperties(customer.get(), form);
		}
		return "customers/edit";
	}
	
	@PostMapping(path = "edit")
	public String edit(@RequestParam Integer id, @Validated CustomerForm form, BindingResult result,
			@AuthenticationPrincipal LoginUserDetails userDetails) {
		if (result.hasErrors()) {
			return editForm(id, form);
		}
		Customer customer = new Customer();
		BeanUtils.copyProperties(form, customer);
		customer.setId(id);
//		customerService.update(customer);
		customerService.update(customer, userDetails.getUser());
		return "redirect:/customers";
	}
	
	@PostMapping(path = "edit", params = "goToTop")
	public String goToTop() {
		return "redirect:/customers";
	}
	
	@PostMapping(path = "delete")
	public String delete(@RequestParam Integer id) {
		customerService.delete(id);
		return "redirect:/customers";
	}

}
