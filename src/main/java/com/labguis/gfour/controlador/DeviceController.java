package com.labguis.gfour.controlador;

import com.labguis.gfour.interfaceService.IAgencieService;
import com.labguis.gfour.interfaceService.IDeviceService;
import com.labguis.gfour.interfaceService.ILocationService;
import com.labguis.gfour.interfaceService.ITypeDeviceService;
import com.labguis.gfour.interfaceService.IUsuarioService;
import com.labguis.gfour.interfaceService.InterfaceWhiteList;
import com.labguis.gfour.modelo.Agencie;
import com.labguis.gfour.modelo.Location;
import com.labguis.gfour.modelo.MigratedDevice;
import com.labguis.gfour.modelo.TypeDevice;
import com.labguis.gfour.modelo.User;
import com.labguis.gfour.modelo.WhiteList;
import com.labguis.gfour.repository.UserRepository;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nicolas Castillo
 */
@Controller
@RequestMapping
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DeviceController {

    @Autowired
    private IDeviceService ids;
    @Autowired
    private IAgencieService ias;
    @Autowired
    private ILocationService ils;
    @Autowired
    private IUsuarioService ius;
    @Autowired
    private ITypeDeviceService itds;
    @Autowired
    private InterfaceWhiteList iwl;

    @GetMapping("/equipos")
    public String equipos(Model model, HttpServletRequest request, @RequestParam(required = false) String edit) {
        if (!ius.isUserLogged(request)) { // if not logged
            return "redirect:/login";
        }
        model.addAttribute("datos", ids.listar());
        model.addAttribute("isadmin", ius.isUserAdmin(request));
        model.addAttribute("device", edit != null ? ids.findByInvPlate(edit) : new MigratedDevice()); // if is edit send specific device, if not, send new empty one
        model.addAttribute("agencies", ias.listar());
        model.addAttribute("typeDevices", itds.listar());
        model.addAttribute("locations", ils.listar());
        model.addAttribute("users", ius.listar());
        return "equipos";
    }

    @PostMapping("/device/import")
    public String importExcel(@RequestParam("file") MultipartFile file, Model model, HttpServletRequest request) throws IOException {
        if (DeviceImporter.hasExcelFormat(file)) {

            List<MigratedDevice> devices = excelData(file.getInputStream());
            devices.forEach(device -> {
                device.setRegisterTime(LocalDateTime.now());
                device.setUpdateTime(LocalDateTime.now());
                device.setOwnerUser(ius.findByCookie(request));
                device.setUpdateUser(ius.findByCookie(request));
                MigratedDevice check_inv = ids.findByInvPlate(device.getInvPlate());
                MigratedDevice check_stk = ids.findByStandarKey(device.getStandarKey());
                if (check_inv != null) {
                    model.addAttribute("error", "La placa de inventario ya existe");
                } else if (check_stk != null) {
                    model.addAttribute("error", "La clave estandar ya existe");
                } else {
                    ids.save(device);
                }
            });

        }        // the models next, its same in /equipos
        model.addAttribute("datos", ids.listar());
        model.addAttribute("isadmin", ius.isUserAdmin(request));
        model.addAttribute("device", new MigratedDevice());
        model.addAttribute("agencies", ias.listar());
        model.addAttribute("typeDevices", itds.listar());
        model.addAttribute("locations", ils.listar());
        model.addAttribute("users", ius.listar());
        return "equipos";

    }

    @GetMapping("/device/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=equipos-" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        DeviceExporter deviceExp = new DeviceExporter(ids.listar());
        deviceExp.export(response);
    }

    @PostMapping("/device/save")
    public String save(@Validated MigratedDevice p, Model model, HttpServletRequest request) {
        p.setRegisterTime(LocalDateTime.now());
        p.setUpdateTime(LocalDateTime.now());
        p.setOwnerUser(ius.findByCookie(request));
        p.setUpdateUser(ius.findByCookie(request));
        MigratedDevice check_inv = ids.findByInvPlate(p.getInvPlate());
        MigratedDevice check_stk = ids.findByStandarKey(p.getStandarKey());
        if (check_inv != null) {
            model.addAttribute("error", "La placa de inventario ya existe");
        } else if (check_stk != null) {
            model.addAttribute("error", "La clave estandar ya existe");
        } else {
            ids.save(p);
            return "redirect:/equipos";
        }
        // the models next, its same in /equipos
        model.addAttribute("datos", ids.listar());
        model.addAttribute("isadmin", ius.isUserAdmin(request));
        model.addAttribute("device", new MigratedDevice());
        model.addAttribute("agencies", ias.listar());
        model.addAttribute("typeDevices", itds.listar());
        model.addAttribute("locations", ils.listar());
        model.addAttribute("users", ius.listar());
        return "equipos";
    }

    @PostMapping("/device/update")
    public String update(@Validated MigratedDevice p, Model model, HttpServletRequest request) {
        p.setUpdateTime(LocalDateTime.now());
        p.setUpdateUser(ius.findByCookie(request));
        ids.save(p);
        return "redirect:/equipos";
    }

    @GetMapping("/device/delete/{id}")
    public String eliminar(@PathVariable int id, Model model, HttpServletRequest request) {
        if (!ius.isUserLogged(request)) { // if not logged
            return "redirect:/login";
        }
        eliminar(id);
        return "redirect:/equipos";
    }

    public void eliminar(int id) {
        ids.delete(id);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void migration() {

        System.err.println("Wait for the start...");
        if (ius.findByName("Usuario inicial") == null) {

            WhiteList wl = new WhiteList();
            wl.setEmail("email@testing.com");
            iwl.save(wl);

            User u = new User();
            u.setName("Usuario inicial");
            u.setEmail("email@testing.com");
            u.setPassword(ius.hashPassword("password"));
            ius.save(u);
        }

        String[] names = new String[]{
            "Decanatura",
            "Vicedecanatura Académica",
            "Vicedecanatura de Investigación y Extensión",
            "Dirección de Bienestar",
            "Escuela Doctoral",
            "Departamentos",
            "Áreas Curriculares",
            "Secretaría de Facultad",
            "Unidad Administrativa",
            "Programa de Relaciones Internacionales - PRI"
        };
        if (ias.findByName(names[0]) == null) {
            Agencie agencie;
            for (String name : names) {
                agencie = new Agencie();
                agencie.setName(name);
                ias.save(agencie);
            }
        }
        String[][] lugares = {
            {"101", "Torre de Enfermería"},
            {"102", "Biblioteca Central o Biblioteca Gabriel Garcia Marquez"},
            {"103", "Polideportivo - División de registro"},
            {"104", "Auditorio León de Greiff"},
            {"201", "Facultad de Derecho - Ciencias Políticas y Sociales"},
            {"205", "Edificio Orlando Fals Borda: Departamento de Sociología"},
            {"207", "Museo de Arquitectura Leopoldo Rother"},
            {"210", "Facultad de Odontología"},
            {"211", "Cafetería de Odontología"},
            {"212", "Aulas de Ciencias Humanas"},
            {"213", "Restaurante Campus de Ciencias Humanas La flecha"},
            {"214", "Edificio Antonio Nariño - Departamento de Ingeniería Civil y Agrícola - Departamento de Lingüística"},
            {"217", "Edificio Francisco de Paula Santander - Diseño Gráfico"},
            {"224", "Edificio Manuel Ancízar -Geografía, Geología"},
            {"225", "Edificio de Postgrados en Ciencias Humanas Rogelio Salmona"},
            {"229", "y 231 Departamento de lenguas extranjeras"},
            {"228", "Edificio Enfermería  ( edificio nuevo )"},
            {"230", "Banco Popular - Sucursal Universidad Nacional"},
            {"235", "Portería Peatonal Calle 26"},
            {"236", "Subestación Eléctrica Calle 26"},
            {"238", "Postgrados de Ciencias Económicas"},
            {"239", "Filosofía"},
            {"251", "Capilla"},
            {"252", "y 253 Porterias vehiculares Capilla"},
            {"301", "Escuela de Artes Plásticas"},
            {"303", "Escuela de Arquitectura - edificio demolido"},
            {"305", "Conservatorio de Música"},
            {"309", "Talleres y Aulas de Construcción"},
            {"310", "y 311 Facultad de Ciencias Económicas"},
            {"314", " Edificio SINDU - Postgrados en Arquitectura"},
            {"317", "Museo de Arte"},
            {"401", "Facultad de Ingeniería"},
            {"404", "Edificio Takeuchi - Departamentos de Matemáticas, Física y Estadística"},
            {"405", "Postgrados en Matemáticas y Física"},
            {"406", "Instituto de Extensión e Investigación Ingeniería IEI"},
            {"407", "Postgrados en Materiales y Procesos de Manufactura"},
            {"408", "Laboratorio de Hidráulica - Hangar y CADE Ingeniería"},
            {"409", "Laboratorio de Hidráulica"},
            {"410", "Playa de Modelos (Anexo 409)"},
            {"411", "Laboratorios de Ingeniería"},
            {"412", "Laboratorio de Ingeniería Química"},
            {"413", "Observatorio Astronómico"},
            {"414", "Canchas de Tenis T-2, T-3 y T-4"},
            {"421", "Departamento de Biología"},
            {"425", "Instituto de Ciencias Naturales, Museo de Historía Natural"},
            {"426", "Instituto de Genética"},
            {"431", "Instituto Pedagógico Arturo Ramírez Montúfar: Colegio IPARM"},
            {"433", "Almacén e Imprenta"},
            {"435", "Talleres de Mantenimiento"},
            {"436", "Parque Automotor"},
            {"437", "Centro de Acopio de Residuos Sólidos"},
            {"438", "Talleres y Gestiones de Mantenimiento"},
            {"450", "Departamento de Farmacia"},
            {"451", "Departamento de Química"},
            {"452", "Postgrados en Bioquímica y Carbones"},
            {"453", "Aulas de Ingeniería"},
            {"454", "Edificio de Ciencia y Tecnología CyT : Luis Carlos Sarmiento Angulo"},
            {"471", "Facultad de Medicina"},
            {"472", "Subestación Eléctrica"},
            {"476", "Facultad de Ciencias"},
            {"477", "Aulas de Informática"},
            {"480", "Depósitos Facultad de Medicina"},
            {"481", "Facultad de Medicina Veterinaria y Zootecnia"},
            {"495", "Cancha de T1"},
            {"496", "Cancha de Voleibol"},
            {"500", "Departamento de Agronomía"},
            {"500A", "a 500P Instituto de Ciencia y Tecnología de Alimentos ICTA"},
            {"501", "Cirugía y Clínica de Grandes Animales"},
            {"502", "Aula y Laboratorios de Histopatología e Inseminación"},
            {"503", "Auditorios, Anfiteatros y Microbiología"},
            {"504", "Patología Aviar, Gallinero y Perrera"},
            {"505", "Laboratorio de Inseminación y Corral de Equinos"},
            {"506", "Laboratorio de Patología Clínica y Corral de Bovinos"},
            {"507", "Clínica de Pequeños Animales"},
            {"508", "Oficinas"},
            {"510", "Farmacia y Oficinas"},
            {"531", "a 537 ICA Laboratorios de investigaciones patológicas ( comodato )"},
            {"561", "Posgrados de Veterinaria"},
            {"561A", "Oficinas de Producción Animal"},
            {"561B", "Posgrado Reproducción Animal"},
            {"561C", "Bioterio y Establos de Producción"},
            {"561D", "Comportamiento Anima"},
            {"561E", "Investigaciones Avícolas"},
            {"561F", "Bioterio de Experimentación"},
            {"561G", "Unibiblos"},
            {"561H", "Aulas y depósitos Unibiblos"},
            {"571", "Hemeroteca Nacional"},
            {"603", "Portería Calle 45"},
            {"606", "Instituto Interamericano de Cooperación para la Agricultura IICA ( comodato)"},
            {"608", "Unidad de Servicios de Computación y SIA"},
            {"610", "Centro en Investigación y Desarrollo en Información Geográfica (Acceso por el IGAC, comodato )"},
            {"614", "Central Telefónica"},
            {"615", "Laboratorio de Química e Ingeominas ( Cerrado )"},
            {"621", "Instituto Geográfico Agustin Codazzi IGAC ( comodato )"},
            {"631", "Instituto Colombiano de Ingeniería Minería: INGEOMINAS ( comodato )"},
            {"632", "Galpón de Biología"},
            {"700", "Portería Vehicular y Peatonal Transversal 38"},
            {"701", "Departamento de Cine y Televisión"},
            {"710", "Diamante de Béisbol ( area vacia )"},
            {"731", "Estadio de fútbol Alfonso López Pumarejo"},
            {"761", "Concha Acústica"},
            {"861", "Edificio Uriel Gutiérrez"},
            {"862", "Unidad Camilo Torres"},
            {"901", "Portería Vehicular y Peatonal Calle 53"},
            {"905", "Jardín Infantil"},
            {"910", "Instituto Colombiano de Normas Técnicas y Certificación: ICONTEC ( comodato )"},
            {"933", "CASE CAN Area de salúd (fuera del campus, CAN Calle 44  con carrera 59, por los lados de Gran Estación)."}
        };
        if (ils.findByName(lugares[0][1]) == null) {
            Location location;
            for (String[] lugare : lugares) {
                location = new Location();
                location.setNumber_id(lugare[0]);
                location.setName(lugare[1]);
                location.setUser(ius.findByName("Usuario inicial"));
                ils.save(location);
            }
        }
        if (itds.findByName("Computador de mesa") == null) {
            TypeDevice type = new TypeDevice();
            type.setName("Computador de mesa");
            type.setDescription("Computador sobre escritorio con pantalla, teclado, mouse y torre");
            type.setUser(ius.findByName("Usuario inicial"));
            itds.save(type);
        }
        if (itds.findByName("Portatil") == null) {
            TypeDevice type = new TypeDevice();
            type.setName("Portatil");
            type.setDescription("Descripción Portatil");
            type.setUser(ius.findByName("Usuario inicial"));
            itds.save(type);
        }
        if (itds.findByName("Impresora HP") == null) {
            TypeDevice type = new TypeDevice();
            type.setName("Impresora HP");
            type.setDescription("Descripción Impresora HP");
            type.setUser(ius.findByName("Usuario inicial"));
            itds.save(type);
        }
        if (itds.findByName("Impresora LASER") == null) {
            TypeDevice type = new TypeDevice();
            type.setName("Impresora LASER");
            type.setDescription("Descripción Impresora LASER");
            type.setUser(ius.findByName("Usuario inicial"));
            itds.save(type);
        }
        if (itds.findByName("TELEVISOR LG") == null) {
            TypeDevice type = new TypeDevice();
            type.setName("TELEVISOR LG");
            type.setDescription("Descripción TELEVISOR LG");
            type.setUser(ius.findByName("Usuario inicial"));
            itds.save(type);
        }
        System.out.println("Success Start!");
    }

    public List<MigratedDevice> excelData(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<MigratedDevice> list_devices = new ArrayList<MigratedDevice>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                MigratedDevice device = new MigratedDevice();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    currentCell.setCellType(CellType.STRING);
                    String value = currentCell.getStringCellValue();
                    switch (cellIdx) {
                        case 0:
                            device.setInvPlate(value);
                            break;

                        case 1:
                            device.setTypeDevice(itds.findByName(value));
                            break;

                        case 2:
                            device.setAgencie(ias.findByName(value));
                            break;

                        case 3:
                            device.setLocation(ils.findByName(value));
                            break;

                        case 4:
                            device.setNewIP(value);
                            break;

                        case 5: // user responsable
                            device.setUser(ius.findByName(value));
                            break;

                        case 6:
                            device.setClassRoom(Integer.parseInt(value));
                            break;

                        case 7:
                            device.setDescription(value);
                            break;

                        case 8:
                            device.setOldIP(value);
                            break;

                        case 9:
                            device.setSwitchIP(value);
                            break;

                        case 10:
                            device.setPort(value);
                            break;

                        case 11:
                            device.setMAC(value);
                            break;

                        case 12:
                            device.setStandarKey(value);
                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }

                list_devices.add(device);
            }

            workbook.close();

            return list_devices;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

}
