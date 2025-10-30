package com.ciberfarma.spring_spring_ciberfarma.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity()
@Table(name = "tbl_cab_boleta")
@Getter @Setter
@DynamicInsert
public class Boleta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "num_boleta")
	private Integer numBoleta;

	@Column(name = "fecha_registro")
	private LocalDateTime fechaRegistro;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "boleta", cascade = CascadeType.ALL)
	private List<DetalleBoleta> lstDetalleBoleta;
}
