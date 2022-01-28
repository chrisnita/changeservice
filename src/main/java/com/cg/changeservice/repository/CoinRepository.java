package com.cg.changeservice.repository;

import com.cg.changeservice.domain.Coin;
import com.cg.changeservice.domain.CoinBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CoinRepository extends JpaRepository<CoinBank, String> {

    @Transactional(readOnly = true)
    CoinBank findCoinBankByCoin(Coin coid );

    CoinBank save(CoinBank coinBank);
//void saveAll(List<CoinBank> totalChange);
}
