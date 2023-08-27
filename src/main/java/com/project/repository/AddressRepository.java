package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.modal.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
