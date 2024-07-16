package com.juice.top.Services;

import com.juice.top.Entity.Insumos;
import com.juice.top.Entity.TipoJugos;
import com.juice.top.Objects.InsumosDTO;
import com.juice.top.Objects.NamesJugosDTO;
import com.juice.top.Objects.NombresInsumosDTO;
import com.juice.top.Repositories.InsumosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InsumosService {

    private static final Logger logger = LoggerFactory.getLogger(InsumosService.class);

    @Autowired
    private InsumosRepository insumosRepository;

    // Method to get all
    public List<InsumosDTO> getAllinsumos(){
        List<Insumos> insumos = insumosRepository.findAll();
        return insumos.stream().map(this::converToDTO).collect(Collectors.toList());
    }
    // Method to get by id
    public Optional<InsumosDTO> getInsumoPerId(Long id){
        Optional<Insumos> insumosOptional = insumosRepository.findById(id);
        return insumosOptional.map(this::converToDTO);
    }
    // Method to create a new insumo
    public InsumosDTO createNewInsumo(InsumosDTO insumosDTO){
        Insumos insumos = new Insumos();
        insumos.setName(insumosDTO.getName());
        insumos.setDescription(insumosDTO.getDescription());
        insumos.setUnidadMedida(insumosDTO.getUnidadMedida());

        Insumos savedInsumos = insumosRepository.save(insumos);

        return converToDTO(savedInsumos);
    }
    // Method to update an insumo
    public InsumosDTO updateInsumo(Long id, InsumosDTO insumosDTO){
        if(insumosRepository.existsById(id)){
            Insumos insumos = insumosRepository.findById(id).
                    orElseThrow(()-> new RuntimeException("Insumo no encontrado"));

            insumos.setName(insumosDTO.getName());
            insumos.setDescription(insumosDTO.getDescription());
            insumos.setUnidadMedida(insumosDTO.getUnidadMedida());

            Insumos updateInsumo = insumosRepository.save(insumos);
            return converToDTO(updateInsumo);
        } else {
            throw new RuntimeException("Insumo no encontrado");
        }
    }

    // Method to delete an insumo
    public void deleteInsumo(Long id){
        insumosRepository.deleteById(id);
    }
    // Method to convert to dto
    private InsumosDTO converToDTO(Insumos insumos){
        return new InsumosDTO(
                insumos.getId(),
                insumos.getName(),
                insumos.getDescription(),
                insumos.getUnidadMedida()
        );
    }

    public List<NombresInsumosDTO> getNombresInsumos() {
        List<Insumos> insumos = insumosRepository.findAll();
        return insumos.stream().map(j -> new NombresInsumosDTO(j.getId(), j.getName())).collect(Collectors.toList());
    }

}
