package com.diego.sistemaacademico;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.diego.sistemaacademico.controllers.StudentController;
import com.diego.sistemaacademico.models.Student;
import com.diego.sistemaacademico.repositories.StudentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SistemaAcademicoApplicationTests {

	@Autowired
	StudentController studentController;
	
	@Autowired
	StudentRepository studentRepository;
	
	@After
	public void clearDatabase() {
		studentRepository.deleteAll();
	}
	
	@Test
	public void shouldReturnCorrectStudentFromStudentsDatabaseWhenNewStudentInserted() {
		//Arrange
		Student student = new Student();
		student.setName("Diego Carrillo");
			
		//Act
		Student insertedStudent = studentController.insertStudent(student);
		
		//Assert
		assertEquals(student.getName(),insertedStudent.getName());
		assertEquals(student.getName(),studentController.getStudentById(insertedStudent.getId()).getName());
	}
	
	@Test
	public void shouldReturnCorrectStudentFromStudentsDatabaseWhenStudentUpdated() {
		//Arrange
		Student student = new Student();
		student.setName("Diego Carrillo");
		
		Student insertedStudent = studentController.insertStudent(student);
		student.setName("Rubens Carrillo");
		
		//Act
		insertedStudent = studentController.updateStudent(insertedStudent.getId(), student);
		
		//Assert
		assertEquals(student.getName(),insertedStudent.getName());
		assertEquals(student.getName(),studentController.getStudentById(insertedStudent.getId()).getName());
	}

}
