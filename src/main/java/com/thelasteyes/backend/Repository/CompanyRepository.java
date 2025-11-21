package com.thelasteyes.backend.Repository;

import com.thelasteyes.backend.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository <Company, Long> {

}
