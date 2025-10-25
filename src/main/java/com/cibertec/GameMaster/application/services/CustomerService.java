package com.cibertec.GameMaster.application.services;

import com.cibertec.GameMaster.application.port.CustomerPort;
import com.cibertec.GameMaster.infraestructure.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerPort port;

    @Autowired
    private CustomerMapper mapper;

//    public CustomerDTO getCustomerById(Long id){
//        return mapper.toDTO(port.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Customer", id)
//        ));
//    }

}
