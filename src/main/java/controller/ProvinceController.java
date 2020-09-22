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
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/province")
    public ModelAndView showListProvince(){
        Iterable<Province> provinces = provinceService.findAll();
        return new ModelAndView("/province/list", "provinces", provinces);
    }

    @GetMapping("/create-province")
    public ModelAndView createProvince(){
        return new ModelAndView("/province/create", "province", new Province());
    }

    @PostMapping("/create-province")
    public ModelAndView saveProvince(@ModelAttribute("province") Province province, Model model){
        provinceService.save(province);
        model.addAttribute("message", "New province created successfully");
        return showListProvince();
    }

    @GetMapping("/edit-province/{id}")
    public ModelAndView editProvince(@PathVariable long id){
        Province province = provinceService.findById(id);
        if(province != null) {
            return new ModelAndView("/province/edit", "province", province);
        }else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/edit-province/{id}")
    public ModelAndView updateProvince(@ModelAttribute("province") Province province, Model model){
        provinceService.save(province);
        model.addAttribute("message", "Province updated successfully");
        return showListProvince();
    }

    @GetMapping("/delete-province/{id}")
    public ModelAndView deleteProvince(@PathVariable long id){
        Province province = provinceService.findById(id);
        if(province != null) {
            return new ModelAndView("/province/delete", "province", province);
        }else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/delete-province/{id}")
    public ModelAndView removeProvince(@ModelAttribute("province") Province province, Model model){
        provinceService.remove(province.getId());
        model.addAttribute("message", "Province deleted successfully");
        return showListProvince();
    }

    @GetMapping("/view-province/{id}")
    public ModelAndView viewProvince(@PathVariable("id") Long id, @SortDefault(sort = {"lastName"}) @PageableDefault(value = 10) Pageable pageable){
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
}
