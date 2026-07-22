Scanner leer = new Scanner(System.in);
Random random = new Random();

public String ANSI_RESET = "\u001B[0m";
public String ANSI_BLANCO = "\u001B[37m";
public String ANSI_VERDE = "\u001B[32m";
public String ANSI_NEGRO = "\u001B[30m";
public String ANSI_ROJO = "\u001B[31m";
public String ANSI_AZUL = "\u001B[34m";

void main() {


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
                plagasPendientes--;
            }
        }
        ////////////////////////////////////////////////////////////////////

    } catch (Exception e) {
        System.out.println("Error al intentar cargar los datos");
    }
    return campo;
}

private void menu(String[][] campo) {
    while(true){
        System.out.println("***Menu***");
        System.out.println("1. Mover drone");
        System.out.println("2. Ver campo");
        int opcion = leer.nextInt();
        switch(opcion){
            case 1:
                moverDrone(campo);
                break;
                case 2:
                    mostrarCampo(campo);
                    break;
                    default:
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

            System.out.print(color + valor + " ");

        }
        System.out.println();
    }
}

private void moverDrone(String[][] campo) {
}

