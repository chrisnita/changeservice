package com.cg.changeservice.domain;

//• Available coins are (0.01, 0.05, 0.10, 0.25)

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum Coin{
    QUARTER(0.25),  DIME(0.10), NICKLE(0.05), PENNY(0.01);
    private double value;
};