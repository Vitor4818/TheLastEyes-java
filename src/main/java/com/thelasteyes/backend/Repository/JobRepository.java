package com.thelasteyes.backend.Repository;

import com.thelasteyes.backend.Model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {


}
