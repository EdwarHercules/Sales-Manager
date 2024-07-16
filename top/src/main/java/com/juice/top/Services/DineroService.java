package com.juice.top.Services;

import com.juice.top.Entity.Dinero;
import com.juice.top.Repositories.DineroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class DineroService {

    @Autowired
    private DineroRepository dineroRepository;

    @Transactional
    public BigDecimal getSaldoActual() {
        Dinero dinero = dineroRepository.findById(1L).orElse(new Dinero());
        return dinero.getSaldo();
    }

    @Transactional
    public void aumentarSaldo(BigDecimal monto) {
        Dinero dinero = dineroRepository.findById(1L).orElse(new Dinero());
        dinero.setSaldo(dinero.getSaldo().add(monto));
        dineroRepository.save(dinero);
    }

    @Transactional
    public void inicializarSaldo(BigDecimal saldoInicial) {
        Dinero dinero = new Dinero();
        dinero.setSaldo(saldoInicial);
        dineroRepository.save(dinero);
    }
    @Transactional
    public void reducirSaldo(BigDecimal monto) {
        Dinero dinero = dineroRepository.findById(1L).orElse(new Dinero());
        dinero.setSaldo(dinero.getSaldo().subtract(monto));
        dineroRepository.save(dinero);
    }
}
