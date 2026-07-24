import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {

    Scanner leer = new Scanner(System.in);
    Random random = new Random();

    public String ANSI_RESET = "\u001B[0m";
    public String ANSI_BLANCO = "\u001B[37m";
    public String ANSI_VERDE = "\u001B[32m";
    public String ANSI_NEGRO = "\u001B[30m";
    public String ANSI_ROJO = "\u001B[31m";
    public String ANSI_AZUL = "\u001B[34m";

    int filaDron = -1;
    int columnaDron = -1;
    String valorDebajoDron = "0";
    Set<String> celdasVisitadas = new HashSet<>();
    List<int[]> posicionesPlagas = new ArrayList<>();
    Set<String> plagasDetectadas = new HashSet<>();
    int totalCeldas = 0;

    public static void main(String[] args) {
        new Main().iniciar();
    }

    private void iniciar() {

        while (true) {
            try {
                System.out.println("Bienvenido!");
                System.out.println("Digite cuantas filas le gustaria que tenga su campo: ");
                int filas = leer.nextInt();
                System.out.println("Digite cuantas columnas le gustaria que tenga su campo: ");
                int columnas = leer.nextInt();

                String[][] campo = inicializarCampo(filas, columnas);
                menu(campo);

            } catch (Exception e) {
                System.out.println("Error al intentar cargar los datos");
                leer.nextLine();
            }
        }
    }

    private String[][] inicializarCampo(int filas, int columnas) {
        int espacios = filas * columnas;
        int espaciosDisponibles = espacios;
        int plagas = 0;
        int obstaculos = 0;
        int cultivos = 0;
        boolean continuar = false;
        String[][] campo = new String[filas][columnas];
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[i].length; j++) {
                campo[i][j] = "0";
            }
        }

        // Reiniciar el estado del dron cada vez que se genera un campo nuevo
        filaDron = -1;
        columnaDron = -1;
        valorDebajoDron = "0";
        celdasVisitadas = new HashSet<>();
        posicionesPlagas = new ArrayList<>();
        plagasDetectadas = new HashSet<>();
        totalCeldas = filas * columnas;

        //recolectar la cantidad de caracteristicas /////////////////////////////////////
        try {
            do {
                System.out.println("Digite cuantas celdas tendran cultivos, celdas disponibles: " + espaciosDisponibles);
                cultivos = leer.nextInt();
                if (cultivos <= espaciosDisponibles) {
                    espaciosDisponibles -= cultivos;
                    continuar = true;
                } else {
                    System.out.println("Error valor invalido");
                }

            } while (!continuar);
            continuar = false;

            do {
                System.out.println("Digite cuantas celdas tendran obstaculos, celdas disponibles: " + espaciosDisponibles);
                obstaculos = leer.nextInt();
                if (obstaculos <= espaciosDisponibles) {
                    espaciosDisponibles -= obstaculos;
                    continuar = true;
                } else {
                    System.out.println("Error valor invalido");
                }
            } while (!continuar);
            continuar = false;

            do {
                System.out.println("Digite cuantas celdas tendran plagas, celdas disponibles: " + espaciosDisponibles);
                plagas = leer.nextInt();
                if (plagas <= espaciosDisponibles) {
                    espaciosDisponibles -= plagas;
                    continuar = true;
                } else {
                    System.out.println("Error valor invalido");
                }
            } while (!continuar);

            //aplicar las caracteristicas/////////////////////////////////////

            int cultivosPendientes = cultivos;
            while (cultivosPendientes > 0) {
                int filaAleatoria = random.nextInt(filas);
                int columnaAleatoria = random.nextInt(columnas);

                if (campo[filaAleatoria][columnaAleatoria].equals("0")) {
                    campo[filaAleatoria][columnaAleatoria] = "C";
                    cultivosPendientes--;
                }
            }
            int obstaculosPendientes = obstaculos;
            while (obstaculosPendientes > 0) {
                int filaAleatoria = random.nextInt(filas);
                int columnaAleatoria = random.nextInt(columnas);

                if (campo[filaAleatoria][columnaAleatoria].equals("0")) {
                    campo[filaAleatoria][columnaAleatoria] = "O";
                    obstaculosPendientes--;
                }
            }

            int plagasPendientes = plagas;
            while (plagasPendientes > 0) {
                int filaAleatoria = random.nextInt(filas);
                int columnaAleatoria = random.nextInt(columnas);

                if (campo[filaAleatoria][columnaAleatoria].equals("0")) {
                    campo[filaAleatoria][columnaAleatoria] = "P";
                    posicionesPlagas.add(new int[]{filaAleatoria, columnaAleatoria});
                    plagasPendientes--;
                }
            }
            ////////////////////////////////////////////////////////////////////

            // totalCeldas debe contar solo las celdas transitables: los
            // obstaculos nunca se marcan como visitados, asi que no cuentan.
            totalCeldas = (filas * columnas) - obstaculos;

        } catch (Exception e) {
            System.out.println("Error al intentar cargar los datos");
        }
        return campo;
    }

    private void menu(String[][] campo) {
        while (true) {
            System.out.println("***Menu***");
            System.out.println("1. Mover drone");
            System.out.println("2. Ver campo");
            System.out.println("3. Volver a generar un campo");
            try {
                int opcion = leer.nextInt();
                switch (opcion) {
                    case 1:
                        System.out.println("Cuantos movimientos maximos puede dar el dron en este recorrido? ");
                        int movimientosMaximos = leer.nextInt();
                        moverDrone(campo, movimientosMaximos);
                        break;
                    case 2:
                        mostrarCampo(campo);
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Error, opcion invalida");
                }
            } catch (Exception e) {
                System.out.println("Error, opcion invalida");
                leer.nextLine();
            }
        }
    }

    private void mostrarCampo(String[][] campo) {
        for (int f = 0; f < campo.length; f++) {
            for (int c = 0; c < campo[f].length; c++) {
                String valor = campo[f][c];
                String color = ANSI_RESET;

                switch (valor) {
                    case "0":
                        color = ANSI_BLANCO;
                        break;
                    case "C":
                        color = ANSI_VERDE;
                        break;
                    case "O":
                        color = ANSI_NEGRO;
                        break;
                    case "P":
                        color = ANSI_ROJO;
                        break;
                    case "D":
                        color = ANSI_AZUL;
                        break;
                }

                System.out.print(color + valor + ANSI_RESET + " ");

            }
            System.out.println();
        }
    }

    private void moverDrone(String[][] campo, int movimientosMaximos) {
        int filas = campo.length;
        int columnas = campo[0].length;

        if (celdasVisitadas.size() >= totalCeldas) {
            System.out.println("El dron ya recorrio todo el campo");
            return;
        }

        if (filaDron == -1) {
            boolean valido = false;
            while (!valido) {
                try {
                    System.out.println("Digite la fila inicial del dron (0 a " + (filas - 1) + "): ");
                    int f = leer.nextInt();
                    System.out.println("Digite la columna inicial del dron (0 a " + (columnas - 1) + "): ");
                    int c = leer.nextInt();

                    if (f < 0 || f >= filas || c < 0 || c >= columnas) {
                        System.out.println("Esa posicion no existe en la matriz.");
                    } else if (campo[f][c].equals("O")) {
                        System.out.println("No se puede iniciar sobre un obstaculo.");
                    } else {
                        filaDron = f;
                        columnaDron = c;
                        valido = true;
                    }
                } catch (Exception e) {
                    System.out.println("Error!!");
                    leer.nextLine();
                }
            }

            // Fallo 1
            boolean arriba = filaDron - 1 < 0 || campo[filaDron - 1][columnaDron].equals("O");
            boolean abajo = filaDron + 1 >= filas || campo[filaDron + 1][columnaDron].equals("O");
            boolean izquierda = columnaDron - 1 < 0 || campo[filaDron][columnaDron - 1].equals("O");
            boolean derecha = columnaDron + 1 >= columnas || campo[filaDron][columnaDron + 1].equals("O");
            if (arriba && abajo && izquierda && derecha) {
                System.out.println("FALLO: el dron quedo completamente rodeado de obstaculos/bordes en su posicion inicial y no puede avanzar.");
                return;
            }

            valorDebajoDron = campo[filaDron][columnaDron];
            campo[filaDron][columnaDron] = "D";
            celdasVisitadas.add(filaDron + "," + columnaDron);
            if (valorDebajoDron.equals("P")) {
                plagasDetectadas.add(filaDron + "," + columnaDron);
            }
        }

        // Recorrido
        List<int[]> ruta = new ArrayList<>();
        for (int f = 0; f < filas; f++) {
            if (f % 2 == 0) {
                for (int c = 0; c < columnas; c++) ruta.add(new int[]{f, c});
            } else {
                for (int c = columnas - 1; c >= 0; c--) ruta.add(new int[]{f, c});
            }
        }
        int indiceInicio = 0;
        for (int i = 0; i < ruta.size(); i++) {
            if (ruta.get(i)[0] == filaDron && ruta.get(i)[1] == columnaDron) {
                indiceInicio = i;
                break;
            }
        }

        System.out.println("   Inicio del recorrido    ");
        int paso;
        for (paso = 1; paso <= movimientosMaximos; paso++) {
            if (celdasVisitadas.size() >= totalCeldas) {
                System.out.println("El dron ya recorrio todo el campo");
                break;
            }
            int indiceActual = (indiceInicio + paso ) % ruta.size();
            int f = ruta.get(indiceActual)[0];
            int c = ruta.get(indiceActual)[1];
            if (campo[f][c].equals("O")) {
                System.out.println("Paso " + paso + ": obstaculo en (" + f + "," + c + "), el dron lo rodea.");
                continue;
            }
            campo[filaDron][columnaDron] = valorDebajoDron;
            String contenidoActual = campo[f][c];
            if (contenidoActual.equals("P")) {
                String clave = f + "," + c;
                if (!plagasDetectadas.contains(clave)) {
                    plagasDetectadas.add(clave);
                    System.out.println("Paso " + paso + ": plaga detectada en (" + f + "," + c + ")");
                }
            } else {
                System.out.println("Paso " + paso + ": el dron avanza a (" + f + "," + c + ")");
            }
            valorDebajoDron = contenidoActual;
            campo[f][c] = "D";
            filaDron = f;
            columnaDron = c;
            celdasVisitadas.add(f + "," + c);
        }
        System.out.println("    Resumen del recorrido    ");
        System.out.println("Plagas detectadas: " + plagasDetectadas.size());
        int sinDetectar = posicionesPlagas.size() - plagasDetectadas.size();
        System.out.println("Plagas sin detectar: " + sinDetectar);
        //Fallo 2
        if (celdasVisitadas.size() < totalCeldas) {
            System.out.println("FALLO: el dron no logro recorrer todo el campo. Se agotaron los movimientos disponibles antes de terminar.");
        } else {
            System.out.println("El dron logro recorrer todo el campo.");
        }
    }
}