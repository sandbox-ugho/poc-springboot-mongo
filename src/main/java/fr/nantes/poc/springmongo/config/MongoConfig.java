package fr.nantes.poc.springmongo.config;

import fr.nantes.poc.springmongo.data.entity.Customer;
import fr.nantes.poc.springmongo.data.repository.CustomerRepository;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
public class MongoConfig {

    @Resource
    private CustomerRepository repository;

    @PostConstruct
    public void initMongoDB() {
        repository.deleteAll();

        // save a couple of customers
        repository.save(new Customer("Alice", "Smith"));
        repository.save(new Customer("Bob", "Smith"));
        repository.save(new Customer("Martin", "Dupont"));
        repository.save(new Customer("Jeane", "Auscour"));
        repository.save(new Customer("Viktor", "Lamar"));
    }

}
