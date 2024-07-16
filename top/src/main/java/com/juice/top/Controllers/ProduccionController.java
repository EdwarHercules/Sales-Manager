package com.juice.top.Controllers;

import com.juice.top.Objects.ProduccionDTO;
import com.juice.top.Services.ProduccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/producciones")
public class ProduccionController {
    @Autowired
    private ProduccionService produccionService;

    // Método para crear una nueva producción
    @PostMapping
    public ResponseEntity<ProduccionDTO> createNewProduccion(@RequestBody ProduccionDTO produccionDTO) {
        ProduccionDTO newProduccion = produccionService.createNewProduccion(produccionDTO);
        return new ResponseEntity<>(newProduccion, HttpStatus.CREATED);
    }

    // Método para obtener todas las producciones
    @GetMapping
    public ResponseEntity<List<ProduccionDTO>> getAllProduction() {
        List<ProduccionDTO> producciones = produccionService.getAllProduction();
        return new ResponseEntity<>(producciones, HttpStatus.OK);
    }

    // Método para obtener una producción por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProduccionDTO> getProductionPerId(@PathVariable Long id) {
        Optional<ProduccionDTO> produccionDTO = produccionService.getProductionPerId(id);
        return produccionDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Método para actualizar una producción
    @PutMapping("/{id}")
    public ResponseEntity<ProduccionDTO> updateProduction(@PathVariable Long id, @RequestBody ProduccionDTO produccionDTO) {
        try {
            ProduccionDTO updatedProduccion = produccionService.updateProduction(id, produccionDTO);
            return new ResponseEntity<>(updatedProduccion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Método para eliminar una producción
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduccion(@PathVariable Long id) {
        try {
            produccionService.deleteProduccion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
