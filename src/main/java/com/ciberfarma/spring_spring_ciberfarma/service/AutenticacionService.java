package com.ciberfarma.spring_spring_ciberfarma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ciberfarma.spring_spring_ciberfarma.dto.AutenticacionFilter;
import com.ciberfarma.spring_spring_ciberfarma.model.Usuario;
import com.ciberfarma.spring_spring_ciberfarma.repository.UsuarioRepository;

@Service
public class AutenticacionService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario autenticar(AutenticacionFilter filter) {
		return usuarioRepository.findByCuentaAndClave(filter.getCuenta(), filter.getClave());
	}

}
