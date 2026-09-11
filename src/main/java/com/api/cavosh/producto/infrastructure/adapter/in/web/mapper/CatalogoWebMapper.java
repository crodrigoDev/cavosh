package com.api.cavosh.producto.infrastructure.adapter.in.web.mapper;

import com.api.cavosh.producto.application.port.in.categoria.CategoriaResult;
import com.api.cavosh.producto.application.port.in.producto.ProductoResumenResult;
import com.api.cavosh.producto.application.port.in.producto.detalle.ProductoDetalleResult;
import com.api.cavosh.producto.application.port.in.producto.detalle.VarianteProductoResult;
import com.api.cavosh.producto.domain.valueobject.CategoriaId;
import com.api.cavosh.producto.domain.valueobject.ProductoId;
import com.api.cavosh.producto.domain.valueobject.VarianteProductoId;
import com.api.cavosh.producto.infrastructure.adapter.in.web.response.CategoriaResponse;
import com.api.cavosh.producto.infrastructure.adapter.in.web.response.ProductoDetalleResponse;
import com.api.cavosh.producto.infrastructure.adapter.in.web.response.ProductoResumenResponse;
import com.api.cavosh.producto.infrastructure.adapter.in.web.response.VarianteProductoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

/**
 * Convierte resultados del catalogo en respuestas HTTP
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CatalogoWebMapper {

    /**
     * Convierte una categoria en su respuesta HTTP
     *
     * @param result categoria obtenida
     * @return categoria para el cliente
     */
    CategoriaResponse toResponse(CategoriaResult result);

    /**
     * Convierte un resumen de producto en su respuesta HTTP
     *
     * @param result producto obtenido
     * @return resumen para el cliente
     */
    ProductoResumenResponse toResponse(ProductoResumenResult result);

    /**
     * Convierte el detalle de producto en su respuesta HTTP
     *
     * @param result producto obtenido
     * @return detalle para el cliente
     */
    ProductoDetalleResponse toResponse(ProductoDetalleResult result);

    /**
     * Convierte una variante en su respuesta HTTP
     *
     * @param result variante obtenida
     * @return variante para el cliente
     */
    VarianteProductoResponse toResponse(VarianteProductoResult result);

    /**
     * Obtiene el UUID guardado en un id de producto
     *
     * @param id identificador del dominio
     * @return UUID del producto
     */
    default UUID toUuid(ProductoId id) {
        return id == null ? null : id.value();
    }

    /**
     * Obtiene el UUID guardado en un id de categoria
     *
     * @param id identificador del dominio
     * @return UUID de la categoria
     */
    default UUID toUuid(CategoriaId id) {
        return id == null ? null : id.value();
    }

    /**
     * Obtiene el UUID guardado en un id de variante
     *
     * @param id identificador del dominio
     * @return UUID de la variante
     */
    default UUID toUuid(VarianteProductoId id) {
        return id == null ? null : id.value();
    }
}
