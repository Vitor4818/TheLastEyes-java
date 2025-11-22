package com.thelasteyes.backend.Specification;

import com.thelasteyes.backend.Constants.CompanyFields;
import com.thelasteyes.backend.Dto.CompanyFilter;
import com.thelasteyes.backend.Model.Company;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CompanySpecification {

    public static Specification<Company> withFilter(CompanyFilter filter) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Filtro por Nome Fantasia (Trade Name)
            Optional.ofNullable(filter.tradeName())
                    .map(String::toLowerCase)
                    .ifPresent(tradeName -> predicates.add(
                            cb.like(cb.lower(root.get(CompanyFields.TRADE_NAME)), "%" + tradeName + "%")
                    ));

            // 2. Filtro por Razão Social (Corporate Name)
            Optional.ofNullable(filter.corporateName())
                    .map(String::toLowerCase)
                    .ifPresent(corporateName -> predicates.add(
                            cb.like(cb.lower(root.get(CompanyFields.CORPORATE_NAME)), "%" + corporateName + "%")
                    ));

            // 3. Filtro por CNPJ (String LIKE)
            Optional.ofNullable(filter.cnpj())
                    .map(String::toLowerCase)
                    .ifPresent(cnpj -> predicates.add(
                            cb.like(cb.lower(root.get(CompanyFields.CNPJ)), "%" + cnpj + "%")
                    ));

            // 4. Filtro por Telefone
            Optional.ofNullable(filter.phone())
                    .map(String::toLowerCase)
                    .ifPresent(phone -> predicates.add(
                            cb.like(cb.lower(root.get(CompanyFields.PHONE)), "%" + phone + "%")
                    ));

            // 5. Filtro por Email
            Optional.ofNullable(filter.email())
                    .map(String::toLowerCase)
                    .ifPresent(email -> predicates.add(
                            cb.like(cb.lower(root.get(CompanyFields.EMAIL)), "%" + email + "%")
                    ));

            var arrayPredicates = predicates.toArray(new Predicate[0]);

            // Combina todas as premissas com AND
            return cb.and(arrayPredicates);
        });
    }
}