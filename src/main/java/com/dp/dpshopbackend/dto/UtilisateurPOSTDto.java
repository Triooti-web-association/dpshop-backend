package com.dp.dpshopbackend.dto;

import com.dp.dpshopbackend.models.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurPOSTDto {

    private Long id;

    private String username;

    private String email;

    private String password;

    private Set<RoleDto> roleDtos = new HashSet<>();

    //  private Set<String> roleDtos;

    public UtilisateurPOSTDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;

    }

    public static UtilisateurPOSTDto fromEntityToDto(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return null;
        }

        return UtilisateurPOSTDto.builder()
                .id(utilisateur.getId())
                .username(utilisateur.getUsername())
                .email(utilisateur.getEmail())
                .password(utilisateur.getPassword())
                //            .roleDtos(utilisateur.getRoles())
                /*
                .roleDtos(utilisateur.getRoles() != null ?
                        utilisateur.getRoles().stream()
                                .map(RoleDto::formEntityToDto)
                                .collect(Collectors.toSet()) : null
                )*/
                .build();

    }

    public static Utilisateur fromDtoToEntity(UtilisateurPOSTDto UtilisateurPOSTDto) {
        if (UtilisateurPOSTDto == null) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(UtilisateurPOSTDto.getId());
        utilisateur.setUsername(UtilisateurPOSTDto.getUsername());
        utilisateur.setEmail(UtilisateurPOSTDto.getEmail());
        utilisateur.setPassword(UtilisateurPOSTDto.getPassword());
        utilisateur.setRoles(utilisateur.getRoles());

        return utilisateur;
    }

    /* public UtilisateurPOSTDto() { }*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleDto> getRoleDtos() {
        return roleDtos;
    }

    public void setRoleDtos(Set<RoleDto> roleDtos) {
        this.roleDtos = roleDtos;
    }
}