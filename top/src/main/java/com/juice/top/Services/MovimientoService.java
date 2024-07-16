package com.juice.top.Services;


import com.juice.top.Objects.MovimientoDTO;
import com.juice.top.Entity.Movimientos;
import com.juice.top.Repositories.MovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoService {

    @Autowired
    private MovimientosRepository movimientoRepository;

    public List<MovimientoDTO> getAllMovimientos() {
        List<Movimientos> movimientos = movimientoRepository.findAll();
        return movimientos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void crearMovimiento(MovimientoDTO movimientoDTO) {
        Movimientos movimiento = convertToEntity(movimientoDTO);
        movimientoRepository.save(movimiento);
    }

    // Método para convertir de Entidad a DTO
    private MovimientoDTO convertToDTO(Movimientos movimiento) {
        return new MovimientoDTO(
                movimiento.getFecha(),
                movimiento.getSaldoAntes(),
                movimiento.getTipoMovimiento(),
                movimiento.getValor(),
                movimiento.getDescripcion(),
                movimiento.getSaldoDespues()
        );
    }


    public Page<MovimientoDTO> getAllMovimientosbyDate(int page, int size, LocalDate fecha) {
        Pageable pageable = PageRequest.of(page, size);

        if (fecha != null) {
            LocalDateTime startOfDay = fecha.atStartOfDay();
            LocalDateTime endOfDay = fecha.atTime(LocalTime.MAX);
            return movimientoRepository.findByFechaBetween(startOfDay, endOfDay, pageable)
                    .map(this::convertToDTO);
        } else {
            return movimientoRepository.findAll(pageable)
                    .map(this::convertToDTO);
        }
    }
    // Método para convertir de DTO a Entidad
    private Movimientos convertToEntity(MovimientoDTO movimientoDTO) {
        Movimientos movimiento = new Movimientos();
        movimiento.setFecha(movimientoDTO.getFecha());
        movimiento.setSaldoAntes(movimientoDTO.getSaldoAntes());
        movimiento.setTipoMovimiento(movimientoDTO.getTipoMovimiento());
        movimiento.setValor(movimientoDTO.getValor());
        movimiento.setDescripcion(movimientoDTO.getDescripcion());
        movimiento.setSaldoDespues(movimientoDTO.getSaldoDespues());
        return movimiento;
    }
}

