package com.pedrosobreira.numo.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface CategoriaJpaRepository extends JpaRepository<CategoriaJpaEntity, UUID> {
    boolean existsByNomeIgnoreCase(String nome);
}
