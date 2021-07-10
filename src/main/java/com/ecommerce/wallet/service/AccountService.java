package com.ecommerce.wallet.service;

import com.ecommerce.wallet.model.UserAccount;

public interface AccountService {
	
	public UserAccount createAccount(UserAccount userAccount);
	public UserAccount checkBalance(String email);
	public boolean transferAmount(UserAccount transferAccount, UserAccount transfereeAccount, double amount) throws HandledException;
	
}
