package com.macyscart.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.macyscart.demo.entity.Account;

@Repository
public interface AccountDAO extends JpaRepository<Account, String>{

	Account findByUserName(String username);

}
