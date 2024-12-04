package model;


import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private double price;

    /**
     * Copy constructor. Initializes a new product using another product's details.
     *
     * @param other the product to copy
     * @throws NullPointerException if the other product is null
     */
    public Product(Product other) {
        if(other == null) throw new NullPointerException();

        this.id = other.id;
        this.name = other.name;
        this.price = other.price;
    }

    /**
     * Constructs a new product with the specified id, name, and price.
     *
     * @param id the product id
     * @param name the product name
     * @param price the product price
     * @throws NullPointerException if the id is negative
     * @throws IllegalArgumentException if the name is null or empty
     */
    public Product(int id, String name, double price) {
        if(id < 0) throw new NullPointerException();
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("Product name cannot be null or empty");

        this.id = id;
        this.name = name;
        this.price = price;
    }

    /**
     * Returns the product id.
     *
     * @return the product id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the product id.
     *
     * @param id the product id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the product name.
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     *
     * @param name the product name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the product price.
     *
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     *
     * @param price the product price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns a string representation of the product.
     *
     * @return a string representation of the product
     */
    @Override
    public String toString() {
        return "productID: " + id + '\n' +
                "name: " + name + '\n' +
                "price: " + price + '\n';
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return true if this object is the same as the o argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return id == product.id && Double.compare(price, product.price) == 0 && Objects.equals(name, product.name);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}


