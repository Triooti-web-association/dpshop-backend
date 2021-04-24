package com.dp.dpshopbackend.dto;

import com.dp.dpshopbackend.models.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    private long id;

    private String reference;

    private String nbreEtoile;

    private String observation;

    private ArticleDto articleDto;

    private UtilisateurDto utilisateurDto;

    public static NotificationDto fromEntityToDto(Notification notification) {
        if (notification == null) {
            return null;
        }

        return NotificationDto.builder()
                .id(notification.getId())
                .reference(notification.getReference())
                .nbreEtoile(notification.getNbreEtoile())
                .observation(notification.getObservation())
                .articleDto(ArticleDto.fromEntityToDto(notification.getArticle()))
                .utilisateurDto(UtilisateurDto.fromEntityToDto(notification.getUtilisateur()))
                .build();
    }

    public static Notification fromDtoToEntity(NotificationDto notificationDto) {
        if (notificationDto == null) {
            return null;
        }

        Notification notification = new Notification();
        notification.setId(notificationDto.getId());
        notification.setReference(notificationDto.getReference());
        notification.setNbreEtoile(notificationDto.getNbreEtoile());
        notification.setObservation(notificationDto.getObservation());
        notification.setArticle(ArticleDto.fromDtoToEntity(notificationDto.getArticleDto()));
        notification.setUtilisateur(UtilisateurDto.fromDtoToEntity(notificationDto.getUtilisateurDto()));

        return notification;
    }



}
