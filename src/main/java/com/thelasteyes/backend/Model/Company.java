package com.thelasteyes.backend.Model;

import com.thelasteyes.backend.Dto.PutCompanyDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy = "company") // "company" é o nome do campo na entidade Job
    private List<Job> jobs;


    public void updateData(PutCompanyDto dto) {

        if (dto.tradeName() != null) {
            this.tradeName = dto.tradeName();
        }
        if (dto.corporateName() != null) {
            this.corporateName = dto.corporateName();
        }
        if (dto.phone() != null) {
            this.phone = dto.phone();
        }
        if (dto.email() != null) {
            this.email = dto.email();
        }
    }

}
