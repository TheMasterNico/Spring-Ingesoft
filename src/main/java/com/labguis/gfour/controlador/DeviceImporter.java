package com.labguis.gfour.controlador;

import com.labguis.gfour.interfaceService.ITypeDeviceService;
import com.labguis.gfour.modelo.Agencie;
import com.labguis.gfour.modelo.Location;
import com.labguis.gfour.modelo.MigratedDevice;
import com.labguis.gfour.modelo.TypeDevice;
import com.labguis.gfour.modelo.User;
import com.labguis.gfour.repository.ITypeDevice;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public class DeviceImporter {

    
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    

}
