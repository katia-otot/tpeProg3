package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CSVReader {

	public CSVReader() {
	}
	
	public List<Tarea> readTasks(String taskPath) {
		
		// Obtengo una lista con las lineas del archivo
		// lines.get(0) tiene la primer linea del archivo
		// lines.get(1) tiene la segunda linea del archivo... y así
		ArrayList<String[]> lines = this.readContent(taskPath);
		 // Lista para almacenar las tareas leídas
        List<Tarea> tareas = new ArrayList<>();
		for (String[] line: lines) {
			// Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
			String id = line[0].trim();
			String nombre = line[1].trim();
			Integer tiempo = Integer.parseInt(line[2].trim());
			Boolean critica = Boolean.parseBoolean(line[3].trim());
			Integer prioridad = Integer.parseInt(line[4].trim());
			// Aca instanciar lo que necesiten en base a los datos leidos
		
            // Instanciar la tarea con los datos leídos
            Tarea tarea = new Tarea(id, nombre, tiempo, critica, prioridad);
            // Agregar la tarea a la lista
            tareas.add(tarea);
        }
		 // Devolver la lista de tareas
         return tareas;
	}
	
	public List<Procesador> readProcessors(String processorPath) {
        List<Procesador> procesadores = new ArrayList<>();
        ArrayList<String[]> lines = this.readContent(processorPath);

        for (String[] line: lines) {
            String id = line[0].trim();
            String codigo = line[1].trim();
            boolean refrigerado = Boolean.parseBoolean(line[2].trim());
            int anio = Integer.parseInt(line[3].trim());

            // Crear un objeto Procesador y agregarlo a la lista
            Procesador procesador = new Procesador(id, codigo, refrigerado, anio);
            procesadores.add(procesador);
        }

        return procesadores;
    }

	private ArrayList<String[]> readContent(String path) {
		ArrayList<String[]> lines = new ArrayList<String[]>();

		File file = new File(path);
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				line = line.trim();
				lines.add(line.split(";"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (bufferedReader != null)
				try {
					bufferedReader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
		
		return lines;
	}
	
}