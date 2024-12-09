package com.felipeAlves.ecommerce_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.felipeAlves.ecommerce_api.exception.EstoqueMinimoException;
import com.felipeAlves.ecommerce_api.model.Produto;
import com.felipeAlves.ecommerce_api.model.Usuario;
import com.felipeAlves.ecommerce_api.repository.ProdutoRepository;
import com.felipeAlves.ecommerce_api.specification.ProdutoSpecifications;

@Service
public class ProdutoService {
	
    private static final int QUANTIDADE_MINIMA_ESTOQUE = 1;


    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto salvarProduto(Produto produto, Usuario usuarioLogado) {
        if (produto == null) {
            throw new IllegalArgumentException("Dados do produto são obrigatórios.");
        }
        
        validarEstoqueMinimo(produto.getQuantidadeEmEstoque());


        produto.setUsuarioCriacao(usuarioLogado.getIdUsuario());
        produto.setUsuarioAtualizacao(usuarioLogado.getIdUsuario());

        try {
            return produtoRepository.save(produto);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Erro ao salvar produto. Verifique os dados. \n"+e.getMessage());
        }
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto atualizarProduto(Long id, Produto produtoAtualizado, Usuario usuarioLogado) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        validarEstoqueMinimo(produtoAtualizado.getQuantidadeEmEstoque());


        produtoExistente.setNomeProduto(produtoAtualizado.getNomeProduto());
        produtoExistente.setDescricao(produtoAtualizado.getDescricao());
        produtoExistente.setPreco(produtoAtualizado.getPreco());
        produtoExistente.setQuantidadeEmEstoque(produtoAtualizado.getQuantidadeEmEstoque());
        produtoExistente.setUsuarioAtualizacao(usuarioLogado.getIdUsuario());

        return produtoRepository.save(produtoExistente);
    }
    
    public List<Produto> buscarProdutosComFiltros(String nome, Double precoMinimo, Boolean estoqueDisponivel) {
        Specification<Produto> spec = Specification.where(null);

        if (nome != null) {
            spec = spec.and(ProdutoSpecifications.nomeContem(nome));
        }
        if (precoMinimo != null) {
            spec = spec.and(ProdutoSpecifications.precoMaiorQue(precoMinimo));
        }
        if (Boolean.TRUE.equals(estoqueDisponivel)) {
            spec = spec.and(ProdutoSpecifications.estoqueDisponivel());
        }

        return produtoRepository.findAll(spec);
    }

    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }
    
    private void validarEstoqueMinimo(int quantidade) {
        if (quantidade < QUANTIDADE_MINIMA_ESTOQUE) {
            throw new EstoqueMinimoException("A quantidade em estoque não pode ser menor que " + QUANTIDADE_MINIMA_ESTOQUE);
        }
    }
    
}
