package org.duffy.ticketing.domain.account.service;

import org.duffy.ticketing.domain.account.dto.CreateBuyerAccountRequest;
import org.duffy.ticketing.domain.account.dto.CreateSellerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class AccountServiceTests {

    @Autowired
    AccountService accountService;

    @Test
    @Rollback(value = false)
    public void createSellerAccountTest() {
        CreateSellerRequest request = CreateSellerRequest.builder()
                .email("duffy@icloud.com")
                .name("Junho")
                .password("test1234!")
                .build();

        accountService.createSellerAccount(request);
    }

    @Test
    @Rollback(value = false)
    public void createBuyerAccountTest() {
        CreateBuyerAccountRequest request = CreateBuyerAccountRequest.builder()
                .email("seller@icloud.com")
                .name("seller")
                .password("test1234!")
                .build();
        accountService.createBuyerAccount(request);
    }
}