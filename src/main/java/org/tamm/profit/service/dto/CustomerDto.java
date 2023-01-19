package org.tamm.profit.service.dto;

import java.text.SimpleDateFormat;

import lombok.Getter;
import lombok.Setter;

import org.tamm.profit.model.Customer;
import org.tamm.profit.service.CustomerService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class CustomerDto {

    private Long id;

    @Size(max = 100)
    @NotBlank(message = "Firstname left blank!")
    private String firstname;

    @Size(max = 100)
    @NotBlank(message = "Lastname left blank!")
    private String lastname;

    @NotBlank(message = "Birth date left blank!")
    private String birthDate;

    @Size(max = 100)
    @NotBlank(message = "Username left blank!")
    private String username;

    @Size(max = 100)
    @NotBlank(message = "Password left blank!")
    private String password;

    public static CustomerDto of(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setFirstname(customer.getFirstname());
        dto.setLastname(customer.getLastname());
        dto.setBirthDate(CustomerService.DATE_FORMAT.format(customer.getBirthDate()));
        dto.setUsername(customer.getUsername());
        dto.setPassword(customer.getPassword());
        return dto;
    }

}
