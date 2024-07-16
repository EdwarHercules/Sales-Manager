package com.juice.top.Controllers;

import com.juice.top.Entity.CompraInsumos;
import com.juice.top.Objects.ComprasInsumosDTO;
import com.juice.top.Services.ComprasInsumosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compras")
public class ComprasInsumoController {

    private static final Logger logger = LoggerFactory.getLogger(ComprasInsumoController.class);

    @Autowired
    private ComprasInsumosService comprasInsumosService;

    @GetMapping
    public ResponseEntity<List<ComprasInsumosDTO>> getallcompras(){
        List<ComprasInsumosDTO> comprasInsumosDTOS = comprasInsumosService.getAllCompras();
        return new ResponseEntity<>(comprasInsumosDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComprasInsumosDTO> getOneCompra(@PathVariable Long id){
        Optional<ComprasInsumosDTO> comprasInsumosDTO = comprasInsumosService.getCompraPerID(id);
        return comprasInsumosDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK)).
                orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComprasInsumosDTO> updateCompra(@PathVariable long id, @RequestBody ComprasInsumosDTO comprasInsumosDTO){
        try {
            ComprasInsumosDTO updateComprasInsumosDTO = comprasInsumosService.updateCompra(id,comprasInsumosDTO);
            return new ResponseEntity<>(updateComprasInsumosDTO, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ComprasInsumosDTO> createNewCompra(@RequestBody ComprasInsumosDTO comprasInsumosDTO){
        ComprasInsumosDTO newCompra = comprasInsumosService.createNewCompra(comprasInsumosDTO);
        return new ResponseEntity<>(newCompra,HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<ComprasInsumosDTO> deleteCompra(@PathVariable Long id){
        try {
            comprasInsumosService.deleteCompra(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
