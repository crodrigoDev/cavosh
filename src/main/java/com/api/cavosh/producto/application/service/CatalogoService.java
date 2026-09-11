package com.api.cavosh.producto.application.service;

import com.api.cavosh.producto.application.exception.ProductoNotFoundException;
import com.api.cavosh.producto.application.port.in.categoria.CategoriaResult;
import com.api.cavosh.producto.application.port.in.categoria.ListarCategoriasUseCase;
import com.api.cavosh.producto.application.port.in.producto.ProductoResumenResult;
import com.api.cavosh.producto.application.port.in.producto.busqueda.BuscarProductosQuery;
import com.api.cavosh.producto.application.port.in.producto.busqueda.BuscarProductosUseCase;
import com.api.cavosh.producto.application.port.in.producto.detalle.ObtenerProductoUseCase;
import com.api.cavosh.producto.application.port.in.producto.detalle.ProductoDetalleResult;
import com.api.cavosh.producto.application.port.in.producto.detalle.VarianteProductoResult;
import com.api.cavosh.producto.application.port.in.producto.nuevo.ListarProductosNuevosUseCase;
import com.api.cavosh.producto.application.port.out.CatalogoRepository;
import com.api.cavosh.producto.domain.model.Categoria;
import com.api.cavosh.producto.domain.model.Producto;
import com.api.cavosh.producto.domain.model.VarianteProducto;
import com.api.cavosh.producto.domain.valueobject.ProductoId;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Atiende las consultas principales del catalogo
 */
public final class CatalogoService implements
        ListarCategoriasUseCase,
        BuscarProductosUseCase,
        ListarProductosNuevosUseCase,
        ObtenerProductoUseCase {

    private static final int MAX_NUEVOS = 50;

    private final CatalogoRepository catalogoRepository;

    /**
     * Crea el servicio con acceso al catalogo guardado
     *
     * @param catalogoRepository repositorio usado para consultar el catalogo
     */
    public CatalogoService(CatalogoRepository catalogoRepository) {
        this.catalogoRepository = Objects.requireNonNull(
                catalogoRepository,
                "El repositorio del catalogo no puede ser null"
        );
    }

    /**
     * Obtiene las categorias visibles
     *
     * @return categorias ordenadas
     */
    @Override
    public List<CategoriaResult> ejecutar() {
        return catalogoRepository.findCategoriasActivas()
                .stream()
                .map(this::toCategoriaResult)
                .toList();
    }

    /**
     * Busca productos usando filtros opcionales
     *
     * @param query filtros solicitados
     * @return productos encontrados
     */
    @Override
    public List<ProductoResumenResult> ejecutar(
            BuscarProductosQuery query
    ) {
        Objects.requireNonNull(query, "La busqueda no puede ser null");

        String texto = normalizarTexto(query.texto());
        return catalogoRepository.findProductos(
                query.categoriaId(),
                texto
        ).stream()
                .map(this::toResumenResult)
                .toList();
    }

    /**
     * Obtiene los productos agregados recientemente
     *
     * @param limite cantidad maxima solicitada
     * @return productos nuevos
     */
    @Override
    public List<ProductoResumenResult> ejecutar(int limite) {
        if (limite < 1 || limite > MAX_NUEVOS)
            throw new IllegalArgumentException("El limite debe estar entre 1 y 50");

        return catalogoRepository.findProductosNuevos(limite)
                .stream()
                .map(this::toResumenResult)
                .toList();
    }

    /**
     * Obtiene el detalle de un producto activo
     *
     * @param productoId identificador solicitado
     * @return detalle encontrado
     */
    @Override
    public ProductoDetalleResult ejecutar(ProductoId productoId) {
        Objects.requireNonNull(productoId, "El id del producto no puede ser null");

        Producto producto = catalogoRepository.findProductoById(productoId)
                .orElseThrow(ProductoNotFoundException::new);

        return new ProductoDetalleResult(
                producto.id(),
                toCategoriaResult(producto.categoria()),
                producto.nombre(),
                producto.descripcion(),
                producto.imagenUrl(),
                producto.variantes().stream()
                        .map(this::toVarianteResult)
                        .toList()
        );
    }

    private String normalizarTexto(String texto) {
        if (texto == null || texto.isBlank())
            return null;

        return texto.trim();
    }

    private CategoriaResult toCategoriaResult(Categoria categoria) {
        return new CategoriaResult(
                categoria.id(),
                categoria.nombre(),
                categoria.descripcion(),
                categoria.orden()
        );
    }

    private ProductoResumenResult toResumenResult(Producto producto) {
        VarianteProducto varianteMenor = producto.variantes()
                .stream()
                .min(Comparator.comparing(variante -> variante.precio().monto()))
                .orElseThrow();

        return new ProductoResumenResult(
                producto.id(),
                toCategoriaResult(producto.categoria()),
                producto.nombre(),
                producto.descripcion(),
                producto.imagenUrl(),
                varianteMenor.precio().monto(),
                varianteMenor.precio().moneda()
        );
    }

    private VarianteProductoResult toVarianteResult(
            VarianteProducto variante
    ) {
        return new VarianteProductoResult(
                variante.id(),
                variante.nombre(),
                variante.precio().monto(),
                variante.precio().moneda()
        );
    }
}
