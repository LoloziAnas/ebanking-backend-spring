package com.morobyte.ebankingbackend.dto;

public class BankDto {
    private final Long id;
    private final String name;

    public BankDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
