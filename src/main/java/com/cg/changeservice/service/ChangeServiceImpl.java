package com.cg.changeservice.service;

import com.cg.changeservice.domain.*;
import com.cg.changeservice.exceptionhandler.NotEnoughCoinsException;
import com.cg.changeservice.repository.CoinRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ChangeServiceImpl implements ChangeService {

    @Autowired
    private CoinRepository coinRepository;

    @Value("${noOfCoinsAtStartup}")
    private int noOfCoinsAtStartup;

    @PostConstruct
    public void init() {
        log.info("noOfCoinsAtStartup : "+noOfCoinsAtStartup);
        loadRepository();
    }

    private void loadRepository() {
         List<CoinBank> totalChange =  Stream.of(Coin.values())
                        .map(c->new CoinBank(c,noOfCoinsAtStartup))
                        .collect( Collectors.toList());
         coinRepository.saveAll(totalChange);
    }


    @Transactional(rollbackFor = NotEnoughCoinsException.class)
    public List<CoinChangeVO> makeChange(Bill bill, Optional<Boolean> mostAmountOfCoins) throws NotEnoughCoinsException{
        printCoinBankBalance();

        List<CoinChangeVO> allCoinChangeVO = new ArrayList<>();
        List<CoinBank> remainingCoins = new ArrayList<>();

        Double remainingAmount =  bill.getValue();
        CoinChangeVO coinChangeVO = null;
        CoinBank cb = null;


        for (Coin coin : getSortedListOfCoins(mostAmountOfCoins)) {
            int numberOfCoinsRequired = (int) (remainingAmount / coin.getValue());
            cb = coinRepository.findCoinBankByCoin(coin);
            if (cb.getQuantity() > 0) {
                coinChangeVO = new CoinChangeVO(coin, 0);
                if (numberOfCoinsRequired > cb.getQuantity()) {
                    coinChangeVO.setQuantity(cb.getQuantity());
                    cb.setQuantity(0);
                } else {
                    coinChangeVO.setQuantity(numberOfCoinsRequired);
                    cb.setQuantity(cb.getQuantity()-numberOfCoinsRequired);
                }
                remainingAmount = remainingAmount - (coinChangeVO.getQuantity() * coin.getValue());
                allCoinChangeVO.add(coinChangeVO);
                remainingCoins.add(cb);
                if (Double.compare(remainingAmount, 0.00) == 0) {
                    break;
                }
            } else {
                continue;
            }
        }
        if (remainingAmount > 0 ) {
             throw new NotEnoughCoinsException("Not enough coins available to provide change for the requested bill.");
        } else if (allCoinChangeVO.size() > 0) {
            coinRepository.saveAll(remainingCoins);
        }
        return allCoinChangeVO;
    }

    private void printCoinBankBalance(){
        List<CoinBank> remainingCoins = coinRepository.findAll();
        remainingCoins.stream().forEach(coinBank->log.info(coinBank.getCoin().name()+coinBank.getQuantity()));

    }

    private Coin[] getSortedListOfCoins(Optional<Boolean> mostAmountOfCoins) {
        Coin[] coins = Coin.values();
        if(mostAmountOfCoins.isPresent() && mostAmountOfCoins.get()){
            log.debug(String.valueOf(mostAmountOfCoins.get()));
            Arrays.sort(coins, Comparator.comparing(Coin::getValue));
        }
            return coins;
    }
}
