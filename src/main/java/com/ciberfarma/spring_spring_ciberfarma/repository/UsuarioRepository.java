package com.ciberfarma.spring_spring_ciberfarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ciberfarma.spring_spring_ciberfarma.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	Usuario findByCuentaAndClave(String cuenta, String clave);
}
