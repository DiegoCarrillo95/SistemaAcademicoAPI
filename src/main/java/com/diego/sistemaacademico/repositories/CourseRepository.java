package com.diego.sistemaacademico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diego.sistemaacademico.models.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>{

}
