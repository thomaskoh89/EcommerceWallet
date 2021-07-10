package com.ecommerce.wallet.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.wallet.model.Wallet;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, Long> {

	@Modifying
	@Query("update Wallet w set w.balanceAmt = ?2 where w.id = ?1")
	int updateWallet(int id, double amount);

}
