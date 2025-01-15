package com.tekarch.AccountService.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long account_id;

    @Column(unique = true)
    private String accountNumber;

    @Column(unique = true)
    private Long userId;

    private String accountType;

    private Double balance;

    private String currency = "USD";

    private LocalDateTime createdAt = LocalDateTime.now();

}
