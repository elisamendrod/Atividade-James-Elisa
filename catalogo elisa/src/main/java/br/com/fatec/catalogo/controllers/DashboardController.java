package br.com.fatec.catalogo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import br.com.fatec.catalogo.repositories.ProdutoRepository;
import br.com.fatec.catalogo.repositories.CategoriaRepository;
import org.springframework.ui.Model;


@Controller
public class DashboardController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("totalProdutos",
                produtoRepository.count());

        model.addAttribute("totalCategorias",
                categoriaRepository.count());

        return "dashboard";
    }
}