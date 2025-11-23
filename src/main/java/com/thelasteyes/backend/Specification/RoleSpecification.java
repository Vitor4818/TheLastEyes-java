package com.thelasteyes.backend.Specification;

import com.thelasteyes.backend.Constants.RoleFields;
import com.thelasteyes.backend.Dto.RoleFilter;
import com.thelasteyes.backend.Model.Role;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleSpecification {

    public static Specification<Role> withFilter(RoleFilter filter) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filktro por nome do setor
            Optional.ofNullable(filter.name())
                    .map(String::toLowerCase)
                    .ifPresent(name -> predicates.add(
                            cb.like(cb.lower(root.get(RoleFields.NAME)), "%" + name + "%")
                    ));

            var arrayPredicates = predicates.toArray(new Predicate[0]);


            return cb.and(arrayPredicates);
        });
    }
}