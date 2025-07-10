package com.banquito.core.examen.repository;

import com.banquito.core.examen.model.TransaccionTurno;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionTurnoRepository extends MongoRepository<TransaccionTurno, String> {
    List<TransaccionTurno> findByCodigoTurno(String codigoTurno);
}