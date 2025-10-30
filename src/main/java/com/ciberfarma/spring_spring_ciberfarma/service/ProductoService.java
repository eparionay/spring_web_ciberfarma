package com.ciberfarma.spring_spring_ciberfarma.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ciberfarma.spring_spring_ciberfarma.dto.ProductoFilter;
import com.ciberfarma.spring_spring_ciberfarma.dto.ResultadoResponse;
import com.ciberfarma.spring_spring_ciberfarma.model.Producto;
import com.ciberfarma.spring_spring_ciberfarma.repository.ProductoRepository;

@Service
public class ProductoService {
	
	@Autowired
	private ProductoRepository productoRepository;
	
	public List<Producto> getAll() {
		return productoRepository.findAllByOrderByIdProductoDesc();
	}
	
	public List<Producto> search(ProductoFilter filter) {
		return productoRepository.findAllWithFilters(filter.getIdCategoria(), filter.getIdProveedor());
	}
	
	public ResultadoResponse create(Producto producto) {
		try {
			Producto productoRegistrado = productoRepository.save(producto);
			
			String mensaje = String.format("Producto registrado con Id %s", productoRegistrado.getIdProducto());
			return new ResultadoResponse(true, mensaje);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResultadoResponse(false, "Error en ProductoService: "+ e.getMessage());
		}
	}
	
	public Producto getOne(Integer id) {
		return productoRepository.findById(id).orElseThrow();
	}
	
	public ResultadoResponse update(Producto producto) {
		try {
			Producto productoRegistrado = productoRepository.save(producto);
			
			String mensaje = String.format("Producto actualizado con Id %s", productoRegistrado.getIdProducto());
			return new ResultadoResponse(true, mensaje);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResultadoResponse(false, "Error en ProductoService: "+ e.getMessage());
		}
	}
	
}
