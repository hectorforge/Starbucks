package com.starbuks.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.starbuks.app.entitys.bean.CarritoItem;
import com.starbuks.app.persistence.CarritoItemRepository;
import com.starbuks.app.persistence.CarritoRepository;

@Controller
@RequestMapping("/api/web-carrito")
public class CarritoWebController {

	 @Autowired
	    private CarritoRepository carritoRepository;

	    @Autowired
	    private CarritoItemRepository carritoItemRepository;

	    @GetMapping("/{usuarioId}")
	    public String verCarrito(@PathVariable Long usuarioId, Model model) {
	        var carritoOpt = carritoRepository.findByUsuarioId(usuarioId);
	        if (carritoOpt.isEmpty()) {
	            model.addAttribute("carritoItems", List.of());
	        } else {
	            List<CarritoItem> items = carritoItemRepository.findByCarritoId(carritoOpt.get().getId());
	            model.addAttribute("carritoItems", items);
	        }
	        model.addAttribute("usuarioId", usuarioId);
	        return "carrito";
	    }
}
