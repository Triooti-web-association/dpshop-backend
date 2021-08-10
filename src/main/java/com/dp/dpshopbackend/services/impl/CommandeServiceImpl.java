package com.dp.dpshopbackend.services.impl;

import com.dp.dpshopbackend.dto.CommandeDto;
import com.dp.dpshopbackend.dto.LigneCommandeDto;
import com.dp.dpshopbackend.enumeration.StatusCommande;
import com.dp.dpshopbackend.exceptions.ResourceNotFoundException;
import com.dp.dpshopbackend.models.Article;
import com.dp.dpshopbackend.models.Commande;
import com.dp.dpshopbackend.models.LigneCommande;
import com.dp.dpshopbackend.models.Utilisateur;
import com.dp.dpshopbackend.repository.*;
import com.dp.dpshopbackend.services.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final LigneCommandeService ligneCommandeService;
    private final ArticleService articleService;
    private final ClientService clientService;
    private final UtilisateurService utilisateurService;
    private final LigneCommandeRepository ligneCommandeRepository;
    private final ClientRepository clientRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ArticleRepository articleRepository;
    double total = 0;
    Logger logger = LoggerFactory.getLogger(CommandeServiceImpl.class);

    @Autowired
    public CommandeServiceImpl(CommandeRepository commandeRepository,
                               LigneCommandeService ligneCommandeService,
                               ArticleService articleService,
                               ClientService clientService,
                               UtilisateurService utilisateurService,
                               LigneCommandeRepository ligneCommandeRepository,
                               ClientRepository clientRepository,
                               ArticleRepository articleRepository,
                               UtilisateurRepository utilisateurRepository
    ) {
        this.commandeRepository = commandeRepository;
        this.ligneCommandeService = ligneCommandeService;
        this.articleService = articleService;
        this.clientService = clientService;
        this.utilisateurService = utilisateurService;
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public CommandeDto save(CommandeDto commandeDto) {
        System.out.println("Initial Numero Commande " + commandeDto.getNumeroCommande());
        logger.info("CommandeDto {}", commandeDto);

        //    ClientDto clientOptional = clientService.findById(commandeDto.getClientDto().getId());
        //     Optional<Client> clientOptional = clientRepository.findById(commandeDto.getClientDto().getId());
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById((commandeDto.getUtilisateurPOSTDto().getId()));
        if (utilisateurOptional == null) {
            log.warn("Client with ID {} was not found in the DB", commandeDto.getUtilisateurPOSTDto().getId());
            throw new IllegalArgumentException("Vous devez selectionner un client");
        }

        if (commandeDto.getLcomms() != null) {
            commandeDto.getLcomms().forEach(ligCmdClt -> {
                if (ligCmdClt.getArticleDto() != null) {
                    //          ArticleDto articleDto = articleService.findById(ligCmdClt.getArticleDto().getId());
                    Optional<Article> articleDto = articleRepository.findById(ligCmdClt.getArticleDto().getId());
                    if (articleDto == null) {
                        log.warn("L'article avec l'ID " + ligCmdClt.getArticleDto().getId() + " n'existe pas");
                    }

                } else {
                    log.warn("Impossible d'enregister une commande avec un aticle NULL");

                }

            });
        }

        Commande savedCmdClt = commandeRepository.save(CommandeDto.fromDtoToEntity(commandeDto));

        if (commandeDto.getLcomms() != null) {
            commandeDto.getLcomms().forEach(ligCmdClt -> {
                        LigneCommande ligneCommande = LigneCommandeDto.fromDtoToEntity(ligCmdClt);
                        ligneCommande.setCommande(savedCmdClt);
                        //        ligneCommandeService.save(LigneCommandeDto.fromEntityToDto(ligneCommande));
                        ligneCommandeRepository.save(ligneCommande);

                        Optional<Article> articleDto = articleRepository.findById(ligCmdClt.getArticleDto().getId());

                        articleDto.get().setQuantity(articleDto.get().getQuantity() - ligneCommande.getQuantity());

                        ligneCommande.setNumero(savedCmdClt.getNumeroCommande());
                        ligneCommande.setPrice(articleDto.get().getPrice());

                        //             total += (ligCmdClt.getQuantity() * ligCmdClt.getPrice());

                        total += (ligneCommande.getQuantity() * ligneCommande.getPrice());
                    }

            );

        }

        savedCmdClt.setTotal(total);
        savedCmdClt.setStatusCommande(StatusCommande.ENCOURS);
        savedCmdClt.setDateCommande(new Date());

        return CommandeDto.fromEntityToDto(savedCmdClt);


    }

    @Override
    public CommandeDto update(Long comId, CommandeDto commandeDto) {
        if (!commandeRepository.existsById(comId)) {
            throw new ResourceNotFoundException("Commande not found");
        }

        Optional<Commande> commandeOptional = commandeRepository.findById(comId);

        if (!commandeOptional.isPresent()) {
            throw new ResourceNotFoundException("Commande not found");
        }

        CommandeDto commandeDtoResult = CommandeDto.fromEntityToDto(commandeOptional.get());
        commandeDtoResult.setReference(commandeDto.getReference());
        commandeDtoResult.setNumeroCommande(commandeDto.getNumeroCommande());
        commandeDtoResult.setTotal(commandeDto.getTotal());
        commandeDtoResult.setLocalDateTime(commandeDto.getLocalDateTime());
        commandeDtoResult.setLcomms(commandeDto.getLcomms());
        commandeDtoResult.setStatusCommande(commandeDto.getStatusCommande());
        //    commandeDtoResult.setClientDto(commandeDto.getClientDto());
        commandeDtoResult.setUtilisateurPOSTDto(commandeDto.getUtilisateurPOSTDto());

        return CommandeDto.fromEntityToDto(
                commandeRepository.save(
                        CommandeDto.fromDtoToEntity(commandeDtoResult)
                )
        );
    }

    @Override
    public CommandeDto findById(Long id) {
        if (id == null) {
            log.error("Commande Id is null");
            return null;
        }

        Optional<Commande> commande = commandeRepository.findById(id);

        return Optional.of(CommandeDto.fromEntityToDto(commande.get())).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Aucnun Commande avec l'Id = " + id + "n'a été trouvé")
        );
    }

    @Override
    public List<CommandeDto> findAll() {
        return commandeRepository.findAll().stream()
                .map(CommandeDto::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal countNumberOfCommande() {
        return commandeRepository.countNumberOfCommande();
    }

    @Override
    public BigDecimal sumTotaleOfCommandeByMonth() {
        return commandeRepository.sumTotaleOfCommandeByMonth();
    }

    @Override
    public BigDecimal sumTotalOfCommandesByYear() {
        return commandeRepository.sumTotalOfCommandesByYear();
    }

    @Override
    public List<CommandeDto> findCommandeByCustomerId(Long userId) {
        return commandeRepository.ListCommandeByCustomerId(userId)
                .stream()
                .map(CommandeDto::fromEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<?> countNumberTotalOfCommandeByMonth() {
        return commandeRepository.countNumberOfCommandeByMonth()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<?> sumTotalOfCommandeByMonth() {
        return commandeRepository.sumTotalOfCommandeByMonth()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Page<CommandeDto> findCommandeByCustomerPageables(Long clientId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<CommandeDto> findCommandeByUtilisateurPageables(Long userId, Pageable pageable) {
        return commandeRepository.findCommandeByUtilisateurPageables(userId, pageable)
                .map(CommandeDto::fromEntityToDto);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            log.error("Commande Id is null");
            return;
        }
        commandeRepository.deleteById(id);

    }
}
