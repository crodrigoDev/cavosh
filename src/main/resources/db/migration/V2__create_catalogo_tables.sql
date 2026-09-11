CREATE TABLE categorias_producto (
    id          UUID            NOT NULL,
    nombre      VARCHAR(100)    NOT NULL,
    descripcion VARCHAR(300)    NOT NULL DEFAULT '',
    orden       INTEGER         NOT NULL DEFAULT 0,
    activa      BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT pk_categorias_producto PRIMARY KEY (id),
    CONSTRAINT uk_categorias_producto_nombre UNIQUE (nombre),
    CONSTRAINT chk_categorias_producto_orden CHECK (orden >= 0),
    CONSTRAINT chk_categorias_producto_fechas CHECK (updated_at >= created_at)
);

CREATE TABLE productos (
    id           UUID            NOT NULL,
    categoria_id UUID            NOT NULL,
    nombre       VARCHAR(150)    NOT NULL,
    descripcion  VARCHAR(1000)   NOT NULL DEFAULT '',
    imagen_url   VARCHAR(500)    NOT NULL DEFAULT '',
    activo       BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT pk_productos PRIMARY KEY (id),
    CONSTRAINT fk_productos_categoria
        FOREIGN KEY (categoria_id) REFERENCES categorias_producto (id),
    CONSTRAINT chk_productos_fechas CHECK (updated_at >= created_at)
);

CREATE TABLE variantes_producto (
    id          UUID            NOT NULL,
    producto_id UUID            NOT NULL,
    nombre      VARCHAR(80)     NOT NULL,
    precio      NUMERIC(12, 2)  NOT NULL,
    moneda      VARCHAR(3)      NOT NULL,
    orden       INTEGER         NOT NULL DEFAULT 0,
    activa      BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT pk_variantes_producto PRIMARY KEY (id),
    CONSTRAINT fk_variantes_producto_producto
        FOREIGN KEY (producto_id) REFERENCES productos (id),
    CONSTRAINT uk_variantes_producto_nombre UNIQUE (producto_id, nombre),
    CONSTRAINT chk_variantes_producto_precio CHECK (precio >= 0),
    CONSTRAINT chk_variantes_producto_orden CHECK (orden >= 0),
    CONSTRAINT chk_variantes_producto_moneda CHECK (LENGTH(moneda) = 3),
    CONSTRAINT chk_variantes_producto_fechas CHECK (updated_at >= created_at)
);

CREATE INDEX idx_productos_categoria_activo
    ON productos (categoria_id, activo);

CREATE INDEX idx_productos_created_at
    ON productos (created_at DESC);

CREATE INDEX idx_variantes_producto_activa
    ON variantes_producto (producto_id, activa);

INSERT INTO categorias_producto (
    id,
    nombre,
    descripcion,
    orden,
    activa,
    created_at,
    updated_at
) VALUES
    (
        '10000000-0000-0000-0000-000000000001',
        'Bebidas calientes',
        'Cafe, chocolate y otras bebidas calientes',
        1,
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        '10000000-0000-0000-0000-000000000002',
        'Bebidas frias',
        'Cafe frio, te y bebidas refrescantes',
        2,
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        '10000000-0000-0000-0000-000000000003',
        'Sandwiches',
        'Sandwiches preparados para recoger',
        3,
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        '10000000-0000-0000-0000-000000000004',
        'Panaderia',
        'Panes y productos horneados',
        4,
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        '10000000-0000-0000-0000-000000000005',
        'Postres',
        'Opciones dulces para acompañar tu pedido',
        5,
        TRUE,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );
