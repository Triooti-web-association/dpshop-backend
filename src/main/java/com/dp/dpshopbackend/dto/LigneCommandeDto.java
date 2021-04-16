package com.dp.dpshopbackend.dto;

import com.dp.dpshopbackend.models.LigneCommande;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LigneCommandeDto {

    private long numero;

    private int quantity;

    private double price;

    private CommandeDto commandeDto;

    private ProduitDto produitDto;

    public static LigneCommandeDto fromEntityToDto(LigneCommande ligneCommande) {
        if (ligneCommande == null) {
            return null;
        }

        return LigneCommandeDto.builder()
                .numero(ligneCommande.getNumero())
                .quantity(ligneCommande.getQuantity())
                .price(ligneCommande.getPrice())
                .commandeDto(CommandeDto.formEntityToDto(ligneCommande.getCommande()))
                .produitDto(ProduitDto.fromEntityToDto(ligneCommande.getProduit()))
                .build();
    }

    public static LigneCommande fromDtoToEntity(LigneCommandeDto ligneCommandeDto) {
        if (ligneCommandeDto == null) {
            return null;
        }

        LigneCommande ligneCommande = new LigneCommande();
        ligneCommande.setNumero(ligneCommandeDto.getNumero());
        ligneCommande.setQuantity(ligneCommandeDto.getQuantity());
        ligneCommande.setPrice(ligneCommandeDto.getPrice());

        return ligneCommande;
    }

}
