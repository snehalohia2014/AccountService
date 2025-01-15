package com.tekarch.AccountService.Controller;

import com.tekarch.AccountService.Model.Account;
import com.tekarch.AccountService.Service.AccountServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = LogManager.getLogger(AccountController.class);

    private final AccountServiceImpl accountServiceImpl;

    public AccountController(AccountServiceImpl accountServiceImpl) {
        this.accountServiceImpl = accountServiceImpl;
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        return new ResponseEntity<>(accountServiceImpl.createAccount(account), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllAccounts() {
        return new ResponseEntity<>(accountServiceImpl.getAllAccounts(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateAccount(@RequestBody Account account) {
        return new ResponseEntity<>(accountServiceImpl.updateAccount(account), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAccountsByUserId(@PathVariable Long userId) {
        List<Account> accounts = accountServiceImpl.getAccountsByUserId(userId);
        if (accounts.isEmpty()) {
            throw new AccountNotFoundException("No accounts found for user with ID: " + userId);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<?> getAccountsByAccountId(@PathVariable Long accountId) {
        Optional <Account> accounts = accountServiceImpl.getAccountsByAccountId(accountId);
        if (accounts.isEmpty()) {
            throw new AccountNotFoundException("No accounts found for user with ID: " + accountId);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<?> deleteAccountById(@PathVariable Long accountId) {
        accountServiceImpl.deleteAccountById(accountId);
        return new ResponseEntity<>("Account deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getAccountBalance(
            @RequestParam(required = false) Long accountId,
            @RequestParam(required = false) String accountNumber) {
        try {
            if (accountId != null) {
                Double balance = accountServiceImpl.getAccountBalanceByAccountId(accountId);
                return new ResponseEntity<>(balance, HttpStatus.OK);
            } else if (accountNumber != null) {
                Double balance = accountServiceImpl.getBalanceByAccountNumber(accountNumber);
                return new ResponseEntity<>(balance, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Missing parameters", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/balance/{userId}")
    public ResponseEntity<?> getBalanceByUserId(@PathVariable Long userId) {
        List<Account> accounts = accountServiceImpl.getAccountsBalanceByUserId(userId);
        if (accounts.isEmpty()) {
            return new ResponseEntity<>("No accounts found for this user", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccountByType(
            @RequestParam Long userId,
            @RequestParam String accountType) {
        accountServiceImpl.deleteAccountByAccountType(userId, accountType);
        return new ResponseEntity<>("Account type deleted successfully", HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    public ResponseEntity<String> respondWithError(Exception e) {
        logger.error("Exception Occurred. Details : {}", e.getMessage());
        return new ResponseEntity<>("Exception Occurred. More info :" + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class AccountNotFoundException extends RuntimeException {
        public AccountNotFoundException(String message) {
            super(message);
        }
    }



}
