package com.dp.dpshopbackend.dto;

import com.dp.dpshopbackend.models.Notification;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {

    private String reference;

    private String nbreEtoile;

    private String observation;

    private ProduitDto produitDto;

    private UtilisateurDto utilisateurDto;

    public static NotificationDto fromEntityToDto(Notification notification) {
        if (notification == null) {
            return null;
        }

        return NotificationDto.builder()
                .reference(notification.getReference())
                .nbreEtoile(notification.getNbreEtoile())
                .observation(notification.getObservation())
                .produitDto(ProduitDto.fromEntityToDto(notification.getProduit()))
                .utilisateurDto(UtilisateurDto.fromEntityToDto(notification.getUtilisateur()))
                .build();
    }

    public static Notification fromDtoToEntity(NotificationDto notificationDto) {
        if (notificationDto == null) {
            return null;
        }

        Notification notification = new Notification();
        notification.setReference(notificationDto.getReference());
        notification.setNbreEtoile(notificationDto.getNbreEtoile());
        notification.setObservation(notificationDto.getObservation());

        return notification;
    }



}