package com.tekarch.AccountService.Service.Interface;

import com.tekarch.AccountService.Model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account createAccount(Account account);

    Account updateAccount(Account account);

    Account updateAccountById(Long userId, Long accountId, Account updatedAccount);

    List<Account> getAllAccounts();

    List<Account> getAccountsByUserId(Long userId);

    Optional<Account> getAccountsByAccountId(Long accountId);

    public void deleteAccountById(Long accountId);

    Double getAccountBalanceByAccountId(Long accountId);

    List<Account> getAccountsBalanceByUserId(Long userId);

    Double getBalanceByAccountNumber(String accountNumber);

    public void deleteAccountByAccountType(Long userId, String accountType);


}
