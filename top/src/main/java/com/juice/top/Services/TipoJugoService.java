package com.juice.top.Services;

import com.juice.top.Entity.TipoJugos;
import com.juice.top.Objects.NamesJugosDTO;
import com.juice.top.Objects.TipoJugosDTO;
import com.juice.top.Repositories.TipoJugosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TipoJugoService {

    private static final Logger logger = LoggerFactory.getLogger(TipoJugoService.class);

    @Autowired
    private TipoJugosRepository tipoJugosRepository;

    // Method to create
    public TipoJugos createANewJuice(TipoJugosDTO tipoJugosDTO){
        TipoJugos tipoJugos = new TipoJugos();
        tipoJugos.setName(tipoJugosDTO.getName());
        tipoJugos.setJuice(tipoJugosDTO.getJuice());
        tipoJugos.setStock(tipoJugosDTO.getStock());
        return tipoJugosRepository.save(tipoJugos);
    }

    // Method to get all
    public List<TipoJugosDTO> getAllJuice(){
        List<TipoJugos> tipoJugos = tipoJugosRepository.findAll();
        return tipoJugos.stream().map(this::converToDTO).collect(Collectors.toList());
    }

    // Method to get by id
    public Optional<TipoJugosDTO> getJuicePerId(Long id){
        Optional<TipoJugos> tipoJugos = tipoJugosRepository.findById(id);

        return tipoJugos.map(this::converToDTO);
    }
    // Method to update
    public TipoJugosDTO updateJuice(Long id, TipoJugosDTO tipoJugosDTO){
        if(tipoJugosRepository.existsById(id)){
            TipoJugos tipoJugos = tipoJugosRepository.findById(id).orElseThrow(()->new RuntimeException("Jugo no encontrado"));
            tipoJugos.setName(tipoJugosDTO.getName());
            tipoJugos.setJuice(tipoJugosDTO.getJuice());
            tipoJugos.setStock(tipoJugosDTO.getStock());

            TipoJugos updateTipoJugos = tipoJugosRepository.save(tipoJugos);

            return converToDTO(updateTipoJugos);
        } else {
            throw new RuntimeException("Jugo no encontrado");
        }
    }

    // Method to delete
    public void deleteJuice(Long id){
        tipoJugosRepository.deleteById(id);
    }


    public List<NamesJugosDTO> getNombresJugos() {
        List<TipoJugos> jugos = tipoJugosRepository.findAll();
        return jugos.stream().map(j -> new NamesJugosDTO(j.getId(), j.getName())).collect(Collectors.toList());
    }


    private TipoJugosDTO converToDTO(TipoJugos tipoJugos){
        return new TipoJugosDTO(tipoJugos.getId(),
                tipoJugos.getName(),
                tipoJugos.getJuice(),
                tipoJugos.getStock());
    }

}
