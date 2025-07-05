package com.starbuks.app.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Service
public class BoletaService {

    @Autowired
    private DataSource dataSource;

    public byte[] generarBoleta(Long ventaId,
                                String voucherId,
                                String clientName,
                                String clientDocument,
                                java.util.Date saleDate,
                                String paymentMethod) throws Exception {
        // 1. Carga del .jasper compilado
        ClassPathResource resource = 
            new ClassPathResource("templates/reportes/boleta.jasper");
        try (InputStream jasperStream = resource.getInputStream();
             Connection conn = dataSource.getConnection()) {

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
            // 2. Parámetros
            Map<String, Object> params = new HashMap<>();
            params.put("VENTA_ID", ventaId);
            params.put("voucher_id", voucherId);
            params.put("client_name", clientName);
            params.put("client_document", clientDocument);
            params.put("sale_date", saleDate);
            params.put("payment_method", paymentMethod);
            String reportDir = new ClassPathResource("templates/reportes/")
                    .getFile()
                    .getAbsolutePath() + "/";
            params.put("imageDir", reportDir);

            // 3. Llenado del reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);

            // 4. Exportar a PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }
}
