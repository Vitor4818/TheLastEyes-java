package com.thelasteyes.backend.Repository;

import com.thelasteyes.backend.Model.Checkin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Long> {
    Page<Checkin> findAllByUserId(Long userId, Pageable pageable);}