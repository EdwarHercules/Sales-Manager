package com.juice.top.Controllers;

import com.juice.top.Objects.InsumosDTO;
import com.juice.top.Objects.NamesJugosDTO;
import com.juice.top.Objects.NombresInsumosDTO;
import com.juice.top.Services.InsumosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/insumos")
public class InsumoController {

    private static final Logger logger = LoggerFactory.getLogger(InsumoController.class);

    @Autowired
    private InsumosService insumosService;

    @GetMapping
    public ResponseEntity<List<InsumosDTO>> getAllInsumos(){
        List<InsumosDTO> insumosDTOS = insumosService.getAllinsumos();
        return new ResponseEntity<>(insumosDTOS, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<InsumosDTO> getInsumoperId(@PathVariable Long id){
        Optional<InsumosDTO> insumosDTO =  insumosService.getInsumoPerId(id);
        return insumosDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK)).
                orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<InsumosDTO> createNewInsumo(@RequestBody InsumosDTO insumosDTO){
        InsumosDTO insumos = insumosService.createNewInsumo(insumosDTO);
        return new ResponseEntity<>(insumos, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsumosDTO> updateInsumo(@PathVariable Long id, @RequestBody InsumosDTO insumosDTO ){
        try {
            InsumosDTO updateInsumos = insumosService.updateInsumo(id,insumosDTO);
            return new ResponseEntity<>(updateInsumos, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<InsumosDTO> deleteInsumo(@PathVariable Long id){
        try {
            insumosService.deleteInsumo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/nombres")
    public ResponseEntity<?> getNombresInsumos() {
        try {
            List<NombresInsumosDTO> insumos = insumosService.getNombresInsumos();
            return ResponseEntity.ok(insumos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener nombres de los insumos: " + e.getMessage());
        }
    }
}
