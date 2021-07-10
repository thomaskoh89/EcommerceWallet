package com.ecommerce.wallet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.wallet.model.UserAccount;

@Repository
public interface AccountRepository extends CrudRepository<UserAccount, Long> {

	UserAccount findByEmail(String email);

}
