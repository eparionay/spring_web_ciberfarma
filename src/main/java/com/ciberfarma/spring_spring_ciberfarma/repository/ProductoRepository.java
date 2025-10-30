package com.ciberfarma.spring_spring_ciberfarma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ciberfarma.spring_spring_ciberfarma.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	List<Producto> findAllByOrderByIdProductoDesc();

	@Query("""
			select p from Producto p
			where
				(:idCategoria is null or p.categoria.idCategoria = :idCategoria)
				and
				(:idProveedor is null or p.proveedor.idProveedor = :idProveedor)

			""")
	List<Producto> findAllWithFilters(
			@Param("idCategoria") Integer idCategoria,
			@Param("idProveedor") Integer idProveedor
	);
}
