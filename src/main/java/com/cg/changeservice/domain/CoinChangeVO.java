package com.cg.changeservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinChangeVO {
    Coin coin;
    Integer quantity;
}
