package br.com.fatec.catalogo.controllers;

import br.com.fatec.catalogo.models.ProdutoModel;
import br.com.fatec.catalogo.services.ProdutoService;
import br.com.fatec.catalogo.repositories.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import br.com.fatec.catalogo.repositories.CategoriaRepository;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long categoria,
            Model model) {

        if (categoria != null) {

            model.addAttribute("produtos",
                    service.buscarPorCategoria(categoria));

        } else {

            model.addAttribute("produtos",
                    service.buscarPorNome(nome));
        }

        model.addAttribute("categorias",
                categoriaRepository.findAll());

        return "lista-produtos";
    }

    @GetMapping("/novo")
    public String exibirFormulario(Model model) {

        model.addAttribute("produto", new ProdutoModel());

        model.addAttribute("categorias",
                categoriaRepository.findAll());

        return "cadastro-produto";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("produto") ProdutoModel produto,
                         BindingResult result) {

        if (result.hasErrors()) {
            return "cadastro-produto";
        }

        try {
            service.salvar(produto);
        } catch (IllegalStateException e) {
            result.rejectValue("nome", "erro.nome", e.getMessage());
            return "cadastro-produto";
        }

        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable long id, Model model) {

        model.addAttribute("produto", service.buscarPorId(id));

        model.addAttribute("categorias",
                categoriaRepository.findAll());

        return "editar-produto";
    }
    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable long id,
                            @Valid @ModelAttribute("produto") ProdutoModel produto,
                            BindingResult result,
                            Model model) {

        if (result.hasErrors()) {

            model.addAttribute("categorias",
                    categoriaRepository.findAll());

            return "editar-produto";
        }

        produto.setIdProduto(id);

        service.salvar(produto);

        return "redirect:/produtos";
    }
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable long id) {
        service.excluir(id);
        return "redirect:/produtos";
    }
}