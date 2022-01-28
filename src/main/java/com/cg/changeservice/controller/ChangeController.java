package com.cg.changeservice.controller;

import com.cg.changeservice.domain.Bill;
import com.cg.changeservice.domain.CoinChangeVO;
import com.cg.changeservice.exceptionhandler.BillConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.cg.changeservice.service.ChangeServiceImpl;

import java.util.List;
import java.util.Optional;

/*
Done • Available bills are (1, 2, 5, 10, 20, 50, 100)

Done • Available coins are (0.01, 0.05, 0.10, 0.25)

Done • Start with 100 coins of each type
Done • Change should be made by utilizing the least amount of coins

Done • API should validate bad input and respond accordingly

Done • Service should respond with an appropriate message if it does not have enough coins to make change

Done • The service should maintain the state of the coins throughout the transactions

• Deliver the code with test cases

• Upload your code to Github and come to interview prepared to walk through code



Bonus:
Done • Allow for number of coins to be configurable and easily changed

Done • Allow for the user to request for the most amount of coins to make change
 */

@RestController
@RequestMapping("/bills")
@Slf4j
public class ChangeController {

    @Autowired
    private ChangeServiceImpl changeService;

    @GetMapping(value = {"/coinChange/{bill}/{isRequestingMostAmountOfCoins}", "/coinChange/{bill}"})
    public ResponseEntity<?> getCoinChange(@PathVariable(value = "bill") Bill bill, @PathVariable(required = false, value = "isRequestingMostAmountOfCoins") Optional<Boolean> isRequestingMostAmountOfCoins) throws Throwable {
        List<CoinChangeVO> coinChangeVOS = null;
        coinChangeVOS = changeService.makeChange(bill, isRequestingMostAmountOfCoins);
        log.info("requesting change for "+bill);
        return new ResponseEntity<List<CoinChangeVO>>(coinChangeVOS, HttpStatus.OK);
    }

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(Bill.class, new BillConverter());
    }

}
