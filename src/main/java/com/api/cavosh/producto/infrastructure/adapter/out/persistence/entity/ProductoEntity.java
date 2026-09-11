package com.api.cavosh.producto.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Representa un producto guardado en la base de datos
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "productos")
public class ProductoEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaEntity categoria;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 1000)
    private String descripcion;

    @Column(name = "imagen_url", nullable = false, length = 500)
    private String imagenUrl;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @BatchSize(size = 50)
    @OrderBy("orden ASC")
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private List<VarianteProductoEntity> variantes = new ArrayList<>();
}
