package com.juice.top.Services;

import com.juice.top.Entity.CompraInsumos;
import com.juice.top.Entity.Insumos;
import com.juice.top.Entity.Movimientos;
import com.juice.top.Objects.ComprasInsumosDTO;
import com.juice.top.Objects.InsumosDTO;
import com.juice.top.Objects.MovimientoDTO;
import com.juice.top.Repositories.CompraInsumoRepository;
import com.juice.top.Repositories.InsumosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComprasInsumosService {
    private static final Logger logger = LoggerFactory.getLogger(ComprasInsumosService.class);

    @Autowired
    private CompraInsumoRepository compraInsumoRepository;

    @Autowired
    private InsumosRepository insumosRepository;

    @Autowired
    private DineroService dineroService;

    @Autowired
    private MovimientoService movimientoService;

    // Method to get all
    public List<ComprasInsumosDTO> getAllCompras(){
        List<CompraInsumos> compraInsumos = compraInsumoRepository.findAll();
        return compraInsumos.stream().map(this::convereToDTO).collect(Collectors.toList());
    }
    // Method to get one
    public Optional<ComprasInsumosDTO> getCompraPerID(Long id){
        Optional<CompraInsumos> compraInsumos = compraInsumoRepository.findById(id);
        return compraInsumos.map(this::convereToDTO);
    }
    // Method to create one
    @Transactional
    public ComprasInsumosDTO createNewCompra(ComprasInsumosDTO comprasInsumosDTO){
        Insumos insumos = insumosRepository.findById(comprasInsumosDTO.getInsumoId().getId()).
                orElseThrow(()-> new RuntimeException("Insumo no encontrado"));

        CompraInsumos compraInsumos = new CompraInsumos();
        compraInsumos.setFecha(comprasInsumosDTO.getFecha());
        compraInsumos.setInsumoId(insumos);
        compraInsumos.setCantidad(comprasInsumosDTO.getCantidad());
        compraInsumos.setPrecioUnitario(comprasInsumosDTO.getPrecioUnitario());
        BigDecimal total = comprasInsumosDTO.getPrecioUnitario().multiply(new BigDecimal(String.valueOf(comprasInsumosDTO.getCantidad())));
        compraInsumos.setTotal(total);

        CompraInsumos savedCompra = compraInsumoRepository.save(compraInsumos);
        // Calcular el total de la compra de insumos
        BigDecimal totalCompra = comprasInsumosDTO.getPrecioUnitario().multiply(new BigDecimal(String.valueOf(comprasInsumosDTO.getCantidad())));

        // Obtener el saldo antes de la operación
        BigDecimal saldoAntes = dineroService.getSaldoActual();

        // Actualizar el saldo
        dineroService.reducirSaldo(total);

        // Crear el objeto Movimiento
        Movimientos movimientoCompra = new Movimientos();
        movimientoCompra.setFecha(LocalDateTime.now());
        movimientoCompra.setSaldoAntes(saldoAntes);
        movimientoCompra.setTipoMovimiento("COMPRA");
        movimientoCompra.setValor(totalCompra);
        movimientoCompra.setDescripcion("Compra de insumo: " + insumos.getName());
        movimientoCompra.setSaldoDespues(dineroService.getSaldoActual());

        // Convertir Movimiento a MovimientoDTO
        MovimientoDTO movimientoCompraDTO = convertToDTO(movimientoCompra);
        // Guardar el movimiento
        movimientoService.crearMovimiento(movimientoCompraDTO);

        return convereToDTO(savedCompra);
    }
    // Method to update one
    @Transactional
    public ComprasInsumosDTO updateCompra(Long id,ComprasInsumosDTO comprasInsumosDTO){
        if(compraInsumoRepository.existsById(id)){
            CompraInsumos compraInsumos = compraInsumoRepository.findById(id).
                    orElseThrow(()->new RuntimeException("Compra no encontrada"));

            Insumos insumos = insumosRepository.findById(comprasInsumosDTO.getInsumoId().getId()).
                    orElseThrow(()->new RuntimeException("Insumo no encontrado"));

            // Calcular la diferencia de precios si hubo un cambio
            BigDecimal montoCompraAnterior = compraInsumos.getTotal();
            BigDecimal montoCompraNuevo = comprasInsumosDTO.getPrecioUnitario().multiply(new BigDecimal(comprasInsumosDTO.getCantidad().toString()));
            BigDecimal diferenciaMonto = montoCompraNuevo.subtract(montoCompraAnterior);

            // Obtener el saldo antes de la operación
            BigDecimal saldoAntes = dineroService.getSaldoActual();

            compraInsumos.setFecha(comprasInsumosDTO.getFecha());
            compraInsumos.setCantidad(comprasInsumosDTO.getCantidad());
            compraInsumos.setPrecioUnitario(comprasInsumosDTO.getPrecioUnitario());
            compraInsumos.setInsumoId(insumos);
            // Calcula el total
            BigDecimal total = comprasInsumosDTO.getPrecioUnitario().multiply(new BigDecimal(String.valueOf(comprasInsumosDTO.getCantidad())));
            compraInsumos.setTotal(total);
            CompraInsumos updateCompra = compraInsumoRepository.save(compraInsumos);

            // Actualizar el saldo
            if (!diferenciaMonto.equals(BigDecimal.ZERO)) {
                if (diferenciaMonto.compareTo(BigDecimal.ZERO) < 0) {
                    dineroService.aumentarSaldo(diferenciaMonto.abs()); // Aumentar el saldo
                } else {
                    dineroService.reducirSaldo(diferenciaMonto); // Reducir el saldo
                }
            }
            // Crear el objeto Movimiento
            Movimientos movimientoCompra = new Movimientos();
            movimientoCompra.setFecha(LocalDateTime.now());
            movimientoCompra.setSaldoAntes(saldoAntes);
            movimientoCompra.setTipoMovimiento("ACTUALIZACIÓN_COMPRA");
            movimientoCompra.setValor(diferenciaMonto);
            movimientoCompra.setDescripcion("Actualización de compra de insumo: " + insumos.getName() + " con ID: " +insumos.getId());
            movimientoCompra.setSaldoDespues(dineroService.getSaldoActual());

            // Convertir Movimiento a MovimientoDTO
            MovimientoDTO movimientoCompraDTO = convertToDTO(movimientoCompra);

            // Guardar el movimiento
            movimientoService.crearMovimiento(movimientoCompraDTO);

            return convereToDTO(updateCompra);
        } else {
            throw  new RuntimeException("Compra no encontrada");
        }
    }
    // Method to delete one
    public void deleteCompra(Long id){
        compraInsumoRepository.deleteById(id);
    }

    // Method to convert to DTO
    private ComprasInsumosDTO convereToDTO(CompraInsumos compraInsumos){
        return new ComprasInsumosDTO(
                compraInsumos.getId(),
                compraInsumos.getFecha(),
                new InsumosDTO(
                        compraInsumos.getInsumoId().getId(),
                        compraInsumos.getInsumoId().getName(),
                        compraInsumos.getInsumoId().getDescription(),
                        compraInsumos.getInsumoId().getUnidadMedida()
                ),
                compraInsumos.getCantidad(),
                compraInsumos.getPrecioUnitario(),
                compraInsumos.getTotal()
        );
    }

    // Método para convertir Movimiento a MovimientoDTO (debes implementarlo)
    private MovimientoDTO convertToDTO(Movimientos movimiento) {
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setFecha(movimiento.getFecha());
        movimientoDTO.setSaldoAntes(movimiento.getSaldoAntes());
        movimientoDTO.setTipoMovimiento(movimiento.getTipoMovimiento());
        movimientoDTO.setValor(movimiento.getValor());
        movimientoDTO.setDescripcion(movimiento.getDescripcion());
        movimientoDTO.setSaldoDespues(movimiento.getSaldoDespues());
        return movimientoDTO;
    }
}
