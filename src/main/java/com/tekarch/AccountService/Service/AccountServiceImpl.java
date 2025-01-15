package com.tekarch.AccountService.Service;

import com.tekarch.AccountService.Model.Account;
import com.tekarch.AccountService.Repository.AccountRepository;
import com.tekarch.AccountService.Service.Interface.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccountById(Long userId, Long accountId, Account updatedAccount) {
        Optional<Account> existingAccount = accountRepository.findById(accountId);
        if (existingAccount.isPresent()) {
            Account accountToUpdate = existingAccount.get();
            accountToUpdate.setBalance(updatedAccount.getBalance());
            accountToUpdate.setAccountType(updatedAccount.getAccountType());
            accountToUpdate.setCurrency(updatedAccount.getCurrency());
            return accountRepository.save(accountToUpdate);
        }
        throw new RuntimeException("Account not found");
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> getAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    @Override
    public Optional<Account> getAccountsByAccountId(Long accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public void deleteAccountById(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public Double getAccountBalanceByAccountId(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            return account.get().getBalance();
        }
        throw new RuntimeException("Account not found");
    }

    @Override
    public List<Account> getAccountsBalanceByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    @Override
    public Double getBalanceByAccountNumber(String accountNumber) {
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        if (account.isPresent()) {
            return account.get().getBalance();
        }
        throw new RuntimeException("Account not found");
    }

    @Override
    public void deleteAccountByAccountType(Long userId, String accountType) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        accounts.stream()
                .filter(account -> account.getAccountType().equalsIgnoreCase(accountType))
                .forEach(account -> accountRepository.delete(account));
    }
}
