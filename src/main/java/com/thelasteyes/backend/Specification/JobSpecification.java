package com.thelasteyes.backend.Specification;

import com.thelasteyes.backend.Constants.CompanyFields;
import com.thelasteyes.backend.Constants.JobFields;
import com.thelasteyes.backend.Dto.JobFilter;
import com.thelasteyes.backend.Model.Job;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobSpecification {

    public static Specification<Job> withFilter(JobFilter filter) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por Cargo
            Optional.ofNullable(filter.position())
                    .map(String::toLowerCase)
                    .ifPresent(position -> predicates.add(
                            cb.like(cb.lower(root.get(JobFields.POSITION)), "%" + position + "%")
                    ));

            // Filtro por Tipo de Contrato
            Optional.ofNullable(filter.contractType())
                    .map(String::toLowerCase)
                    .ifPresent(contractType -> predicates.add(
                            cb.like(cb.lower(root.get(JobFields.CONTRACT_TYPE)), "%" + contractType + "%")
                    ));

            // Filtro por Modelo de Trabalho
            Optional.ofNullable(filter.workModel())
                    .map(String::toLowerCase)
                    .ifPresent(workModel -> predicates.add(
                            cb.like(cb.lower(root.get(JobFields.WORK_MODEL)), "%" + workModel + "%")
                    ));

            // Filtro por ID da Empresa
            Optional.ofNullable(filter.companyId())
                    .ifPresent(companyId -> predicates.add(
                            cb.equal(root.get(JobFields.COMPANY).get(CompanyFields.ID), companyId)
                    ));

            // Filtro por Satisfação
            Optional.ofNullable(filter.jobSatisfactionScore())
                    .ifPresent(score -> predicates.add(
                            cb.equal(root.get(JobFields.JOB_SATISFACTION_SCORE), score)
                    ));

            var arrayPredicates = predicates.toArray(new Predicate[0]);

            return cb.and(arrayPredicates);
        });
    }
}