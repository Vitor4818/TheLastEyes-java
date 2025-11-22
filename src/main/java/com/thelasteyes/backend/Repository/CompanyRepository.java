package com.thelasteyes.backend.Repository;

import com.thelasteyes.backend.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompanyRepository extends JpaRepository <Company, Long>, JpaSpecificationExecutor<Company> {

    boolean existsByEmail(String email);
    boolean existsByCnpj(String cnjp);

    boolean existsByEmailAndIdNot(String email, Long id);
}
