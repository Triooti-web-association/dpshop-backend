package com.dp.dpshopbackend.repository;

import com.dp.dpshopbackend.models.LigneCommande;
import com.dp.dpshopbackend.models.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {

    List<Mail> findByOrderByIdDesc();
}