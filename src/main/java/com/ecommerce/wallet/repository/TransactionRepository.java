package com.ecommerce.wallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.wallet.model.TransactionRecord;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionRecord, Long> {

	@Query("SELECT tr FROM TransactionRecord tr WHERE tr.to = ?1 or tr.from = ?1")
	List<TransactionRecord> retrieveTransactionHistory(String email);

}
