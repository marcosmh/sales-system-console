package com.mark.microsystem.sales.system.main.service;

import com.mark.microsystem.sales.system.main.model.entity.CashClosure;
import com.mark.microsystem.sales.system.main.model.entity.UserPerson;
import com.mark.microsystem.sales.system.main.repository.CashClosureRepository;
import com.mark.microsystem.sales.system.main.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CashClosureService implements ICashClosureService {

    private final SaleRepository saleRepository;
    private final CashClosureRepository cashClosureRepository;

    @Override
    public CashClosure generateClosure(UserPerson user) {
        LocalDateTime toDay = LocalDateTime.now();

        if(cashClosureRepository.findByUsuarioAndFecha(user,toDay).isPresent() ) {
            throw new IllegalStateException("The end-of-day reconciliation has already been generated " + toDay + ".");
        }

        LocalDateTime start = toDay.toLocalDate().atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        BigDecimal dayTotal = saleRepository.sumTotalByUserAndDateBetween(user, start, end);

        if (dayTotal == null) {
            dayTotal = BigDecimal.ZERO;
        }

        CashClosure cashClosure = CashClosure.builder()
                .user(user)
                .date(toDay)
                .dailyTotal(dayTotal)
                .build();

        return cashClosureRepository.save(cashClosure);
    }



}
