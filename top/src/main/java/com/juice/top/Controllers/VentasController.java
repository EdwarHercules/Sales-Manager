package com.juice.top.Controllers;

import com.juice.top.Objects.VentasDTO;
import com.juice.top.Services.VentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")
public class VentasController {

    @Autowired
    private VentasService ventasService;

    // Método para crear una nueva venta
    @PostMapping
    public ResponseEntity<VentasDTO> createNewSale(@RequestBody VentasDTO ventasDTO) {
        VentasDTO newSale = ventasService.createANewSale(ventasDTO);
        return new ResponseEntity<>(newSale, HttpStatus.CREATED);
    }

    // Método para obtener todas las ventas
    @GetMapping
    public ResponseEntity<Page<VentasDTO>> getAllSales(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        Page<VentasDTO> ventas = ventasService.getAllSales(PageRequest.of(page, size));
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    // Método para obtener una venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<VentasDTO> getSalePerId(@PathVariable Long id) {
        Optional<VentasDTO> ventasDTO = ventasService.getSalePerId(id);
        return ventasDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Método para actualizar una venta
    @PutMapping("/{id}")
    public ResponseEntity<VentasDTO> updateSale(@PathVariable Long id, @RequestBody VentasDTO ventasDTO) {
        try {
            VentasDTO updatedSale = ventasService.updateSale(id, ventasDTO);
            return new ResponseEntity<>(updatedSale, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Método para eliminar una venta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        try {
            ventasService.deleteSale(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
