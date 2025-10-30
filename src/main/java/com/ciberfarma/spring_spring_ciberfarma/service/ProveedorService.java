package com.ciberfarma.spring_spring_ciberfarma.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ciberfarma.spring_spring_ciberfarma.model.Proveedor;
import com.ciberfarma.spring_spring_ciberfarma.repository.ProveedorRepository;

@Service
public class ProveedorService {

	@Autowired
	private ProveedorRepository proveedorRepository;
	
	public List<Proveedor> getAll() {
		return proveedorRepository.findAll();
	}
}
