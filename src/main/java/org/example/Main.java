package org.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private Map<String, Producto> inventario;

    public Main() {
        inventario = new HashMap<>();
    }

    public void agregarProducto(String nombre, double precio, String informacionAdicional) {
        if (!existeProducto(nombre.toLowerCase())) {
            Producto producto = new Producto(nombre, precio, informacionAdicional);
            inventario.put(nombre.toLowerCase(), producto);
            guardarProductoEnCSV(producto);
            System.out.println("Producto agregado con éxito al inventario.");
        } else {
            System.out.println("Error: Ya existe un producto con el mismo nombre en el inventario.");
        }
    }

    public boolean existeProducto(String nombre) {
        return inventario.containsKey(nombre);
    }

    public void mostrarInventario() {
        System.out.println("Inventario de la frutería:");
        for (Map.Entry<String, Producto> entry : inventario.entrySet()) {
            System.out.println("Nombre: " + entry.getKey() + ", Precio: $" + entry.getValue().precio + ", Información Adicional: " + entry.getValue().informacionAdicional);
        }
    }

    public void menuIngresoProducto(Scanner scanner) {
        System.out.println("Ingrese el nombre del producto:");
        String nombre = scanner.nextLine();
        double precio = obtenerPrecioValido(scanner);
        System.out.println("Ingrese información adicional del producto:");
        String informacionAdicional = scanner.nextLine();

        agregarProducto(nombre, precio, informacionAdicional);
    }

    public double obtenerPrecioValido(Scanner scanner) {
        double precio;
        while (true) {
            System.out.println("Ingrese el precio del producto:");
            try {
                precio = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número válido como precio.");
            }
        }
        return precio;
    }

    public void guardarProductoEnCSV(Producto producto) {
        try (FileWriter writer = new FileWriter("productos.csv", true)) {
            writer.append(producto.nombre)
                    .append(",")
                    .append(String.valueOf(producto.precio))
                    .append(",")
                    .append(producto.informacionAdicional)
                    .append("\n");
        } catch (IOException e) {
            System.out.println("Error al guardar el producto en el archivo CSV.");
        }
    }

    public void cargarInventarioDesdeCSV() {
        String archivoCSV = "productos.csv";
        String linea;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivoCSV))) {
            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length == 3) {
                    String nombre = campos[0];
                    double precio = Double.parseDouble(campos[1]);
                    String informacionAdicional = campos[2];
                    inventario.put(nombre.toLowerCase(), new Producto(nombre, precio, informacionAdicional));
                }
            }
            System.out.println("Inventario cargado desde el archivo CSV.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar el inventario desde el archivo CSV.");
        }
    }

    class Producto {
        String nombre;
        double precio;
        String informacionAdicional;

        public Producto(String nombre, double precio, String informacionAdicional) {
            this.nombre = nombre.toLowerCase();
            this.precio = precio;
            this.informacionAdicional = informacionAdicional;
        }
    }

    public static void main(String[] args) {
        Main admin = new Main();
        Scanner scanner = new Scanner(System.in);
        admin.cargarInventarioDesdeCSV();

        int opcion;

        do {
            System.out.println("----- Menú de la Frutería -----");
            System.out.println("1. Mostrar inventario");
            System.out.println("2. Agregar producto");
            System.out.println("3. Salir");
            System.out.println("Seleccione una opción:");

            opcion = obtenerOpcionValida(scanner);

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
        } while (opcion != 3);

        scanner.close();
    }

    public static int obtenerOpcionValida(Scanner scanner) {
        int opcion;
        while (true) {
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                if (opcion >= 1 && opcion <= 3) {
                    break;
                } else {
                    System.out.println("Error: Por favor, ingrese una opción válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número válido como opción.");
            }
        }
        return opcion;
    }
}
