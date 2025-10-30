package com.ciberfarma.spring_spring_ciberfarma.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ciberfarma.spring_spring_ciberfarma.dto.ProductoSeleccionado;
import com.ciberfarma.spring_spring_ciberfarma.model.Boleta;
import com.ciberfarma.spring_spring_ciberfarma.model.DetalleBoleta;
import com.ciberfarma.spring_spring_ciberfarma.model.Producto;
import com.ciberfarma.spring_spring_ciberfarma.model.Usuario;
import com.ciberfarma.spring_spring_ciberfarma.service.BoletaService;
import com.ciberfarma.spring_spring_ciberfarma.service.ProductoService;
import com.ciberfarma.spring_spring_ciberfarma.util.Alert;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("boleta")
@SessionAttributes("lstSeleccionado")
public class BoletaController {
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private BoletaService boletaService;
	
	@ModelAttribute("lstSeleccionado")
	public List<ProductoSeleccionado> inicializarSeleccionados() {
		return new ArrayList<>();
	}

	@GetMapping("listado")
	public String listado(Model model) {
		return "boleta/listado";
	}
	
	@GetMapping("filtrar")
	public String filtrar(Model model) {
		return "boleta/listado";
	}
	
	@GetMapping("nuevo")
	public String nuevo(Model model) {
		model.addAttribute("productoSeleccionado", new ProductoSeleccionado());
		model.addAttribute("productos", productoService.getAll());
		return "boleta/nuevo";
	}
	
	@PostMapping("agregar")
	public String agregar(
			@ModelAttribute ProductoSeleccionado seleccionado,
			@ModelAttribute("lstSeleccionado") List<ProductoSeleccionado> lstSeleccionado,
			Model model
	) {
		model.addAttribute("productos", productoService.getAll());
		//validamos si el producto ya está seleccionado
		boolean existeProducto = lstSeleccionado.stream().anyMatch(item -> item.getIdProducto() == seleccionado.getIdProducto());
		
		if(existeProducto) {
			model.addAttribute("alert", Alert.sweetAlertInfo("El producto ya fue seleccionado"));
			return "boleta/nuevo";
		}
		
		lstSeleccionado.add(seleccionado);
		model.addAttribute("productoSeleccionado", new ProductoSeleccionado());
		return "boleta/nuevo";
	}
	
	@PostMapping("quitar")
	public String quitar(
			@RequestParam Integer idProducto,
			@ModelAttribute("lstSeleccionado") List<ProductoSeleccionado> lstSeleccionado,
			Model model
	) {
		
		lstSeleccionado.removeIf(item -> item.getIdProducto() == idProducto);
		
		model.addAttribute("productos", productoService.getAll());
		model.addAttribute("productoSeleccionado", new ProductoSeleccionado());
		return "boleta/nuevo";
	}
	
	@PostMapping("registrar")
	public String registrar(
			@ModelAttribute("lstSeleccionado") List<ProductoSeleccionado> lstSeleccionado,
			Model model,
			HttpSession session
	) {
		Integer idUsuario = (Integer) session.getAttribute("idUsuario");
		
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(idUsuario);
		
		//Convertimos los productos seleccionados a Detalles de boleta
		List<DetalleBoleta> lstDetalleBoleta = 
				lstSeleccionado.stream().map(item -> {
					DetalleBoleta detalle = new DetalleBoleta();
					detalle.setCantidad(item.getCantidad());
					detalle.setPrecioVenta(item.getPrecio());
					
					Producto producto = new Producto();
					producto.setIdProducto(item.getIdProducto());
					detalle.setProducto(producto);
					
					return detalle;
				}).toList();
		
		//Instanciamos la boleta
		Boleta boleta = new Boleta();
		boleta.setUsuario(usuario);
		boleta.setLstDetalleBoleta(lstDetalleBoleta);
		
		boletaService.create(boleta);
		return "redirect:/boleta/listado";
	}
	
	
	
	
	
	
	
	
}
