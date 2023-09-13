package com.example.forms;

import com.example.forms.exception.ResourceNotFoundException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/form/listOfCustomers")
public class FormsApplication {

	private final CustomerRepository customerRepository;

	public FormsApplication(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(FormsApplication.class, args);
	}

	record NewCustomerRequest(String firstName, String lastName, String email, String dob) {
	}

	record ExistingCustomerRequest(String email, String dob){

	}

	@GetMapping
	public List<Customer> getCustomers(){
		return customerRepository.findAll();
	}

	@PostMapping
	public void addCustomer(@RequestBody NewCustomerRequest request){
		Customer customer = new Customer();
		customer.setFirstName(request.firstName());
		customer.setLastName(request.lastName());
		customer.setEmail(request.email());
		customer.setDob(request.dob());
		customerRepository.save(customer);
	}

	@DeleteMapping("{customerId}")
	public void deleteCustomer(@PathVariable("customerId") Integer id){
		customerRepository.deleteById(id);
	}

	@PutMapping("{customerId}")
	public void updateCustomer(@PathVariable("customerId") Integer id, @RequestBody ExistingCustomerRequest request){
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));
		customer.setEmail(request.email());
		customer.setDob(request.dob());
		customerRepository.save(customer);
	}

}
