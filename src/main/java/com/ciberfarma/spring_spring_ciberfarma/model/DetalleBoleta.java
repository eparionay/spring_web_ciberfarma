package com.ciberfarma.spring_spring_ciberfarma.model;

import com.ciberfarma.spring_spring_ciberfarma.dto.DetalleBoletaId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_det_boleta")
@Getter
@Setter
@IdClass(DetalleBoletaId.class)
public class DetalleBoleta {
	
	@Id
	@ManyToOne
	@JoinColumn(name = "num_boleta")
	private Boleta boleta;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "id_producto")
	private Producto producto;
	
	@Column(name = "cantidad")
	private Integer cantidad;
	
	@Column(name = "precio_venta")
	private Double precioVenta;
}
