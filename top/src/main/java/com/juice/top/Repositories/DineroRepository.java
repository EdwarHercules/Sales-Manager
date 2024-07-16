package com.juice.top.Repositories;

import com.juice.top.Entity.Dinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DineroRepository extends JpaRepository<Dinero, Long> {
}
