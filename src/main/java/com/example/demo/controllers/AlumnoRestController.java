package com.example.demo.controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.entity.Alumno;
import com.example.demo.models.services.AlumnoService;
import com.google.common.io.ByteStreams;
@RestController
@RequestMapping("/api")
public class AlumnoRestController {
	@Autowired
	private AlumnoService alumnoService;
	
	
	@GetMapping("/alumnos")
	public List<Alumno> index(){
		return alumnoService.findAll();
	}
	
	/*@GetMapping("/alumnos/{id}")
	public Cliente show(@PathVariable Long id) {
		return clienteService.findById(id);
	}*/
	@GetMapping("/alumnos/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		Alumno alumno = null;
		Map<String,Object> response = new HashMap<>();
		
		try {
			alumno = alumnoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar consulta en base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(alumno == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat("no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Alumno>(alumno,HttpStatus.OK);
	}
	
	
	/*@PostMapping("/alumnos")
	@ResponseStatus(HttpStatus.CREATED)
	public Alumno create(@RequestBody Alumno alumno) {
		return clienteService.save(alumno);
	}*/
	
	@PostMapping("/alumnos")
	public ResponseEntity<?> create(@RequestBody Alumno alumno){
		Alumno alumnoNew = null;
		Map<String,Object> response = new HashMap<>();
		
		try {
			alumnoNew = alumnoService.save(alumno);
		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar insert en base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El cliente ha sido creado con éxito!");
		response.put("cliente", alumnoNew);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	/*@PutMapping("/alumnos/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Alumno update(@RequestBody Cliente cliente, @PathVariable Long id) {
		Alumno alumnoUpdate = alumnoService.findById(id);
		
		clienteUpdate.setApellido(cliente.getApellido());
		clienteUpdate.setNombre(cliente.getNombre());
		clienteUpdate.setEmail(cliente.getEmail());
		
		return clienteService.save(clienteUpdate);
	}*/

	@PutMapping("/alumnos/{id}")
	public ResponseEntity<?> update(@RequestBody Alumno alumno, @PathVariable Long id) {
		Alumno alumnoActual= alumnoService.findById(id);
		
		Alumno alumnoUpdated = null;
		Map<String,Object> response = new HashMap<>();
		
		if(alumnoActual == null){
			response.put("mensaje", "Error: no se pudo editar, el cliente ID: ".concat(id.toString().concat("no existe el id en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			alumnoActual.setApellido(alumno.getApellido());
			alumnoActual.setNombre(alumno.getNombre());
			alumnoActual.setEmail(alumno.getEmail());
			alumnoActual.setTelefono(alumno.getTelefono());
			alumnoActual.setCreatedAt(alumno.getCreatedAt());
			
			
			alumnoUpdated = alumnoService.save(alumnoActual);
		} catch (DataAccessException e) {
			response.put("mensaje","Error al actualizar el alumno en base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El cliente ha sido actualizado con éxito!");
		response.put("cliente", alumnoUpdated);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	/*@DeleteMapping("clientes/{id}")
	public void delete(@PathVariable Long id) {
		clienteService.delete(id);
	}*/
	@DeleteMapping("alumno/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String,Object> response = new HashMap<>();
		
		try {
			
			Alumno cliente = alumnoService.findById(id);
			String nombreFotoAnterior = cliente.getImagen();
			
			if(nombreFotoAnterior != null && nombreFotoAnterior.length() > 0){
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				
				if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()){
					
					archivoFotoAnterior.delete();
				}
			}
			
			alumnoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el alumno en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","El alumno ha sido eliminado con éxito!");

		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("alumnos/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id){
		Map<String,Object> response = new HashMap<>();
		
		Alumno alumno = alumnoService.findById(id);
		
		if(!archivo.isEmpty()) {
			String nombreArchivo = UUID.randomUUID().toString()+"_"+ archivo.getOriginalFilename().replace(" ", "");
			Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
			
			try {
				Files.copy(archivo.getInputStream(),rutaArchivo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				response.put("mensaje", "Error al subir la imagen del cliente");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			String nombreFotoAnterior = alumno.getImagen();
			
			if(nombreFotoAnterior != null && nombreFotoAnterior.length() > 0){
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				
				if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()){
					
					archivoFotoAnterior.delete();
				}
			}
			
			alumno.setImagen(nombreArchivo);
			
			alumnoService.save(alumno);
			
			response.put("cliente",alumno);
			response.put("mensaje","Has subido correctamente la imagen: "+ nombreArchivo);
			
		}
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
		Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
		
		Resource recurso = null;
		
		try {
			recurso = new UrlResource(rutaArchivo.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		if(!recurso.exists() && !recurso.isReadable()){
			throw new RuntimeException("Error no se puede cargar la imagen "+nombreFoto);
		}
		
		HttpHeaders cabecera = new HttpHeaders();
		//cabecera.add(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""+recurso.getFilename()+"\"");
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION,"file=\""+recurso.getFilename()+"\"");
	
		
		return new ResponseEntity<Resource>(recurso,cabecera,HttpStatus.OK);
	}
	
	
	

	
}


