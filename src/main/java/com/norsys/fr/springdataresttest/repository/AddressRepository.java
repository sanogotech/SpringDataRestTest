package com.norsys.fr.springdataresttest.repository;

import org.springframework.data.repository.CrudRepository;
import com.norsys.fr.springdataresttest.entity.Address;

public interface AddressRepository extends CrudRepository<Address, String>{

}
