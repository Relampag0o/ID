import java.util.Date;

public class Product {
    private String name;
    private int Price;
    private int stock;
    private double weight;

    private Date expirationDate;

    private String color;
    private String material;
    private int modelNumber;

    public Product(String name, int price, int stock, double weight, Date expirationDate, String color, String material, int modelNumber) {
        this.name = name;
        Price = price;
        this.stock = stock;
        this.weight = weight;
        this.expirationDate = expirationDate;
        this.color = color;
        this.material = material;
        this.modelNumber = modelNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(int modelNumber) {
        this.modelNumber = modelNumber;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", Price=" + Price +
                ", stock=" + stock +
                ", weight=" + weight +
                ", expirationDate=" + expirationDate +
                ", color='" + color + '\'' +
                ", material='" + material + '\'' +
                ", modelNumber=" + modelNumber +
                '}';
    }
}
