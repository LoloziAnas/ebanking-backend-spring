package com.morobyte.ebankingbackend.dto;

public class BankDto {
    private final Long id;
    private final String name;
    private final String desc;

    public BankDto(Long id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

}
