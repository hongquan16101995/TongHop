package controller;

import model.Customer;
import model.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import service.CustomerService;
import service.ProvinceService;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> provinces(){
        return provinceService.findAll();
    }

    @GetMapping("/customer")
    public ModelAndView showListCustomer(){
        Iterable<Customer> customers = customerService.findAll();
        return new ModelAndView("/customer/list", "customers", customers);
    }

    @GetMapping("/create-customer")
    public ModelAndView createCustomer(){
        return new ModelAndView("/customer/create", "customer", new Customer());
    }

    @PostMapping("/create-customer")
    public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer, Model model){
        customerService.save(customer);
        model.addAttribute("message", "New customer created successfully");
        return showListCustomer();
    }

    @GetMapping("/edit-customer/{id}")
    public ModelAndView editCustomer(@PathVariable long id){
        Customer customer = customerService.findById(id);
        if(customer != null) {
            return new ModelAndView("/customer/edit", "customer", customer);
        }else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/edit-customer/{id}")
    public ModelAndView updateCustomer(@ModelAttribute("customer") Customer customer, Model model){
        customerService.save(customer);
        model.addAttribute("message", "Customer updated successfully");
        return showListCustomer();
    }

    @GetMapping("/delete-customer/{id}")
    public ModelAndView deleteCustomer(@PathVariable long id){
        Customer customer = customerService.findById(id);
        if(customer != null) {
            return new ModelAndView("/customer/delete", "customer", customer);
        }else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/delete-customer/{id}")
    public ModelAndView removeCustomer(@ModelAttribute("customer") Customer customer, Model model){
        customerService.remove(customer.getId());
        model.addAttribute("message", "Customer deleted successfully");
        return showListCustomer();
    }

    @GetMapping("/view-customer/{id}")
    public ModelAndView showCustomer(@PathVariable long id){
        Customer customer = customerService.findById(id);
        if(customer != null) {
            return new ModelAndView("/customer/view", "customer", customer);
        }else {
            return new ModelAndView("/error.404");
        }
    }
}
