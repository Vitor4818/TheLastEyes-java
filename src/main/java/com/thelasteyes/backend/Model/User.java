package com.thelasteyes.backend.Model;

import com.thelasteyes.backend.Dto.PutUserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchConnectionDetails;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TB_LST_USUARIO")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nome_usuario")
    private String name;

    @Column(name = "email_usuario")
    private String email;

    @Column(name = "senha")
    private String password;

    @Column (name = "cpf")
    private String cpf;

    @Column (name = "telefone")
    private String phone;

    @Column(name = "data_nascimento")
    private LocalDate birthDate;

    // Relação com Perfil (Role)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_perfil", nullable = false)
    private Role role;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_emprego", nullable = true)
    private Job currentJob;



    public void updateData(PutUserDto dto) {

        if (dto.name() != null) {
            this.name = dto.name();
        }
        if (dto.email() != null) {
            this.email = dto.email();
        }
        if (dto.password() != null) {
            this.password = dto.password();
        }
        if (dto.phone() != null) {
            this.phone = dto.phone();
        }
        if (dto.birthDate() != null) {
            this.birthDate = dto.birthDate();
        }
    }

}

