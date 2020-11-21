package com.labguis.gfour.controlador;

import com.labguis.gfour.modelo.MigratedDevice;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class DeviceExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<MigratedDevice> listDevices;
     
    public DeviceExporter(List<MigratedDevice> listDevices) {
        this.listDevices = listDevices;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Placa de inventario", style);      
        createCell(row, 1, "Equipo", style);       
        createCell(row, 2, "Dependencia", style);    
        createCell(row, 3, "Edificio", style);
        createCell(row, 4, "IP Nueva", style);
        createCell(row, 5, "Responsable", style);  
        createCell(row, 6, "Salon", style);  
        createCell(row, 7, "Descripcion", style);    
        createCell(row, 8, "IP Antigua", style);
        createCell(row, 9, "Switch", style);
        createCell(row,10, "Puerto", style);
        createCell(row,11, "Mac", style);
        createCell(row,12, "Clave Estandar", style);
        createCell(row,13, "Fecha de creación", style);
        createCell(row,14, "Fecha de actualización", style);
        createCell(row,15, "Usuario creador", style);
        createCell(row,16, "Usuario que actualiza", style);
        
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines() {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        for (MigratedDevice m : listDevices) {
            Row row = sheet.createRow(rowCount++);
             
            createCell(row, 0, m.getInvPlate(), style);
            createCell(row, 1, m.getTypeDevice().getName(), style);
            createCell(row, 2, m.getAgencie().getName(), style);
            createCell(row, 3, m.getLocation().getName(), style);
            createCell(row, 4, m.getNewIP(), style);
            createCell(row, 5, m.getUser().getName(), style);
            createCell(row, 6, m.getClassRoom(), style);
            createCell(row, 7, m.getDescription(), style);
            createCell(row, 8, m.getOldIP(), style);
            createCell(row, 9, m.getSwitchIP(), style);
            createCell(row,10, m.getPort(), style);
            createCell(row,11, m.getMAC(), style);
            createCell(row,12, m.getStandarKey(), style);
            createCell(row,13, m.getRegisterTime().toLocalDate().toString() + ' ' + m.getRegisterTime().toLocalTime().toString(), style);
            createCell(row,14, m.getUpdateTime().toLocalDate().toString() + ' ' + m.getUpdateTime().toLocalTime().toString(), style);
            createCell(row,15, m.getOwnerUser().getName(), style);
            createCell(row,16, m.getUpdateUser().getName(), style);
             
        }
    }
     
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
}
