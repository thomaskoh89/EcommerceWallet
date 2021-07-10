package com.ecommerce.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecommerce.wallet.model.TransactionRecord;
import com.ecommerce.wallet.model.UserAccount;
import com.ecommerce.wallet.model.Wallet;
import com.ecommerce.wallet.repository.AccountRepository;
import com.ecommerce.wallet.repository.TransactionRepository;
import com.ecommerce.wallet.repository.WalletRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
class WalletApplicationTests {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	WalletRepository walletRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testFindByEmail() throws Exception {

		UserAccount testData = new UserAccount("test@hotmail.com");
		entityManager.persist(testData);
		entityManager.flush();

		UserAccount result = accountRepository.findByEmail(testData.getEmail());
		assertEquals(result.getEmail(), testData.getEmail());
	}

	@Test
	public void testRetrieveTransactionHistory() throws Exception {

		TransactionRecord trData1 = new TransactionRecord("from@hotmail.com", "to@hotmail.com", "transfer", 20);
		TransactionRecord trData2 = new TransactionRecord("to@hotmail.com", "from@hotmail.com", "transfer", 20);
		entityManager.persist(trData1);
		entityManager.persist(trData2);
		entityManager.flush();

		List<TransactionRecord> result = transactionRepository.retrieveTransactionHistory("from@hotmail.com");
		assertEquals(result.size(), 2, 0);
	}

	@Test
	public void testUpdateWalletil() throws Exception {
		UserAccount userData = new UserAccount("test@hotmail.com");
		Wallet w = new Wallet(10000);
		userData.setWallet(w);
		entityManager.persist(userData);
		entityManager.flush();

		int result = walletRepository.updateWallet(userData.getWallet().getId(), 2000);

		assertEquals(result, 1);
	}

}
