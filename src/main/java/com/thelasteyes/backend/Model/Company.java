package com.thelasteyes.backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TB_LST_EMPRESA")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    private Long id;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "nome_fantasia")
    private String tradeName;

    @Column(name = "razao_social")
    private String corporateName;

    @Column(name = "telefone")
    private String phone;

    @Column(name = "email")
    private String email;

}
