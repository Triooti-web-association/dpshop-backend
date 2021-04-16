package com.dp.dpshopbackend.repository;

import com.dp.dpshopbackend.models.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    Optional<Produit> findProduitByReference(String reference);
}
