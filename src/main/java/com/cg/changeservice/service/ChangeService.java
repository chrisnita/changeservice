package com.cg.changeservice.service;

import com.cg.changeservice.domain.Bill;
import com.cg.changeservice.domain.CoinChangeVO;

import java.util.List;
import java.util.Optional;

public interface ChangeService {
    public List<CoinChangeVO> makeChange(Bill bill, Optional<Boolean> isRequestingMostAmountOfCoins) throws Exception;
}
