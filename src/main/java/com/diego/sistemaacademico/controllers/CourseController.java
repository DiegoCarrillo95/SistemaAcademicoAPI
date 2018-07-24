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
import com.diego.sistemaacademico.repositories.CourseRepository;

@RestController
@RequestMapping("/api")
public class CourseController {

	@Autowired
	CourseRepository courseRepository;

	@GetMapping("/courses")
	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}

	@PostMapping("/courses")
	public Course insertCourse(@Valid @RequestBody Course course) {
		return courseRepository.save(course);
	}

	@PutMapping("/courses/{id}")
	public Course updateCourse(@PathVariable(value = "id") int id, @Valid @RequestBody Course courseDetails) {

		Course course = courseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));

		if (courseDetails.getName() != null) {
			course.setName(courseDetails.getName());
		}
		
		if (courseDetails.getProfessor() != null) {
			course.setProfessor(courseDetails.getProfessor());
		}

		course.getStudents().addAll(courseDetails.getStudents());

		return courseRepository.save(course);
	}

	@GetMapping("/courses/{id}")
	public Course getCourseById(@PathVariable(value = "id") int id) {

		Course course = courseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));

		return course;
	}

	@DeleteMapping("/courses/{id}")
	public ResponseEntity<?> deleteCourse(@PathVariable(value = "id") int id) {

		Course course = courseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));

		courseRepository.delete(course);

		return ResponseEntity.ok().build();

	}

}
