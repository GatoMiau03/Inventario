package org.example;
import java.util.HashMap; // Este almacena las claves
import java.util.Map; //Lo utilizare para crear una coleccion de clave-valor para que cada elemento tenga un valor propio, en este caso son los productos
import java.util.Scanner;

public class Main {
    Map<String, Producto> inventario;

    public Main() {
        inventario = new HashMap<>();
    }

    public void agregarProducto(String nombre, double precio, String informacionAdicional) {
        Producto producto = new Producto(nombre, precio, informacionAdicional);
        inventario.put(nombre, producto);
    }

    public void mostrarInventario() {
        System.out.println("Inventario de la frutería:");
        for (Map.Entry<String, Producto> entry : inventario.entrySet()) {
            System.out.println("Nombre: " + entry.getKey() + ", Precio: $" + entry.getValue().precio + ", Información Adicional: " + entry.getValue().informacionAdicional);
        }
    }

    public void menuIngresoProducto(Scanner scanner) {
        try {
            System.out.println("Ingrese el nombre del producto:");
            String nombre = scanner.nextLine();
            double precio = obtenerPrecioValido(scanner);
            scanner.nextLine();
            System.out.println("Ingrese información adicional del producto:");
            String informacionAdicional = scanner.nextLine();

            agregarProducto(nombre, precio, informacionAdicional);
            System.out.println("Producto agregado con éxito al inventario.");
        } catch (NumberFormatException e) {
            System.out.println("Error. El precio debe ser un número válido.");
        }
    }
    public boolean esNumero(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public double obtenerPrecioValido(Scanner scanner) {
        double precio;
        while (true) {
            System.out.println("Ingrese el precio del producto:");
            String input = scanner.nextLine();
            if (esNumero(input)) {
                precio = Double.parseDouble(input);
                break;
            } else {
                System.out.println("Precio inválido. Por favor, ingrese un número válido.");
            }
        }
        return precio;
    }

    class Producto {
        String nombre;
        double precio;
        String informacionAdicional;

        public Producto(String nombre, double precio, String informacionAdicional) {
            this.nombre = nombre;
            this.precio = precio;
            this.informacionAdicional = informacionAdicional;
        }
    }

    public static void main(String[] args) {
        Main admin = new Main();
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("----- Menú de la Frutería -----");
            System.out.println("1. Mostrar inventario");
            System.out.println("2. Agregar producto");
            System.out.println("3. Salir");
            System.out.println("Seleccione una opción:");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        admin.mostrarInventario();
                        break;
                    case 2:
                        admin.menuIngresoProducto(scanner);
                        break;
                    case 3:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error. Debe ingresar un número válido como opción.");
                opcion = 0;
            }

        } while (opcion != 3);

        scanner.close();
    }
}
