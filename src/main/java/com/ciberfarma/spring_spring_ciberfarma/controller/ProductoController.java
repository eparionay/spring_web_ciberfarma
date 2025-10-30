package com.ciberfarma.spring_spring_ciberfarma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ciberfarma.spring_spring_ciberfarma.dto.ProductoFilter;
import com.ciberfarma.spring_spring_ciberfarma.model.Producto;
import com.ciberfarma.spring_spring_ciberfarma.service.CategoriaService;
import com.ciberfarma.spring_spring_ciberfarma.service.ProductoService;
import com.ciberfarma.spring_spring_ciberfarma.service.ProveedorService;
import com.ciberfarma.spring_spring_ciberfarma.util.Alert;

@Controller
@RequestMapping("producto")
public class ProductoController {

	@Autowired
	private ProductoService productoService;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private ProveedorService proveedorService;

	@GetMapping("listado")
	public String listado(Model model) {
		model.addAttribute("lstProducto", productoService.getAll());
		model.addAttribute("lstCategoria", categoriaService.getAll());
		model.addAttribute("lstProveedor", proveedorService.getAll());
		model.addAttribute("filter", new ProductoFilter());
		return "producto/listado";
	}

	@GetMapping("filtrar")
	public String filtrar(@ModelAttribute ProductoFilter filter, Model model) {
		model.addAttribute("lstProducto", productoService.search(filter));
		model.addAttribute("lstCategoria", categoriaService.getAll());
		model.addAttribute("lstProveedor", proveedorService.getAll());
		model.addAttribute("filter", filter);
		return "producto/listado";
	}

	@GetMapping("nuevo")
	public String nuevo(Model model) {
		model.addAttribute("lstCategoria", categoriaService.getAll());
		model.addAttribute("lstProveedor", proveedorService.getAll());
		model.addAttribute("producto", new Producto());
		return "producto/nuevo";
	}

	@PostMapping("registrar")
	public String registrar(@ModelAttribute Producto producto, Model model, RedirectAttributes flash) {

		var response = productoService.create(producto);

		if (!response.success) {
			model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
			model.addAttribute("lstCategoria", categoriaService.getAll());
			model.addAttribute("lstProveedor", proveedorService.getAll());
			return "producto/nuevo";
		}

		flash.addFlashAttribute("toast", Alert.sweetToast(response.mensaje, "success", 5000));
		return "redirect:/producto/listado";
	}

	@GetMapping("edicion/{id}")
	public String edicion(@PathVariable Integer id, Model model) {
		model.addAttribute("lstCategoria", categoriaService.getAll());
		model.addAttribute("lstProveedor", proveedorService.getAll());
		model.addAttribute("producto", productoService.getOne(id));
		return "producto/edicion";
	}

	@PostMapping("guardar")
	public String guardar(@ModelAttribute Producto producto, Model model, RedirectAttributes flash) {

		var response = productoService.update(producto);

		if (!response.success) {
			model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
			model.addAttribute("lstCategoria", categoriaService.getAll());
			model.addAttribute("lstProveedor", proveedorService.getAll());
			return "producto/nuevo";
		}

		flash.addFlashAttribute("toast", Alert.sweetToast(response.mensaje, "success", 5000));
		return "redirect:/producto/listado";
	}

}
