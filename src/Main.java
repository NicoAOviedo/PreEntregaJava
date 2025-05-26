import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Producto> productos = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Listar Productos");
            System.out.println("3. Buscar/Actualizar Producto");
            System.out.println("4. Eliminar Producto");
            System.out.println("5. Crear Pedido");
            System.out.println("6. Listar Pedidos");
            System.out.println("7. Salir");
            System.out.print("Selecciona una opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> agregarProducto();
                case "2" -> listarProductos();
                case "3" -> buscarActualizarProducto();
                case "4" -> eliminarProducto();
                case "5" -> crearPedido();
                case "6" -> listarPedidos();
                case "7" -> salir = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void agregarProducto() {
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Precio: ");
            double precio = Double.parseDouble(scanner.nextLine());
            System.out.print("Stock: ");
            int stock = Integer.parseInt(scanner.nextLine());

            productos.add(new Producto(nombre, precio, stock));
            System.out.println("Producto agregado correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: ingrese valores numéricos válidos.");
        }
    }

    private static void listarProductos() {
        if (productos.isEmpty()) {
            System.out.println("No hay productos.");
            return;
        }
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    private static void buscarActualizarProducto() {
        System.out.print("Ingrese ID del producto: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Producto p = buscarProductoPorId(id);
            if (p != null) {
                System.out.println("Producto encontrado: " + p);
                System.out.print("Nuevo stock: ");
                int nuevoStock = Integer.parseInt(scanner.nextLine());
                p.setStock(nuevoStock);
                System.out.println("Stock actualizado.");
            } else {
                System.out.println("Producto no encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private static void eliminarProducto() {
        System.out.print("ID del producto a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Producto p = buscarProductoPorId(id);
            if (p != null) {
                productos.remove(p);
                System.out.println("Producto eliminado.");
            } else {
                System.out.println("Producto no encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private static void crearPedido() {
        Pedido pedido = new Pedido();
        System.out.print("¿Cuántos productos va a agregar? ");
        try {
            int cantidad = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < cantidad; i++) {
                System.out.print("ID del producto: ");
                int id = Integer.parseInt(scanner.nextLine());
                Producto p = buscarProductoPorId(id);
                if (p == null) {
                    System.out.println("Producto no encontrado.");
                    i--;
                    continue;
                }
                System.out.print("Cantidad: ");
                int cant = Integer.parseInt(scanner.nextLine());
                if (p.getStock() < cant) {
                    throw new StockInsuficienteException("No hay suficiente stock para " + p.getNombre());
                }
                p.setStock(p.getStock() - cant);
                pedido.agregarLinea(new LineaPedido(p, cant));
            }
            pedidos.add(pedido);
            System.out.println("Pedido creado con éxito.");
        } catch (NumberFormatException e) {
            System.out.println("Error: se esperaba un número.");
        } catch (StockInsuficienteException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos.");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println(p);
            System.out.println();
        }
    }

    private static Producto buscarProductoPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) return p;
        }
        return null;
    }
}