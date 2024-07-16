package com.juice.top.Repositories;

import com.juice.top.Entity.Movimientos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface MovimientosRepository extends JpaRepository<Movimientos, Long> {

    // Método para obtener movimientos filtrados por fecha
    Page<Movimientos> findByFechaBetween(LocalDateTime startOfDay, LocalDateTime endOfDay, Pageable pageable);
}
