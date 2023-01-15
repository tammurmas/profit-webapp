package org.tamm.profit.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tamm.profit.dao.CustomerDAO;
import org.tamm.profit.dao.CustomerDAOImpl;
import org.tamm.profit.model.Customer;
import org.tamm.profit.model.FormValidator;

import jakarta.validation.Valid;

@Slf4j
@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    @GetMapping()
    public String customer(Model model) {
        model.addAttribute("customer", new Customer());

        CustomerDAO dao = CustomerDAOImpl.getDbCon();
        model.addAttribute("customers", dao.listCustomers());
        return "customer";
    }

    @PostMapping(value = "/add")
    public String addCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult result, ModelMap model) {

        FormValidator formValidator = new FormValidator();
        formValidator.validate(customer, result);

        if (result.hasErrors()) {
            return "customer";
        }

        CustomerDAO dao = CustomerDAOImpl.getDbCon();
        dao.addCustomer(customer);

        return "redirect:/customer";
    }

    @PostMapping("/remove")
    public String removeCustomer(@ModelAttribute("customer") Customer customer) {
        log.info("Removing customer with id={}", customer.getId());
        CustomerDAOImpl.getDbCon().removeCustomer(customer.getId());
        return "redirect:/customer";
    }

    @GetMapping("/edit/{id}")
    public String editCustomer(@PathVariable("id") int id, Model model) {
        model.addAttribute("customer", CustomerDAOImpl.getDbCon().getCustomerById(id));
        return "edit";
    }

    @PostMapping(value = "/update")
    public String updateCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult result, ModelMap model) {

        FormValidator formValidator = new FormValidator();//TODO: create some custom validator for altering username
        formValidator.validate(customer, result);

        if (result.hasErrors()) {
            return "edit";
        }

        CustomerDAOImpl.getDbCon().updateCustomer(customer);

        return "redirect:/customer";
    }
}
