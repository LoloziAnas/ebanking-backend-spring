package com.morobyte.ebankingbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("SA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SavingAccount extends BankAccount {
    private double interestRate;
}
