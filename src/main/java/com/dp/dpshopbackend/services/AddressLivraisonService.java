package com.dp.dpshopbackend.services;

import com.dp.dpshopbackend.dto.AddressLivraisonDto;

import java.util.List;

public interface AddressLivraisonService {

    AddressLivraisonDto save(AddressLivraisonDto addressLivraisonDto);

    AddressLivraisonDto update(Long idAddress, AddressLivraisonDto addressLivraisonDto);

    AddressLivraisonDto findById(Long id);

    List<AddressLivraisonDto> findAll();

    void delete(Long id);
}
