public class Producto {
    private static int contador = 1;
    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(String nombre, double precio, int stock) {
        this.id = contador++;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    public void setNombre(String nombre) {this.nombre = nombre; }
    public void setPrecio(double precio) {this.precio = precio; }
    public void setStock(int stock) { this.stock = stock; }


    public String toString() {
        return String.format("ID: %d | Nombre: %s | Precio: %.2f | Stock: %d", id, nombre, precio, stock);
    }
}
