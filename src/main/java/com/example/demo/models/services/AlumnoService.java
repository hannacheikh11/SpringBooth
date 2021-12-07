package com.example.demo.models.services;

import java.util.List;

import com.example.demo.models.entity.Alumno;

public interface AlumnoService {
public List<Alumno> findAll();
	
	public Alumno findById(Long id);
	
	public Alumno save(Alumno alumno);
	
	public void delete(Long id);
	

}
