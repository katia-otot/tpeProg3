import utils.CSVReader;
import utils.Tarea;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {
    // Estructura interna para almacenar las tareas
    private HashMap<String, Tarea> tareasMap;
	
    /*
     * Complejidad temporal del constructor: O(n), donde n es la cantidad total de tareas procesadas
     */
	public Servicios(String pathProcesadores, String pathTareas) {
        tareasMap = new HashMap<>();
        CSVReader reader = new CSVReader();
        List<Tarea> tareas = reader.readTasks(pathTareas);
        
        for (Tarea tarea : tareas) {
            tareasMap.put(tarea.getId(), tarea);
        }
    }
	
	/*
     * Complejidad temporal del servicio 1: O(1)
     */
	public Tarea servicio1(String ID) {	
        return tareasMap.get(ID); // Devuelve la tarea por ID
    }
    
   /*
     * Complejidad temporal del servicio 2: O(n), donde n es el número de tareas
     */
	public List<Tarea> servicio2(boolean esCritica) {
        List<Tarea> result = new ArrayList<>();
        for (Tarea tarea : tareasMap.values()) {
            if (tarea.isCritica() == esCritica) {
                result.add(tarea);
            }
        }
        return result;
    }

    /*
     * Complejidad temporal del servicio 3: O(n), donde n es el número de tareas
     */
	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        List<Tarea> result = new ArrayList<>();
        for (Tarea tarea : tareasMap.values()) {
            if (tarea.getNivelPrioridad() >= prioridadInferior && tarea.getNivelPrioridad() <= prioridadSuperior) {
                result.add(tarea);
            }
        }
        return result;
    }
}


