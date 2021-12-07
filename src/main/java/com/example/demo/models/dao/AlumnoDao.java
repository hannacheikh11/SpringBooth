package com.example.demo.models.dao;
import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.example.demo.models.entity.Alumno;

public interface AlumnoDao extends CrudRepository<Alumno,Long> {

	List<Alumno> findAll();

	


}
