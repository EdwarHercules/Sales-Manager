package com.juice.top.Services;

import com.juice.top.Entity.Produccion;
import com.juice.top.Entity.TipoJugos;
import com.juice.top.Objects.ProduccionDTO;
import com.juice.top.Objects.TipoJugosDTO;
import com.juice.top.Repositories.ProduccionRepository;
import com.juice.top.Repositories.TipoJugosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProduccionService {
    private static final Logger logger = LoggerFactory.getLogger(ProduccionService.class);

    @Autowired
    private ProduccionRepository produccionRepository;

    @Autowired
    private TipoJugosRepository tipoJugosRepository;

    // Method to create a new production
    public ProduccionDTO createNewProduccion(ProduccionDTO produccionDTO){
        TipoJugos tipoJugos = tipoJugosRepository.findById(produccionDTO.getJugoId().getId())
                .orElseThrow(() -> new RuntimeException("Tipo de jugo no encontrado"));
        // Incrementar el stock
        tipoJugos.setStock(tipoJugos.getStock() + produccionDTO.getCantidad());
        tipoJugosRepository.save(tipoJugos);

        Produccion produccion = new Produccion();
        produccion.setCantidad(produccionDTO.getCantidad());
        produccion.setFecha(produccionDTO.getFecha());
        produccion.setJugoId(tipoJugos);

        Produccion savedProduccion = produccionRepository.save(produccion);
        return convertToDTO(savedProduccion);
    }

    // Method to get all
    public List<ProduccionDTO> getAllProduction(){
        List<Produccion> produccions = produccionRepository.findAll();
        return produccions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    // Method to get one
    public Optional<ProduccionDTO>  getProductionPerId(Long id){
        Optional<Produccion> produccion = produccionRepository.findById(id);
        return produccion.map(this::convertToDTO);
    }

    // Method to update
    public ProduccionDTO updateProduction(Long id, ProduccionDTO produccionDTO){
        if(produccionRepository.existsById(id)) {
            Produccion produccion = produccionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Prduccion no encontrada"));
            TipoJugos tipoJugos = tipoJugosRepository.findById(produccionDTO.getJugoId().getId())
                    .orElseThrow(() -> new RuntimeException("Jugo no encontrado"));

            // Restaurar el stock
            tipoJugos.setStock(tipoJugos.getStock() - produccion.getCantidad());
            // Aplicar el nuevo cambio en el stock
            tipoJugos.setStock(tipoJugos.getStock() + produccionDTO.getCantidad());
            tipoJugosRepository.save(tipoJugos);

            produccion.setCantidad(produccionDTO.getCantidad());
            produccion.setFecha(produccionDTO.getFecha());
            produccion.setJugoId(tipoJugos);

            Produccion updateProduccion = produccionRepository.save(produccion);

            return convertToDTO(updateProduccion);
        } else {
            throw new RuntimeException("Produccion no encontrada");
        }
    }
    // Method to delete
    public void deleteProduccion(Long id){
        Produccion produccion = produccionRepository.findById(id)
                        .orElseThrow(()-> new RuntimeException("Produccion no encontrada"));
        TipoJugos tipoJugos = produccion.getJugoId();

        tipoJugos.setStock(tipoJugos.getStock() - produccion.getCantidad());
        tipoJugosRepository.save(tipoJugos);

        produccionRepository.deleteById(id);
    }


    private ProduccionDTO convertToDTO(Produccion produccion){
        return new ProduccionDTO(
                produccion.getId(),
                produccion.getFecha(),
                new TipoJugosDTO(produccion.getJugoId().getId(), produccion.getJugoId().getName(), produccion.getJugoId().getJuice(), produccion.getJugoId().getStock()),
                produccion.getCantidad()
        );
    }
}
