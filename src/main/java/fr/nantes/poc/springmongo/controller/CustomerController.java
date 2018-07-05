package fr.nantes.poc.springmongo.controller;

import fr.nantes.poc.springmongo.data.entity.Customer;
import fr.nantes.poc.springmongo.data.repository.CustomerRepository;
import fr.nantes.poc.springmongo.exception.RestResponseException;
import fr.nantes.poc.springmongo.model.AddCustomerModel;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Resource
    private CustomerRepository repository;

    @GetMapping
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{idCustomer}")
    public Customer findById(@PathVariable(name = "idCustomer") String id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer addCustomer(@Valid @RequestBody AddCustomerModel model, BindingResult result) throws RestResponseException {
        if (result.hasErrors()) {
            throw new RestResponseException(HttpStatus.BAD_REQUEST, result);
        }
        return repository.save(new Customer(model.getFirstName(), model.getLastName()));
    }

    @PutMapping("/{idCustomer}")
    public Customer updateCustomer(@PathVariable(name = "idCustomer") String id,
                                   @Valid @RequestBody AddCustomerModel model) throws RestResponseException {
        Customer customer = repository.findById(id).orElse(null);

        if (customer == null) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, "Customer not found");
        }

        if (!StringUtils.isEmpty(model.getFirstName())) {
            customer.setFirstName(model.getFirstName());
        }

        if (!StringUtils.isEmpty(model.getLastName())) {
            customer.setFirstName(model.getLastName());
        }

        return repository.save(customer);
    }

    @DeleteMapping("/{idCustomer}")
    public void deleteCustomer(@PathVariable(name = "idCustomer") String id) {
        repository.deleteById(id);
    }
}
