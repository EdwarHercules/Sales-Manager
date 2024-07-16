package com.juice.top.Objects;

import java.math.BigDecimal;

public class DineroDTO {
    private BigDecimal saldo;

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public DineroDTO() {
    }

    public DineroDTO(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
