package com.thelasteyes.backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TB_LST_CHECKINS")
public class Checkin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String checkinText;

    @Enumerated(EnumType.STRING)
    private ClassificationStatus status = ClassificationStatus.PENDING;

    private String sentimentResult;
    private Double classificationScore;

    @CreationTimestamp
    private LocalDateTime createdAt;



}