CREATE TABLE usuarios (
    id              UUID            NOT NULL,
    nombre_completo VARCHAR(150)    NOT NULL,
    email           VARCHAR(320)    NOT NULL,
    password_hash   VARCHAR(255)    NOT NULL,
    created_at      TIMESTAMP       WITH TIME ZONE  NOT NULL,
    updated_at      TIMESTAMP       WITH TIME ZONE  NOT NULL,

    CONSTRAINT pk_usuarios PRIMARY KEY (id),
    CONSTRAINT uk_usuarios_email UNIQUE (email),
    CONSTRAINT chk_usuarios_fechas
        CHECK ( updated_at >= created_at )
);