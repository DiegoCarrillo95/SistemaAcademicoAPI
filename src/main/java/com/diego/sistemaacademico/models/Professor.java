package com.diego.sistemaacademico.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Entity
public class Professor {
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
}
