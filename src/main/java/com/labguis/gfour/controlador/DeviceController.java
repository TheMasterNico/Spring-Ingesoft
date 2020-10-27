package com.labguis.gfour.controlador;

import com.labguis.gfour.interfaceService.IAgencieService;
import com.labguis.gfour.interfaceService.IDeviceService;
import com.labguis.gfour.interfaceService.ILocationService;
import com.labguis.gfour.interfaceService.IUsuarioService;
import com.labguis.gfour.modelo.Agencie;
import com.labguis.gfour.modelo.Location;
import com.labguis.gfour.modelo.MigratedDevice;
import com.labguis.gfour.modelo.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Nicolas Castillo
 */
@Controller
@RequestMapping
public class DeviceController {
    
    @Autowired
    private IDeviceService ids;    
    @Autowired
    private IAgencieService ias;
    @Autowired
    private ILocationService ils;
    @Autowired
    private IUsuarioService ius;
    
    @GetMapping("/equipos")
    public String equipos(Model model) {
        List<MigratedDevice>devices =  ids.listar();
        model.addAttribute("datos", devices);
        return "equipos";
    }
    
    @GetMapping("/populate")
    public String populate(Model model) {
        if(ius.findByName("Servidor") == null) {
            User u = new User();
            u.setName("Servidor");
            u.setEmail("email@testing.com");
            u.setPassword("password");
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
        if(ias.findByName(names[0]) == null) {
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
        if(ils.findByName(lugares[0][1]) == null) {
            Location location;
            for (String[] lugare : lugares) {
                location = new Location();
                location.setNumber_id(lugare[0]);
                location.setName(lugare[1]);
                location.setUser(ius.findByName("Servidor"));
                ils.save(location);
            }
        }
//        MigratedDevice m = new MigratedDevice();
//        m.setAgencie(ias.findByName(names[0]));
//        m.setClass_room(211);
//        m.setDescription("Descripcion cortita");
//        m.setInv_plate("115599");
//        m.setLocation(2);
//        
//        ids.save(m);
        System.out.println("Se ingresaron los datos");
        return "index";
    }
    
}
