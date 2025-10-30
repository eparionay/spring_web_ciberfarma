package com.ciberfarma.spring_spring_ciberfarma.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoSeleccionado {
	private Integer idProducto;
	private String descripcion;
	private double precio;
	private int cantidad;

	public Double getSubtotal() {
		return precio * cantidad;
	}
}
