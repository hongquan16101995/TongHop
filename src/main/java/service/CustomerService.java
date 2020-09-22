package service;

import model.Customer;
import model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    Page<Customer> findAll(Pageable pageable);

    Customer findById(Long id);

    void save(Customer customer);

    void remove(Long id);

    Page<Customer> findAllByProvince(Province province, Pageable pageable);

    Page<Customer> findAllByLastNameContaining(String lastname, Pageable pageable);
}
