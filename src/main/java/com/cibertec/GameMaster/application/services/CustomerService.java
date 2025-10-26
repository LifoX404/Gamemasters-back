package com.cibertec.GameMaster.application.services;

import com.cibertec.GameMaster.application.port.CustomerPort;
import com.cibertec.GameMaster.infraestructure.mapper.CustomerMapper;
import com.cibertec.GameMaster.infraestructure.web.dto.CustomerDTO;
import com.cibertec.GameMaster.infraestructure.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerPort port;

    @Autowired
    private CustomerMapper mapper;

    public CustomerDTO getCustomerById(Long id) {
        return mapper.toDTO(port.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id)));
    }

    public List<CustomerDTO> getCustomers() {
        return port.findAll().stream()
                .map(customer -> mapper.toDTO(customer))
                .toList();
    }

    public void deleteCustomer(Long id){
        port.logicDelete(id);
    }

}
