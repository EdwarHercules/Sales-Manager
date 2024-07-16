package com.juice.top.Controllers;

import com.juice.top.Objects.MovimientoDTO;
import com.juice.top.Services.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> getAllMovimientos() {
        List<MovimientoDTO> movimientos = movimientoService.getAllMovimientos();
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/dates")
    public ResponseEntity<Page<MovimientoDTO>> getAllMovimientosbyDate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        Page<MovimientoDTO> movimientos = movimientoService.getAllMovimientosbyDate(page, size, fecha);
        return ResponseEntity.ok(movimientos);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearMovimiento(@RequestBody MovimientoDTO movimientoDTO) {
        movimientoService.crearMovimiento(movimientoDTO);
        return ResponseEntity.ok().build();
    }
}