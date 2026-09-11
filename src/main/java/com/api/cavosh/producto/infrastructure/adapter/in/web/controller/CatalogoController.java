package com.api.cavosh.producto.infrastructure.adapter.in.web.controller;

import com.api.cavosh.producto.application.port.in.categoria.CategoriaResult;
import com.api.cavosh.producto.application.port.in.categoria.ListarCategoriasUseCase;
import com.api.cavosh.producto.application.port.in.producto.ProductoResumenResult;
import com.api.cavosh.producto.application.port.in.producto.busqueda.BuscarProductosQuery;
import com.api.cavosh.producto.application.port.in.producto.busqueda.BuscarProductosUseCase;
import com.api.cavosh.producto.application.port.in.producto.detalle.ObtenerProductoUseCase;
import com.api.cavosh.producto.application.port.in.producto.detalle.ProductoDetalleResult;
import com.api.cavosh.producto.application.port.in.producto.nuevo.ListarProductosNuevosUseCase;
import com.api.cavosh.producto.domain.valueobject.CategoriaId;
import com.api.cavosh.producto.domain.valueobject.ProductoId;
import com.api.cavosh.producto.infrastructure.adapter.in.web.mapper.CatalogoWebMapper;
import com.api.cavosh.producto.infrastructure.adapter.in.web.response.CategoriaResponse;
import com.api.cavosh.producto.infrastructure.adapter.in.web.response.ProductoDetalleResponse;
import com.api.cavosh.producto.infrastructure.adapter.in.web.response.ProductoResumenResponse;
import com.api.cavosh.shared.adapter.in.web.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Expone las consultas usadas para mostrar el catalogo
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CatalogoController {

    private final ListarCategoriasUseCase listarCategoriasUseCase;
    private final BuscarProductosUseCase buscarProductosUseCase;
    private final ListarProductosNuevosUseCase listarProductosNuevosUseCase;
    private final ObtenerProductoUseCase obtenerProductoUseCase;
    private final CatalogoWebMapper catalogoWebMapper;

    /**
     * Lista las categorias activas del menu
     *
     * @return categorias visibles
     */
    @GetMapping("/categorias")
    public ResponseEntity<ApiResponse<List<CategoriaResponse>>> listarCategorias() {
        List<CategoriaResult> result = listarCategoriasUseCase.ejecutar();
        List<CategoriaResponse> response = result.stream()
                .map(catalogoWebMapper::toResponse)
                .toList();

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK,
                        "Categorias obtenidas correctamente",
                        response
                )
        );
    }

    /**
     * Busca productos por categoria o por el texto escrito
     *
     * @param categoriaId identificador de categoria, si se desea filtrar
     * @param buscar texto de busqueda, si existe
     * @return productos encontrados
     */
    @GetMapping("/productos")
    public ResponseEntity<ApiResponse<List<ProductoResumenResponse>>> buscarProductos(
            @RequestParam(required = false) String categoriaId,
            @RequestParam(required = false) String buscar
    ) {
        CategoriaId categoria = categoriaId == null
                ? null
                : CategoriaId.fromString(categoriaId);

        List<ProductoResumenResult> result = buscarProductosUseCase.ejecutar(
                new BuscarProductosQuery(categoria, buscar)
        );

        List<ProductoResumenResponse> response = result.stream()
                .map(catalogoWebMapper::toResponse)
                .toList();

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK,
                        "Productos obtenidos correctamente",
                        response
                )
        );
    }

    /**
     * Lista los productos agregados recientemente
     *
     * @param limite cantidad maxima solicitada
     * @return productos nuevos
     */
    @GetMapping("/productos/nuevos")
    public ResponseEntity<ApiResponse<List<ProductoResumenResponse>>> listarNuevos(
            @RequestParam(defaultValue = "10") int limite
    ) {
        List<ProductoResumenResponse> response = listarProductosNuevosUseCase
                .ejecutar(limite)
                .stream()
                .map(catalogoWebMapper::toResponse)
                .toList();

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK,
                        "Productos nuevos obtenidos correctamente",
                        response
                )
        );
    }

    /**
     * Obtiene el detalle y las variantes de un producto
     *
     * @param id identificador del producto
     * @return detalle del producto
     */
    @GetMapping("/productos/{id}")
    public ResponseEntity<ApiResponse<ProductoDetalleResponse>> obtenerProducto(
            @PathVariable String id
    ) {
        ProductoDetalleResult result = obtenerProductoUseCase.ejecutar(
                ProductoId.fromString(id)
        );

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK,
                        "Producto obtenido correctamente",
                        catalogoWebMapper.toResponse(result)
                )
        );
    }
}
