
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import utils.Tarea;
import utils.Procesador;

public class AsignadorTareas {

    private List<Tarea> tareas;
    private List<Procesador> procesadores;
    private int maxTiempoSinRefrigeracion;

    public AsignadorTareas(List<Tarea> tareas, List<Procesador> procesadores, int maxTiempoSinRefrigeracion) {
        this.tareas = tareas;
        this.procesadores = procesadores;
        this.maxTiempoSinRefrigeracion = maxTiempoSinRefrigeracion;
    }

    // Algoritmo de Backtracking
    public Solucion backtracking() {
        List<Estado> solucion = new ArrayList<>();
        Solucion resultado = new Solucion();
        backtrack(0, solucion, resultado);
        return resultado;
    }

    private void backtrack(int tareaIdx, List<Estado> solucion, Solucion resultado) {
        if (tareaIdx == tareas.size()) {
            int maxTiempo = calcularTiempoMaximo(solucion);
            if (resultado.getTiempoMaximo() == -1 || maxTiempo < resultado.getTiempoMaximo()) {
                resultado.setSolucion(new ArrayList<>(solucion));
                resultado.setTiempoMaximo(maxTiempo);
            }
            return;
        }

        Tarea tareaActual = tareas.get(tareaIdx);
        for (Procesador procesador : procesadores) {
            if (esValidaAsignacion(procesador, tareaActual, solucion)) {
                solucion.add(new Estado(procesador, tareaActual));
                backtrack(tareaIdx + 1, solucion, resultado);
                solucion.remove(solucion.size() - 1);
            }
        }
    }

    private boolean esValidaAsignacion(Procesador procesador, Tarea tarea, List<Estado> solucion) {
        int tareasCriticas = 0;
        int tiempoEjecutado = 0;
    
        // Recorrer todas las asignaciones actuales para este procesador
        for (Estado e : solucion) {
            if (e.getProcesador().equals(procesador)) {
                tiempoEjecutado += e.getTarea().getTiempoEjecucion();
                if (e.getTarea().isCritica()) {
                    tareasCriticas++;
                }
            }
        }
    
        // Verificar si el procesador ya tiene dos tareas críticas asignadas
        if (tarea.isCritica() && tareasCriticas >= 2) return false;
        
        // Verificar si el procesador sin refrigeración supera el tiempo máximo permitido
        if (!procesador.isEstaRefrigerado() && (tiempoEjecutado + tarea.getTiempoEjecucion()) > maxTiempoSinRefrigeracion) {
            return false; // No asignar la tarea si se supera el límite
        }
    
        return true;
    }

    private int calcularTiempoMaximo(List<Estado> solucion) {
        HashMap<Procesador, Integer> tiempos = new HashMap<>();
        for (Estado e : solucion) {
            tiempos.put(e.getProcesador(), tiempos.getOrDefault(e.getProcesador(), 0) + e.getTarea().getTiempoEjecucion());
        }
        int max = 0;
        for (int tiempo : tiempos.values()) {
            if (tiempo > max) max = tiempo;
        }
        return max;
    }

    // Algoritmo Greedy
    public Solucion greedy() {
        List<Estado> solucion = new ArrayList<>();
        tareas.sort((t1, t2) -> Integer.compare(t2.getNivelPrioridad(), t1.getNivelPrioridad()));

        for (Tarea tarea : tareas) {
            Procesador mejorProcesador = null;
            int minTiempo = Integer.MAX_VALUE;

            for (Procesador procesador : procesadores) {
                int tiempoActual = calcularTiempoParaProcesador(procesador, solucion);
                if (esValidaAsignacion(procesador, tarea, solucion) && tiempoActual < minTiempo) {
                    minTiempo = tiempoActual;
                    mejorProcesador = procesador;
                }
            }
            if (mejorProcesador != null) {
                solucion.add(new Estado(mejorProcesador, tarea));
            }
        }

        int maxTiempo = calcularTiempoMaximo(solucion);
        return new Solucion(solucion, maxTiempo);
    }

    private int calcularTiempoParaProcesador(Procesador procesador, List<Estado> solucion) {
        int tiempo = 0;
        for (Estado e : solucion) {
            if (e.getProcesador().equals(procesador)) {
                tiempo += e.getTarea().getTiempoEjecucion();
            }
        }
        return tiempo;
    }

    // Clase auxiliar para representar la asignación de una tarea a un procesador
    public static class Estado {
        private Procesador procesador;
        private Tarea tarea;

        public Estado(Procesador procesador, Tarea tarea) {
            this.procesador = procesador;
            this.tarea = tarea;
        }

        public Procesador getProcesador() {
            return procesador;
        }

        public Tarea getTarea() {
            return tarea;
        }
    }

    // Clase para contener el resultado de la asignación
    public static class Solucion {
        private List<Estado> solucion;
        private int tiempoMaximo;

        public Solucion() {
            this.solucion = new ArrayList<>();
            this.tiempoMaximo = -1;
        }

        public Solucion(List<Estado> solucion, int tiempoMaximo) {
            this.solucion = solucion;
            this.tiempoMaximo = tiempoMaximo;
        }

        public List<Estado> getSolucion() {
            return solucion;
        }

        public int getTiempoMaximo() {
            return tiempoMaximo;
        }

        public void setSolucion(List<Estado> solucion) {
            this.solucion = solucion;
        }

        public void setTiempoMaximo(int tiempoMaximo) {
            this.tiempoMaximo = tiempoMaximo;
        }
    }
}
