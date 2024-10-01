
import java.util.List;

import utils.Tarea;

public class Main {
    public static void main(String[] args) {
        // Crear una instancia de Servicios con los archivos CSV como entrada
        Servicios servicios = new Servicios("datasets/Procesadores.csv", "datasets/Tareas.csv");

        // Prueba Servicio 1: Buscar tarea por ID
        System.out.println("=== Servicio 1: Buscar Tarea por ID ===");
        Tarea tareaBuscada = servicios.servicio1("T1");
        if (tareaBuscada != null) {
            System.out.println("Tarea encontrada: " + tareaBuscada);
        } else {
            System.out.println("No se encontró la tarea con ID T1.");
        }

        // Prueba Servicio 2: Listar tareas críticas
        System.out.println("=== Servicio 2: Listar Tareas Críticas ===");
        List<Tarea> tareasCriticas = servicios.servicio2(true); // true para críticas
        for (Tarea t : tareasCriticas) {
            System.out.println(t);
        }

        // Prueba Servicio 2: Listar tareas no críticas
        System.out.println("=== Servicio 2: Listar Tareas No Críticas ===");
        List<Tarea> tareasNoCriticas = servicios.servicio2(false); // false para no críticas
        for (Tarea t : tareasNoCriticas) {
            System.out.println(t);
        }

        // Prueba Servicio 3: Tareas entre dos niveles de prioridad
        System.out.println("=== Servicio 3: Tareas entre Prioridades 30 y 90 ===");
        List<Tarea> tareasPrioridad = servicios.servicio3(30, 90);
        for (Tarea t : tareasPrioridad) {
            System.out.println(t);
        }
    }
}