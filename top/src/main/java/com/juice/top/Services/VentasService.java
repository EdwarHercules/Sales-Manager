package com.juice.top.Services;

import com.juice.top.Entity.Movimientos;
import com.juice.top.Entity.TipoJugos;
import com.juice.top.Entity.Ventas;
import com.juice.top.Objects.MovimientoDTO;
import com.juice.top.Objects.TipoJugosDTO;
import com.juice.top.Objects.VentasDTO;
import com.juice.top.Repositories.TipoJugosRepository;
import com.juice.top.Repositories.VentasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VentasService {

    private static final Logger logger = LoggerFactory.getLogger(VentasService.class);

    @Autowired
    private VentasRepository ventasRepository;

    @Autowired
    private TipoJugosRepository tipoJugosRepository;

    @Autowired
    private DineroService dineroService;

    @Autowired
    private MovimientoService movimientoService;


    // Method to create a new sale
    public VentasDTO createANewSale(VentasDTO ventasDTO){
        logger.info("Creando una nueva venta");
        // Verifica que tipoJugos no sea nulo
        if (ventasDTO.getTipoJugos() == null || ventasDTO.getTipoJugos().getId() == null) {
            throw new RuntimeException("El tipo de jugo no puede ser nulo");
        }

        TipoJugos tipoJugos = tipoJugosRepository.findById(ventasDTO.getTipoJugos().getId()).orElseThrow(()->new RuntimeException("Tipo de jugo no encontrado"));
        // Reducir el stock
        tipoJugos.setStock(tipoJugos.getStock() - ventasDTO.getCantidad());
        tipoJugosRepository.save(tipoJugos);

        // Obtener el saldo antes de la operación
        BigDecimal saldoAntes = dineroService.getSaldoActual();

        // Actualizar el saldo
        BigDecimal montoVenta = ventasDTO.getPrecio().multiply(BigDecimal.valueOf(ventasDTO.getCantidad()));
        dineroService.aumentarSaldo(montoVenta);

        // Registrar el movimiento de venta
        Movimientos movimientoVenta = new Movimientos();
        movimientoVenta.setFecha(LocalDateTime.now());
        movimientoVenta.setSaldoAntes(saldoAntes);
        movimientoVenta.setTipoMovimiento("VENTA");
        movimientoVenta.setValor(montoVenta);
        movimientoVenta.setDescripcion("Venta de jugo: " + tipoJugos.getName());
        movimientoVenta.setSaldoDespues(dineroService.getSaldoActual());

        // Convertir Movimiento a MovimientoDTO
        MovimientoDTO movimientoDTO = convertToDTO(movimientoVenta);

        movimientoService.crearMovimiento(movimientoDTO);

        Ventas venta = new Ventas();
        venta.setFecha(ventasDTO.getFecha());
        venta.setJugo(tipoJugos);
        venta.setCantidad(ventasDTO.getCantidad());
        venta.setPrecio(ventasDTO.getPrecio());

        Ventas newSale = ventasRepository.save(venta);
        return convertToDTO(newSale);
    }

    // Method to get all sales
    public Page<VentasDTO> getAllSales(Pageable pageable){
        logger.info("Obteniendo todas las ventas");
        Page<Ventas> ventas = ventasRepository.findAll(pageable);
        return ventas.map(this::convertToDTO);
    }
    // Method to get a sales for ID
    public Optional<VentasDTO> getSalePerId(Long id){
        logger.info("Obteniendo una venta");
        Optional<Ventas> ventas = ventasRepository.findById(id);
        return ventas.map(this::convertToDTO);
    }

    // Method to update a sale
    public VentasDTO updateSale(Long id, VentasDTO ventasDTO){
        if(ventasRepository.existsById(id)){
            logger.info("Actualizando una venta");
            Ventas ventas = ventasRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("Venta no encontrada con ID: " + id));
            TipoJugos tipoJugos = tipoJugosRepository.findById(ventasDTO.getTipoJugos().getId())
                    .orElseThrow(()->new RuntimeException("Tipo de jugo no encontrado"));

            // restaurar el stock original
            tipoJugos.setStock(tipoJugos.getStock() + ventas.getCantidad());
            // Aplicar el nuevo cambio en el stock
            tipoJugos.setStock(tipoJugos.getStock() - ventasDTO.getCantidad());
            tipoJugosRepository.save(tipoJugos);

            // Calcular la diferencia de precios si hubo un cambio
            BigDecimal montoVentaAnterior = ventas.getPrecio().multiply(BigDecimal.valueOf(ventas.getCantidad()));
            BigDecimal montoVentaNuevo = ventasDTO.getPrecio().multiply(BigDecimal.valueOf(ventasDTO.getCantidad()));
            BigDecimal diferenciaMonto = montoVentaNuevo.subtract(montoVentaAnterior);

            // Obtener el saldo antes de la operación
            BigDecimal saldoAntes = dineroService.getSaldoActual();

            // Actualizar el saldo
            if (!diferenciaMonto.equals(BigDecimal.ZERO)) {
                if (diferenciaMonto.compareTo(BigDecimal.ZERO) < 0) {
                    dineroService.aumentarSaldo(diferenciaMonto.abs()); // Reducir el saldo
                } else {
                    dineroService.reducirSaldo(diferenciaMonto); // Aumentar el saldo
                }
            }

            ventas.setPrecio(ventasDTO.getPrecio());
            ventas.setCantidad(ventasDTO.getCantidad());
            ventas.setFecha(ventasDTO.getFecha());
            ventas.setJugo(tipoJugos);

            Ventas updateVenta = ventasRepository.save(ventas);


            // Crear el objeto Movimiento
            Movimientos movimientoVenta = new Movimientos();
            movimientoVenta.setFecha(LocalDateTime.now());
            movimientoVenta.setSaldoAntes(saldoAntes);
            movimientoVenta.setTipoMovimiento("ACTUALIZACIÓN_VENTA");
            movimientoVenta.setValor(diferenciaMonto);
            movimientoVenta.setDescripcion("Actualización de venta de jugo: " + tipoJugos.getName() + " con ID: " + tipoJugos.getId());
            movimientoVenta.setSaldoDespues(dineroService.getSaldoActual());

            // Convertir Movimiento a MovimientoDTO
            MovimientoDTO movimientoVentaDTO = convertToDTO(movimientoVenta);

            // Guardar el movimiento
            movimientoService.crearMovimiento(movimientoVentaDTO);
            return convertToDTO(updateVenta);
        } else {
            throw new RuntimeException("Venta no encontrada con ID: " + id);
        }
    }

    // Method to delete a sale
    public void deleteSale(Long id){
        logger.info("Eliminando una venta");
        Ventas ventas = ventasRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Venta no encontrada"));

        TipoJugos tipoJugos = ventas.getJugo();
        // Restaurar el stock orignal
        tipoJugos.setStock(tipoJugos.getStock() + ventas.getCantidad());
        tipoJugosRepository.save(tipoJugos);

        ventasRepository.deleteById(id);
    }


    private VentasDTO convertToDTO(Ventas ventas){
        return new VentasDTO(
                ventas.getId(),
                ventas.getFecha(),
                new TipoJugosDTO(ventas.getJugo().getId(), ventas.getJugo().getName(), ventas.getJugo().getJuice(), ventas.getJugo().getStock()),
                ventas.getCantidad(),
                ventas.getPrecio()
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
