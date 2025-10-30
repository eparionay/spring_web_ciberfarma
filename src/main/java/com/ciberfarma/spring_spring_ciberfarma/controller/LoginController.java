package com.ciberfarma.spring_spring_ciberfarma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ciberfarma.spring_spring_ciberfarma.dto.AutenticacionFilter;
import com.ciberfarma.spring_spring_ciberfarma.model.Usuario;
import com.ciberfarma.spring_spring_ciberfarma.service.AutenticacionService;
import com.ciberfarma.spring_spring_ciberfarma.util.Alert;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
	private AutenticacionService autenticacionService;

	@GetMapping({ "/", "login" })
	public String login(Model model) {
		model.addAttribute("filter", new AutenticacionFilter());
		return "login";
	}

	@PostMapping("iniciar-sesion")
	public String iniciarSesion(@ModelAttribute AutenticacionFilter filter, Model model, RedirectAttributes flash,
			HttpSession session) {
		
		Usuario usuario = autenticacionService.autenticar(filter);
		
		if (usuario == null) {
			model.addAttribute("alert", Alert.sweetAlertError("Credenciales incorrectas"));
			model.addAttribute("filter", new AutenticacionFilter());
			return "login";
		}
		
		if (!usuario.getActivo()) {
			model.addAttribute("alert", Alert.sweetAlertInfo("Usuario inactivo"));
			model.addAttribute("filter", new AutenticacionFilter());
			return "login";
		}
		
		session.setAttribute("idUsuario", usuario.getIdUsuario());
		session.setAttribute("nombreCompleto", usuario.getNombres()+ " "+ usuario.getApellidos());
		
		flash.addFlashAttribute("alert", Alert.sweetImageUrl(
				"Bienvenido a Ciberfarma", 
				usuario.getNombres()+ " "+ usuario.getApellidos(), 
				"/assets/img/mapache_pedro.gif"));
		
		return "redirect:/dashboard";
	}

	@GetMapping("dashboard")
	public String home() {
		return "dashboard";
	}
	
	@GetMapping("cerrar-sesion")
	public String cerrarSesion(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
