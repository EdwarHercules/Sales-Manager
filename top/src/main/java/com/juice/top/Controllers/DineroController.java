package com.juice.top.Controllers;

import com.juice.top.Objects.DineroDTO;
import com.juice.top.Services.DineroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/dinero")
public class DineroController {
    @Autowired
    private DineroService dineroService;

    @GetMapping("/saldo")
    public ResponseEntity<DineroDTO> getSaldo() {
        BigDecimal saldoActual = dineroService.getSaldoActual();
        DineroDTO dineroDTO = new DineroDTO(saldoActual);
        return ResponseEntity.ok(dineroDTO);
    }

    @PostMapping("/saldo/inicializar")
    public ResponseEntity<?> inicializarSaldo(@RequestParam BigDecimal saldoInicial) {
        dineroService.inicializarSaldo(saldoInicial);
        return ResponseEntity.ok().build();
    }
}
