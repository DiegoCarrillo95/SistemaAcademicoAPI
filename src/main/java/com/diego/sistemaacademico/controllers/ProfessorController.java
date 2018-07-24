package com.diego.sistemaacademico.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego.sistemaacademico.exceptions.ResourceNotFoundException;
import com.diego.sistemaacademico.models.Course;
import com.diego.sistemaacademico.models.Professor;
import com.diego.sistemaacademico.repositories.CourseRepository;
import com.diego.sistemaacademico.repositories.ProfessorRepository;

@RestController
@RequestMapping("/api")
public class ProfessorController {

	@Autowired
	ProfessorRepository professorRepository;

	@Autowired
	CourseRepository courseRepository;

	@GetMapping("/professors")
	public List<Professor> getAllProfessors() {
		return professorRepository.findAll();
	}

	@PostMapping("/professors")
	public Professor insertProfessor(@Valid @RequestBody Professor professor) {
		return professorRepository.save(professor);
	}

	@PutMapping("/professors/{id}")
	public Professor updateProfessor(@PathVariable(value = "id") int id,
			@Valid @RequestBody Professor professorDetails) {

		Professor professor = professorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Professor", "id", id));

		professor.setName(professorDetails.getName());

		return professorRepository.save(professor);
	}

	@GetMapping("/professors/{id}")
	public Professor getProfessorById(@PathVariable(value = "id") int id) {

		Professor professor = professorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Professor", "id", id));

		return professor;
	}

	@DeleteMapping("/professors/{id}")
	public ResponseEntity<?> deleteProfessor(@PathVariable(value = "id") int id) {

		Professor professor = professorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Professor", "id", id));

		List<Course> listCourses = courseRepository.findAll();

		for (Course course : listCourses) {
			if (course.getProfessor() == professor) {
				course.setProfessor(null);
				courseRepository.saveAndFlush(course);
			}
		}

		professorRepository.delete(professor);

		return ResponseEntity.ok().build();

	}

}
