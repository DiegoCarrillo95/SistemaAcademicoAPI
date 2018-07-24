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
import com.diego.sistemaacademico.models.Student;
import com.diego.sistemaacademico.repositories.CourseRepository;
import com.diego.sistemaacademico.repositories.StudentRepository;

@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	CourseRepository courseRepository;

	@GetMapping("/students")
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@PostMapping("/students")
	public Student insertStudent(@Valid @RequestBody Student student) {
		return studentRepository.save(student);
	}

	@PutMapping("/students/{id}")
	public Student updateStudent(@PathVariable(value = "id") int id, @Valid @RequestBody Student studentDetails) {
		
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));

		student.setName(studentDetails.getName());

		return studentRepository.save(student);
	}

	@GetMapping("/students/{id}")
	public Student getStudentById(@PathVariable(value = "id") int id) {

		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));

		return student;
	}

	@DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable(value = "id") int id) {
		
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
		
		List<Course> listCourses = courseRepository.findAll();
		
		for(Course course : listCourses) {
			for(Student studentDelete : course.getStudents()) {
				if (studentDelete == student) {
					course.getStudents().remove(studentDelete);
					break;
				}
			}
			courseRepository.saveAndFlush(course);
		}
		
		studentRepository.delete(student);
		
		return ResponseEntity.ok().build();
		
	}
	
	
}
