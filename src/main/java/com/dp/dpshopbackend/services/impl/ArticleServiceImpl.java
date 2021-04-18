package com.dp.dpshopbackend.services.impl;

import com.dp.dpshopbackend.dto.ArticleDto;
import com.dp.dpshopbackend.exceptions.ResourceNotFoundException;
import com.dp.dpshopbackend.models.Article;
import com.dp.dpshopbackend.repository.ProduitRepository;
import com.dp.dpshopbackend.services.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private final ProduitRepository produitRepository;

    public ArticleServiceImpl(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @Override
    public ArticleDto save(ArticleDto articleDto) {

        return ArticleDto.fromEntityToDto(
                produitRepository.save(
                        ArticleDto.fromDtoToEntity(articleDto)
                )
        );
    }

    @Override
    public ArticleDto findById(Long id) {
        if (id == null) {
            log.error("Produit Id is null");
            return null;
        }

        Optional<Article> produit = produitRepository.findById(id);

        return Optional.of(ArticleDto.fromEntityToDto(produit.get())).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Aucnun produit avec l'Id = " + id + "n'a été trouvé")
        );
    }

    @Override
    public ArticleDto findByReference(String reference) {
        if (!StringUtils.hasLength(reference)) {
            log.error("Produit REFERENCE is null");
        }

        Optional<Article> produit = produitRepository.findProduitByReference(reference);

        return Optional.of(ArticleDto.fromEntityToDto(produit.get())).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Aucnun produit avec l'Id = " + reference + "n'a été trouvé")
        );


    }

    @Override
    public List<ArticleDto> findAll() {
        return produitRepository.findAll().stream()
                .map(ArticleDto::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            log.error("Produit Id is null");
            return;
        }
        produitRepository.deleteById(id);

    }
}