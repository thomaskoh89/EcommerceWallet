package com.ecommerce.wallet.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.wallet.model.Constants;
import com.ecommerce.wallet.model.TransactionRecord;
import com.ecommerce.wallet.model.UserAccount;
import com.ecommerce.wallet.repository.AccountRepository;
import com.ecommerce.wallet.repository.TransactionRepository;
import com.ecommerce.wallet.service.AccountService;

@RestController
@RequestMapping("/api")
public class AccountController {

	@Autowired
	AccountService accountService;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	TransactionRepository transactionRepository;

	@PostMapping("/userAccount")
	public ResponseEntity<?> createAccount(@RequestBody UserAccount userAccount) {
		JSONObject resp = new JSONObject();
		try{
			UserAccount createAccount = accountService.createAccount(userAccount);
			if(createAccount !=null) {
				resp.put("success", Constants.TRUE);
				resp.put("balance", userAccount.getWallet().getBalanceAmt());
			}else {
				resp.put("success", Constants.FALSE);
			}
		}catch(Exception e){
			resp.put("success", Constants.FALSE);
		}	
		return new ResponseEntity<String>(resp.toString(), HttpStatus.CREATED);
	}
	
	@PostMapping("/retrieveBalance")
	public ResponseEntity<?> retrieveBalance(@RequestBody Map<String, ?>input) {
		
		
		String email = input.get("email").toString();
		JSONObject resp = new JSONObject();
		
		if(email.isEmpty()) {
			resp.put("success", Constants.FALSE);
			resp.put("errorMsg", Constants.INVALID_USER);
			return new ResponseEntity<String>(resp.toString(), HttpStatus.CREATED);	
		}

		try{
			UserAccount result = accountService.checkBalance(email);
			if(result !=null) {
					resp.put("success", Constants.TRUE);
					resp.put("balance", result.getWallet().getBalanceAmt());
			}else {
				resp.put("success", Constants.FALSE);
				resp.put("errorMsg", Constants.INVALID_USER);
			}
		}catch(Exception e){
			resp.put("success", Constants.FALSE);
		}
		return new ResponseEntity<String>(resp.toString(), HttpStatus.CREATED);	
	}
	
	@PostMapping("/transferAmount")
	public ResponseEntity<?> transferAmount(@RequestBody Map<String, ?> inputs) {
		JSONObject resp = new JSONObject();

		String transferEmail = inputs.get("email").toString();
		String transfereeEmail = inputs.get("transferee").toString();
		double amount = Double.parseDouble(inputs.get("amount").toString());

		UserAccount transferAccount = accountRepository.findByEmail(transferEmail);
		UserAccount transfereeAccount = accountRepository.findByEmail(transfereeEmail);

		try {

			if (transferAccount == null || transfereeAccount == null) {
				resp.put("success", Constants.FALSE);
				resp.put("errorMsg", Constants.INVALID_USER);
			} else {
				if (transferAccount.getWallet() == null || transfereeAccount.getWallet() == null) {
					resp.put("success", Constants.FALSE);
					resp.put("errorMsg", Constants.INVALID_USER);
				} else {
					if (transferAccount.getWallet().getBalanceAmt() < amount) {
						resp.put("success", Constants.FALSE);
						resp.put("errorMsg", Constants.INSUFFICIENT_BALANCE);
					} else {
						boolean result = accountService.transferAmount(transferAccount, transfereeAccount, amount);
						if (result) {
							resp.put("success", Constants.TRUE);
						} else {
							resp.put("success", Constants.FALSE);
							resp.put("errorMsg", Constants.TRANSFERERROR);
						}
					}
				}
			}
		} catch (Exception e) {
			resp.put("success", Constants.FALSE);
		}

		return new ResponseEntity<String>(resp.toString(), HttpStatus.CREATED);
	}
	
	
	@PostMapping("/transactionRecord")
	public ResponseEntity<?> transactionRecord(@RequestBody Map<String, ?>inputs) {
		JSONObject resp = new JSONObject();
		String email = inputs.get("email").toString();
		
		UserAccount account = accountRepository.findByEmail(email);
		
		try {

			if (account == null) {
				resp.put("success", Constants.FALSE);
				resp.put("errorMsg", Constants.INVALID_USER);
			}else {
				List<TransactionRecord> result = transactionRepository.retrieveTransactionHistory(account.getEmail());
				
				resp.put("success", Constants.TRUE);
				resp.put("transactions", result);
			}
		}catch (Exception e) {
				resp.put("success", Constants.FALSE);
			}
		
		return new ResponseEntity<String>(resp.toString(), HttpStatus.CREATED);
	}

}
