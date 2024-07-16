package com.juice.top.Controllers;

import com.juice.top.Entity.TipoJugos;
import com.juice.top.Objects.NamesJugosDTO;
import com.juice.top.Objects.TipoJugosDTO;
import com.juice.top.Services.TipoJugoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipo-jugos")
public class TipoJugosController {

    @Autowired
    private TipoJugoService tipoJugoService;

    // Método para obtener todos los tipos de jugos
    @GetMapping
    public ResponseEntity<List<TipoJugosDTO>> getAllJuice() {
        List<TipoJugosDTO> tipoJugosList = tipoJugoService.getAllJuice();
        return new ResponseEntity<>(tipoJugosList, HttpStatus.OK);
    }
    // Método para obtener un tipo de jugo por ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoJugosDTO> getJuicePerId(@PathVariable Long id) {
        Optional<TipoJugosDTO> tipoJugosDTO = tipoJugoService.getJuicePerId(id);
        if (tipoJugosDTO.isPresent()) {
            return new ResponseEntity<>(tipoJugosDTO.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Método para crear un nuevo tipo de jugo
    @PostMapping
    public ResponseEntity<TipoJugosDTO> createANewJuice(@RequestBody TipoJugosDTO tipoJugosDTO) {
        TipoJugos newJuice = tipoJugoService.createANewJuice(tipoJugosDTO);
        return new ResponseEntity<>(convertToDTO(newJuice), HttpStatus.CREATED);
    }

    // Método para actualizar un tipo de jugo
    @PutMapping("/{id}")
    public ResponseEntity<TipoJugosDTO> updateJuice(@PathVariable Long id, @RequestBody TipoJugosDTO tipoJugosDTO) {
        try {
            TipoJugosDTO updatedJuice = tipoJugoService.updateJuice(id, tipoJugosDTO);
            return new ResponseEntity<>(updatedJuice, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Método para eliminar un tipo de jugo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJuice(@PathVariable Long id) {
        try {
            tipoJugoService.deleteJuice(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/nombres")
    public ResponseEntity<?> getNombresJugos() {
        try {
            List<NamesJugosDTO> jugos = tipoJugoService.getNombresJugos();
            return ResponseEntity.ok(jugos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener nombres de jugos: " + e.getMessage());
        }
    }


    private TipoJugosDTO convertToDTO(TipoJugos tipoJugos) {
        return new TipoJugosDTO(
                tipoJugos.getId(),
                tipoJugos.getName(),
                tipoJugos.getJuice(),
                tipoJugos.getStock()
        );
    }
}
