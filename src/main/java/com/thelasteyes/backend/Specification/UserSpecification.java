package com.thelasteyes.backend.Specification;

import com.thelasteyes.backend.Constants.UserFields;
import com.thelasteyes.backend.Dto.UserFilter;
import com.thelasteyes.backend.Model.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserSpecification {

    public static Specification<User> withFilter(UserFilter filter) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por Nome
            Optional.ofNullable(filter.name())
                    .map(String::toLowerCase)
                    .ifPresent(name -> predicates.add(
                            cb.like(cb.lower(root.get(UserFields.NAME)), "%" + name + "%")
                    ));

            // Filtro por Email
            Optional.ofNullable(filter.email())
                    .map(String::toLowerCase)
                    .ifPresent(email -> predicates.add(
                            cb.like(cb.lower(root.get(UserFields.EMAIL)), "%" + email + "%")
                    ));

            // Filtro por CPF
            Optional.ofNullable(filter.cpf())
                    .map(String::toLowerCase)
                    .ifPresent(cpf -> predicates.add(
                            cb.like(cb.lower(root.get(UserFields.CPF)), "%" + cpf + "%")
                    ));

            // Filtro por ID do Perfil
            Optional.ofNullable(filter.roleId())
                    .ifPresent(roleId -> predicates.add(
                            cb.equal(root.get(UserFields.ROLE).get("id"), roleId)
                    ));


            var arrayPredicates = predicates.toArray(new Predicate[0]);
            return cb.and(arrayPredicates);
        });
    }
}