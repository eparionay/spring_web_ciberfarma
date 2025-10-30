package com.ciberfarma.spring_spring_ciberfarma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ciberfarma.spring_spring_ciberfarma.dto.ResultadoResponse;
import com.ciberfarma.spring_spring_ciberfarma.model.Boleta;
import com.ciberfarma.spring_spring_ciberfarma.repository.BoletaRepository;

import jakarta.transaction.Transactional;

@Service
public class BoletaService {
	
	@Autowired
	private BoletaRepository boletaRepository;
	
	public ResultadoResponse create(Boleta boleta) {
		try {
			Boleta registrado = boletaRepository.save(boleta);
			
			String mensaje = String.format("Boleta registrada con número %s", registrado.getNumBoleta());
			return new ResultadoResponse(true, mensaje);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResultadoResponse(false, "Error en BoletaService: "+ e.getMessage());
		}
	}
}
