package com.starbuks.app.init;

import com.starbuks.app.entitys.bean.Categoria;
import com.starbuks.app.entitys.bean.Producto;
import com.starbuks.app.persistence.CategoriaRepository;
import com.starbuks.app.persistence.ProductoRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    @PostConstruct
    public void initData() {
        if (categoriaRepository.count() == 0 && productoRepository.count() == 0) {
            List<Categoria> categorias = List.of(
                    new Categoria(null, "Café", "Bebidas a base de café", true),
                    new Categoria(null, "Té", "Variedades de té caliente y frío", true),
                    new Categoria(null, "Repostería", "Dulces y pasteles", true),
                    new Categoria(null, "Bebidas Frías", "Frappuccinos y bebidas heladas", true),
                    new Categoria(null, "Snacks", "Bocadillos y galletas", true),
                    new Categoria(null, "Sandwiches", "Sandwiches calientes y fríos", true),
                    new Categoria(null, "Jugos Naturales", "Bebidas naturales exprimidas", true),
                    new Categoria(null, "Accesorios", "Termos, tazas y vasos", true),
                    new Categoria(null, "Promociones", "Productos en oferta", true),
                    new Categoria(null, "Edición Limitada", "Productos especiales por temporada", true)
            );

            categoriaRepository.saveAll(categorias);
            List<Producto> productos = List.of(
                    Producto.builder().nombre("Latte Vainilla").descripcion("Café con leche y esencia de vainilla").stock(50).precio(BigDecimal.valueOf(12.90)).activo(true).codigo("LAT001").imagenUrl("https://www.starbucksathome.com/pe/sites/default/files/2021-03/Vanilla-Latte-4.jpg").unidadMedida("vaso").peso(0.3).categoriaId(categorias.get(0)).build(),
                    Producto.builder().nombre("Té Chai Latte").descripcion("Té negro especiado con leche").stock(35).precio(BigDecimal.valueOf(10.50)).activo(true).codigo("TEA002").imagenUrl("https://carorocco.com/wp-content/uploads/2021/03/Te-Chai-Latte-VERTICAL.jpg").unidadMedida("vaso").peso(0.3).categoriaId(categorias.get(1)).build(),
                    Producto.builder().nombre("Brownie de Chocolate").descripcion("Brownie artesanal con nueces").stock(20).precio(BigDecimal.valueOf(8.00)).activo(true).codigo("REP003").imagenUrl("https://cdn.recetasderechupete.com/wp-content/uploads/2019/11/Brownie-1200x828.jpg").unidadMedida("unidad").peso(0.15).categoriaId(categorias.get(2)).build(),
                    Producto.builder().nombre("Frappuccino Caramelo").descripcion("Bebida fría de café con caramelo").stock(40).precio(BigDecimal.valueOf(13.50)).activo(true).codigo("BEB004").imagenUrl("https://sal-pimienta.com/wp-content/uploads/2023/10/Japon_Insta_Feed-2-4.jpg").unidadMedida("vaso").peso(0.4).categoriaId(categorias.get(3)).build(),
                    Producto.builder().nombre("Galletas de Avena").descripcion("Galletas artesanales con avena y pasas").stock(30).precio(BigDecimal.valueOf(6.00)).activo(true).codigo("SNA005").imagenUrl("https://www.recetasnestle.cl/sites/default/files/styles/recipe_detail_desktop_new/public/srh_recipes/df2b52515a0d96b7e21123fe0acc90a1.jpg?itok=WTVpcDZd").unidadMedida("paquete").peso(0.2).categoriaId(categorias.get(4)).build(),
                    Producto.builder().nombre("Panini de Pollo").descripcion("Panini con pollo, queso y albahaca").stock(25).precio(BigDecimal.valueOf(14.90)).activo(true).codigo("SAN006").imagenUrl("https://easyways.cl/storage/20220224063245panini-con-pollo-grillado-palya-y-queso.jpg").unidadMedida("unidad").peso(0.35).categoriaId(categorias.get(5)).build(),
                    Producto.builder().nombre("Jugo de Naranja").descripcion("Jugo natural recién exprimido").stock(20).precio(BigDecimal.valueOf(9.50)).activo(true).codigo("JUG007").imagenUrl("https://5aldia.cl/wp-content/uploads/2018/04/jugo-de-naranja-destacado.jpg").unidadMedida("botella").peso(0.5).categoriaId(categorias.get(6)).build(),
                    Producto.builder().nombre("Vaso Reutilizable").descripcion("Vaso ecológico reutilizable 500ml").stock(15).precio(BigDecimal.valueOf(20.00)).activo(true).codigo("ACC008").imagenUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQvV7R90p8vsTasFc3-dn9KgJm_UpDa8A-vIA&s").unidadMedida("unidad").peso(0.25).categoriaId(categorias.get(7)).build(),
                    Producto.builder().nombre("Pack Promoción Desayuno").descripcion("Café + sándwich + galleta").stock(10).precio(BigDecimal.valueOf(18.00)).activo(true).codigo("PRO009").imagenUrl("https://www.starbucks.pe/Multimedia/productos/DESAYUNO_PARA_DOS_V4.png").unidadMedida("pack").peso(0.6).categoriaId(categorias.get(8)).build(),
                    Producto.builder().nombre("Pumpkin Spice Latte").descripcion("Café con leche sabor calabaza - Edición limitada").stock(12).precio(BigDecimal.valueOf(15.00)).activo(true).codigo("LTD010").imagenUrl("https://coffeecopycat.com/wp-content/uploads/2023/10/IcedPumpkinSpiceLatte-1200-x-1200.jpg").unidadMedida("vaso").peso(0.3).categoriaId(categorias.get(9)).build()
            );

            productoRepository.saveAll(productos);

            System.out.println("🟢 Datos insertados correctamente.");
        }
    }
}