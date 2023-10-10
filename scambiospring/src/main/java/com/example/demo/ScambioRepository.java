package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ScambioRepository extends JpaRepository<Scambio, Long> {

}

//@Query("SELECT s FROM Scambio s WHERE s.piazza = ?1")
//Optional<Scambio> findScambioByPiazza(String piazza);
