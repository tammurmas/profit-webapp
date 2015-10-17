package org.tamm.profit;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.tamm.profit.dao.CustomerDAO;
import org.tamm.profit.dao.CustomerDAOImpl;
import org.tamm.profit.model.Customer;
import org.tamm.profit.model.FormValidator;

@Controller
public class CustomerController {
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public ModelAndView customer() {
		ModelAndView mv = new ModelAndView("customer");
		mv.addObject("customer", new Customer());

		CustomerDAO dao = CustomerDAOImpl.getDbCon();
		mv.addObject("list", dao.listCustomers());
		return mv;
	}

	@RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
	public String addCustomer(
			@Valid @ModelAttribute("customer") Customer customer,
			BindingResult result, ModelMap model) {

		FormValidator formValidator = new FormValidator();
		formValidator.validate(customer, result);

		if (result.hasErrors()) {
			return "customer";
		}

		CustomerDAO dao = CustomerDAOImpl.getDbCon();
		dao.addCustomer(customer);

		return "redirect:/customer";
	}

	@RequestMapping("/remove/{id}")
	public String removeCustomer(@PathVariable("id") int id) {
		CustomerDAOImpl.getDbCon().removeCustomer(id);
		return "redirect:/customer";
	}
	
	@RequestMapping("/edit/{id}")
    public String editCustomer(@PathVariable("id") int id, Model model){
        model.addAttribute("customer", CustomerDAOImpl.getDbCon().getCustomerById(id));
        return "edit";
    }
	
	@RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
	public String updateCustomer(
			@Valid @ModelAttribute("customer") Customer customer,
			BindingResult result, ModelMap model) {

		FormValidator formValidator = new FormValidator();//TODO: create some custom validator for altering username
		formValidator.validate(customer, result);

		if (result.hasErrors()) {
			List<ObjectError> errs = result.getAllErrors();
			for (ObjectError objectError : errs) {
				System.out.println(objectError);
			}
			return "edit";
		}

		CustomerDAOImpl.getDbCon().updateCustomer(customer);

		return "redirect:/customer";
	}
}
