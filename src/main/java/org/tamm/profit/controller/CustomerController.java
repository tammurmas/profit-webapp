package org.tamm.profit.controller;

import java.text.ParseException;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tamm.profit.model.Customer;
import org.tamm.profit.service.CustomerService;
import org.tamm.profit.service.dto.CustomerDto;

import jakarta.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("customer", new CustomerDto());
        model.addAttribute("customers", customerService.findAllCustomers());
        return "customer";
    }

    @PostMapping(value = "/add")
    public String addCustomer(@Valid @ModelAttribute("customer") CustomerDto customer, BindingResult result, Model model) {
        validate(customer, result);
        if (result.hasErrors()) {
            return "customer";
        }
        customerService.save(customer);

        return "redirect:/customer";
    }

    @PostMapping("/remove")
    public String removeCustomer(@ModelAttribute("customer") Customer customer) {
        log.info("Removing customer with id={}", customer.getId());
        customerService.delete(customer.getId());
        return "redirect:/customer";
    }

    @GetMapping("/edit/{id}")
    public String editCustomer(@PathVariable("id") int id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "edit";
    }

    @PostMapping(value = "/update")
    public String updateCustomer(@Valid @ModelAttribute("customer") CustomerDto customer, BindingResult result, ModelMap model) {
        validate(customer, result);
        if (result.hasErrors()) {
            return "edit";
        }
        customerService.save(customer);

        return "redirect:/customer";
    }

    private void validate(CustomerDto customer, Errors errors) {
        if (customer.getBirthDate() == null) {
            errors.rejectValue("birthDate", "", "Date of birth left blank!");
        } else {
            try {
                CustomerService.DATE_FORMAT.parse(customer.getBirthDate());
            } catch (ParseException e) {
                errors.rejectValue("birthDate", "", "Date of birth not valid!");
            }
        }

        Optional<CustomerDto> optionalCustomer = customerService.findByUsername(customer.getUsername());
        if (optionalCustomer.isPresent()) {
            boolean sameCustomer = (customer.getId().equals(optionalCustomer.get().getId()));
            if (!sameCustomer) {
                errors.rejectValue("username", "Username already in use!", "Username already in use!");
            }
        }
    }
}
