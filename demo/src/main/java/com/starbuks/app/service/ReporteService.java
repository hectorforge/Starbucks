package com.starbuks.app.service;

import com.starbuks.app.entitys.bean.Producto;
import com.starbuks.app.entitys.bean.Venta;
import com.starbuks.app.persistence.ProductoRepository;
import com.starbuks.app.persistence.VentaRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final VentaRepository ventaRepository;

    /**
     * Genera el reporte de ventas en PDF, incluyendo un subreporte para el detalle.
     */
    public byte[] generarReporteVentas() throws JRException {
        try {
            // 1) Obtener todas las ventas con sus detalles
            List<Venta> ventas = ventaRepository.findAllConDetalles();

            // 2) Cargar el .jasper principal desde classpath
            InputStream mainStream =
                new ClassPathResource("templates/reportes/reporte_ventas.jasper")
                    .getInputStream();

            // 3) Cargar el .jasper del subreporte y convertirlo en JasperReport
            InputStream detailStream =
                new ClassPathResource("templates/reportes/detalle_venta.jasper")
                    .getInputStream();
            JasperReport detailReport = (JasperReport) JRLoader.loadObject(detailStream);

            // 4) Preparar parámetros
            Map<String, Object> params = new HashMap<>();
            // Parámetro para inyectar el JasperReport del subreporte
            params.put("detalleSubreport", detailReport);
            // Parámetro para la imagen (si la usas)
            String reportDir = new ClassPathResource("templates/reportes/")
                                    .getFile()
                                    .getAbsolutePath() + "/";
            params.put("imageDir", reportDir);

            // 5) Crear DataSource a partir de la lista de ventas
            JRBeanCollectionDataSource ds =
                new JRBeanCollectionDataSource(ventas);

            // 6) Llenar el reporte principal con datos y parámetros
            JasperPrint jp = JasperFillManager.fillReport(mainStream, params, ds);

            // 7) Exportar a PDF y devolver como byte array
            return JasperExportManager.exportReportToPdf(jp);

        } catch (IOException e) {
            throw new RuntimeException("Error cargando recursos del reporte", e);
        }
    }
    private final ProductoRepository productoRepo;    // o el repo que uses

    // ... método generarReporteVentas()

    public byte[] generarReporteProductos() throws JRException {
        try {
            List<Producto> productos = productoRepo.findAll();
            InputStream jasperStream = new ClassPathResource("templates/reportes/reporte_productos.jasper")
                .getInputStream();

            // 3) Parámetros
            Map<String, Object> params = new HashMap<>();
            // Fecha actual como java.util.Date para el parámetro ReportDate
            Date reportDate = Date.from(
                LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()
            );
            params.put("ReportDate", reportDate);

            String reportDir = new ClassPathResource("templates/reportes/")
                .getFile()
                .getAbsolutePath() + "/";
            params.put("imageDir", reportDir);

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(productos);
            JasperPrint jp = JasperFillManager.fillReport(jasperStream, params, ds);
            return JasperExportManager.exportReportToPdf(jp);

        } catch (IOException e) {
            throw new RuntimeException("No pude cargar reporte_productos.jasper", e);
        }
    }
}
