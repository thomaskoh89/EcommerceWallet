package com.ecommerce.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.wallet.model.TransactionRecord;
import com.ecommerce.wallet.model.UserAccount;
import com.ecommerce.wallet.model.Wallet;
import com.ecommerce.wallet.repository.AccountRepository;
import com.ecommerce.wallet.repository.TransactionRepository;
import com.ecommerce.wallet.repository.WalletRepository;



@Service
public class AccountServiceInpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	WalletRepository walletRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	public UserAccount createAccount(UserAccount userAccount) {
		Wallet wallet = new Wallet();	
		wallet.setBalanceAmt(10000.00);
		userAccount.setWallet(wallet);
		return accountRepository.save(userAccount);
	}

	public UserAccount checkBalance(String email) {
		UserAccount userAccount = accountRepository.findByEmail(email);
		return userAccount;
	}
	
	@Transactional(rollbackFor = {HandledException.class, Exception.class} )
	public boolean transferAmount(UserAccount transferAccount, UserAccount transfereeAccount, double amount) throws HandledException{
			TransactionRecord record = new TransactionRecord();
			record.setFrom(transferAccount.getEmail());
			record.setTo(transfereeAccount.getEmail());
			record.setAmount(amount);
			record.setType("transfer");

			transactionRepository.save(record);
			
			transferAccount.getWallet().setBalanceAmt(transferAccount.getWallet().getBalanceAmt() - amount);
			int updateTransferWallet = walletRepository.updateWallet(transferAccount.getWallet().getId(),
					transferAccount.getWallet().getBalanceAmt());
			
			transfereeAccount.getWallet().setBalanceAmt(transfereeAccount.getWallet().getBalanceAmt() + amount);
			int updateTransfereeWallet = walletRepository.updateWallet(transfereeAccount.getWallet().getId(),
					transfereeAccount.getWallet().getBalanceAmt());
			
			if (updateTransferWallet == 0 || updateTransfereeWallet == 0) {
				throw new HandledException("Failed to update");
			} else {
				return true;
			}
	}
}
