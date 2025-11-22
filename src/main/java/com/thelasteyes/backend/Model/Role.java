package com.thelasteyes.backend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TB_LST_PERFIL")
public class Role {

    @Id
    @Column(name = "id_perfil")
    private Long id;
    @Column(name = "nome_perfil")
    private String name;
    @Column(name = "desc_perfil")
    private String description;

}
