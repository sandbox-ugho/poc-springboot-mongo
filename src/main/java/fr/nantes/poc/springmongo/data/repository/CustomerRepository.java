package fr.nantes.poc.springmongo.data.repository;

import fr.nantes.poc.springmongo.data.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}