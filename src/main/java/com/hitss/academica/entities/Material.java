package com.hitss.academica.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "materiales")
@Getter
@Setter
@SQLDelete(sql = "UPDATE materiales SET deleted = true, deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted = false or deleted is null")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String titulo;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @Column(name = "archivo_url", nullable = false, length = 500)
    private String archivoUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private Boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignatura_id", nullable = false)
    private Asignatura asignatura;
}