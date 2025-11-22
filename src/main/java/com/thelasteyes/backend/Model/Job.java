package com.thelasteyes.backend.Model;


import com.thelasteyes.backend.Dto.PutJobDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_LST_EMPREGO")
public class Job {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_emprego")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Company company;

    @Column(name = "data_admissao")
    private LocalDate hireDate;

    @Column(name = "cargo")
    private String position;

    @Column(name = "tipo_contrato")
    private String contractType;

    @Column(name = "carga_horaria_semanal")
    private int weeklyHours;

    @Column(name = "modelo_trabalho")
    private String workModel;

    @Column(name = "satisfacao_trabalho")
    private int jobSatisfactionScore;


    @OneToOne(mappedBy = "currentJob") // <--- LADO INVERSO: Referencia o NOME DO CAMPO no User.java
    private User user;



    public void updateData(PutJobDto dto) {

        if (dto.hireDate() != null) {
            this.hireDate = dto.hireDate();
        }
        if (dto.position() != null) {
            this.position = dto.position();
        }
        if (dto.contractType() != null) {
            this.contractType = dto.contractType();
        }
        if (dto.workModel() != null) {
            this.workModel = dto.workModel();
        }
        if (dto.weeklyHours() != null) {
            this.weeklyHours = dto.weeklyHours().intValue();
        }
        if (dto.jobSatisfactionScore() != null) {
            this.jobSatisfactionScore = dto.jobSatisfactionScore().intValue();
        }


}
}
