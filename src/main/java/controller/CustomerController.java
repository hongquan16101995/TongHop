package controller;

import model.Customer;
import model.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public ModelAndView showListCustomer(@PageableDefault (value = 5) Pageable pageable){
        Page<Customer> customers = customerService.findAll(pageable);
        return new ModelAndView("/customer/list", "customers", customers);
    }

    @GetMapping("/customer-create")
    public ModelAndView createCustomer(){
        return new ModelAndView("/customer/create", "customer", new Customer());
    }
}
