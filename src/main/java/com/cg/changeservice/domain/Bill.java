package com.cg.changeservice.domain;

//• Available bills are (1, 2, 5, 10, 20, 50, 100)

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum Bill {
    ONE(1), TWO(2), FIVE(5), TEN(10), TWENTY(20), FIFTY(50), HUNDRED(100);
    private double value;

    public static Bill fromValue(double value) {
        for (Bill bill : values()) {
            if (Double.compare(bill.value, value) == 0) {
                return bill;
            }
        }
        throw new IllegalArgumentException(
                "Unknown Bill " + value + ". Acceptable bills are (1, 2, 5, 10, 20, 50, 100). Please input a correct bill" );
    }
}
