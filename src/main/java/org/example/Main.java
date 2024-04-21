package org.example;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public void eliminarProducto(String nombre) {
        if (existeProducto(nombre.toLowerCase())) {
            inventario.remove(nombre.toLowerCase());
            System.out.println("Producto eliminado con éxito del inventario.");
        } else {
            System.out.println("Error: No existe un producto con ese nombre en el inventario.");
        }
    }

    public boolean existeProducto(String nombre) {
        return inventario.containsKey(nombre);
    }

    public void mostrarInventario() {
        System.out.println("Inventario de la frutería:");
        for (Map.Entry<String, Producto> entry : inventario.entrySet()) {
            System.out.println("Nombre: " + entry.getKey() + ", Precio: $" + entry.getValue().precio + ", Información Adicional: " + entry.getValue().informacionAdicional + ", Fecha de ingreso: " + entry.getValue().fechaIngreso);
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
                    .append(",")
                    .append(producto.fechaIngreso)
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
                if (campos.length == 4) {
                    String nombre = campos[0];
                    double precio = Double.parseDouble(campos[1]);
                    String informacionAdicional = campos[2];
                    String fechaIngreso = campos[3];
                    inventario.put(nombre.toLowerCase(), new Producto(nombre, precio, informacionAdicional, fechaIngreso));
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
        String fechaIngreso;

        public Producto(String nombre, double precio, String informacionAdicional) {
            this.nombre = nombre.toLowerCase();
            this.precio = precio;
            this.informacionAdicional = informacionAdicional;
            this.fechaIngreso = obtenerFechaActual();
        }

        public Producto(String nombre, double precio, String informacionAdicional, String fechaIngreso) {
            this.nombre = nombre.toLowerCase();
            this.precio = precio;
            this.informacionAdicional = informacionAdicional;
            this.fechaIngreso = fechaIngreso;
        }

        private String obtenerFechaActual() {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            return formatter.format(date);
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
            System.out.println("3. Eliminar producto");
            System.out.println("4. Salir");
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
                    System.out.println("Ingrese el nombre del producto a eliminar:");
                    String nombreProductoEliminar = scanner.nextLine();
                    admin.eliminarProducto(nombreProductoEliminar);
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
            }
        } while (opcion != 4);

        scanner.close();
    }

    public static int obtenerOpcionValida(Scanner scanner) {
        int opcion;
        while (true) {
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                if (opcion >= 1 && opcion <= 4) {
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
