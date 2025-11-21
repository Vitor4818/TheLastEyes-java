package com.thelasteyes.backend.Repository;

import com.thelasteyes.backend.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository <Company, Long> {

    boolean existsByEmail(String email);
    boolean existsByCnpj(String cnjp);

    boolean existsByEmailAndIdNot(String email, Long id);
}
