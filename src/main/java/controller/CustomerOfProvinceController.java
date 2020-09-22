package controller;

import model.Customer;
import model.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
public class CustomerOfProvinceController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/view-province")
    public ModelAndView viewProvince(Long id, @SortDefault(sort = {"lastName"}) @PageableDefault(value = 10) Pageable pageable){
        Province province = provinceService.findById(id);
        if(province == null){
            return new ModelAndView("/error.404");
        }

        Page<Customer> customers = customerService.findAllByProvince(province, pageable);

        ModelAndView modelAndView = new ModelAndView("/province/view");
        modelAndView.addObject("province", province);
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @ModelAttribute("provinces")
    public Iterable<Province> provinces(){
        return provinceService.findAll();
    }

    @GetMapping("/create-customer-province/{idp}")
    public ModelAndView createCustomer(@PathVariable("idp") long idp, Model model){
        model.addAttribute("idp", idp);
        return new ModelAndView("/customer/create", "customer", new Customer());
    }

    @PostMapping("/create-customer-province/{idp}")
    public ModelAndView saveCustomer(@PathVariable("idp") long idp, @ModelAttribute("customer") Customer customer, Model model,
                                     @SortDefault (sort = {"lastName"}) @PageableDefault(value = 10)Pageable pageable){
        customerService.save(customer);
        model.addAttribute("message", "New customer created successfully");
        return viewProvince(idp, pageable);
    }

    @GetMapping("/edit-customer-province/{id}/{idp}")
    public ModelAndView editCustomer(@PathVariable long id, @PathVariable("idp") long idp, Model model){
        Customer customer = customerService.findById(id);
        if(customer != null) {
            model.addAttribute("idp", idp);
            return new ModelAndView("/customer/edit", "customer", customer);
        }else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/edit-customer-province/{id}/{idp}")
    public ModelAndView updateCustomer(@PathVariable("idp") long idp, @ModelAttribute("customer") Customer customer, Model model,
                                       @SortDefault (sort = {"lastName"}) @PageableDefault(value = 10) Pageable pageable){
        customerService.save(customer);
        model.addAttribute("message", "Customer updated successfully");
        return viewProvince(idp, pageable);
    }

    @GetMapping("/delete-customer-province/{id}/{idp}")
    public ModelAndView deleteCustomer(@PathVariable long id, @PathVariable("idp") long idp, Model model){
        Customer customer = customerService.findById(id);
        if(customer != null) {
            model.addAttribute("idp", idp);
            return new ModelAndView("/customer/delete", "customer", customer);
        }else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/delete-customer-province/{id}/{idp}")
    public ModelAndView removeCustomer(@PathVariable("idp") long idp, @ModelAttribute("customer") Customer customer, Model model,
                                       @SortDefault (sort = {"lastName"}) @PageableDefault(value = 10) Pageable pageable){
        customerService.remove(customer.getId());
        model.addAttribute("message", "Customer deleted successfully");
        return viewProvince(idp, pageable);
    }
}
