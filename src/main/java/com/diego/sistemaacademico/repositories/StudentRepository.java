package com.diego.sistemaacademico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diego.sistemaacademico.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{

}
