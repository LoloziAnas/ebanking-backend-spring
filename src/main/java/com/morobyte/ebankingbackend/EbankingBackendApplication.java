package com.morobyte.ebankingbackend;

import com.morobyte.ebankingbackend.entity.AccountOperation;
import com.morobyte.ebankingbackend.entity.CurrentAccount;
import com.morobyte.ebankingbackend.entity.Customer;
import com.morobyte.ebankingbackend.entity.SavingAccount;
import com.morobyte.ebankingbackend.enums.AccountStatus;
import com.morobyte.ebankingbackend.enums.OperationType;
import com.morobyte.ebankingbackend.repository.AccountOperationRepository;
import com.morobyte.ebankingbackend.repository.BankAccountRepository;
import com.morobyte.ebankingbackend.repository.CustomerRepository;
import com.morobyte.ebankingbackend.sec.entity.AppRole;
import com.morobyte.ebankingbackend.sec.entity.AppUser;
import com.morobyte.ebankingbackend.sec.service.IAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }


    @Bean
    CommandLineRunner start(BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository,
                            CustomerRepository customerRepository) {
        return args -> {
            Stream.of("Jack", "Ronaldo", "Messi").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(customer -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setBalance(Math.random() * 1500);
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setCustomer(customer);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setBalance(Math.random() * 1500);
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setCustomer(customer);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);

            });
            bankAccountRepository.findAll().forEach(bankAccount -> {
                for (int i = 0; i < 5; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setBankAccount(bankAccount);
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random() * 1500);
                    accountOperation.setOperationType(Math.random() > 0.5 ? OperationType.CREDIT : OperationType.DEBIT);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }

    @Bean
    CommandLineRunner st(IAccountService accountService) {
        return args -> {
            accountService.addRole(new AppRole(null, "USER"));
            accountService.addRole(new AppRole(null, "ADMIN"));
            accountService.addRole(new AppRole(null, "MANAGER"));

            accountService.addUser(new AppUser(null, "user1", "123", new ArrayList<>()));
            accountService.addUser(new AppUser(null, "admin", "123", new ArrayList<>()));
            accountService.addUser(new AppUser(null, "user2", "123", new ArrayList<>()));
            accountService.addUser(new AppUser(null, "user3", "123", new ArrayList<>()));

            accountService.addRoleToUser("user1", "USER");
            accountService.addRoleToUser("admin", "ADMIN");
            accountService.addRoleToUser("user2", "USER");
            accountService.addRoleToUser("user2", "MANAGER");
            accountService.addRoleToUser("user3", "MANAGER");
            accountService.addRoleToUser("user3", "ADMIN");

        };
    }
}
