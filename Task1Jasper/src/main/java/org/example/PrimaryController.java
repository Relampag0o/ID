package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import org.slf4j.Logger;

import javafx.fxml.FXML;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }


    public void exportDocument(){
            try {
            InputStream reportFile = getClass().getResourceAsStream("/task1.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportFile);

            String url = "jdbc:mariadb://localhost:3306/techmart";
            String username = "root";
            String password = "5856101097";
            Connection connection = DriverManager.getConnection(url, username, password);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
            JasperViewer.viewReport(jasperPrint, false);;
            JasperExportManager.exportReportToPdfFile(jasperPrint, "report.pdf");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
