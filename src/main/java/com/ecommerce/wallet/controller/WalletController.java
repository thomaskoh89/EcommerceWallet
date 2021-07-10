package com.ecommerce.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.wallet.model.Wallet;
import com.ecommerce.wallet.service.WalletService;


@RestController
@RequestMapping("/api")
public class WalletController {
	
	@Autowired
	WalletService walletService;
	
}
