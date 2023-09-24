package product;

import java.io.Serializable;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Vector;

import server.Response;
import server.database;

public class Product implements Serializable {
    private static final long serialVersionUID = 123123;
    
    public Integer Id;
    public String Name;
    public Integer Price;
    public Integer Quantity;
    public String Type;
    public Timestamp CreatedAt;
    public String RequestType;

    public Product() {
    }

    public static Response Routes(Object object) {
        Product product = (Product) object;
        Response response = new Response();
        response.message = "success";
        switch (product.RequestType) {
            case "create":
                try {
                    product.Create();
                } catch (Exception e) {
                    response.message = "error";
                    response.object = e;
                }
                break;
            case "list":
                try {
                    response.object = Product.List();
                } catch (Exception e) {
                    response.message = "error";
                    response.object = e;
                }
                break;
            case "update":
                try {
                    product.Update();
                } catch (Exception e) {
                    response.message = "error";
                    response.object = e;
                }
                break;
            case "delete":
                try {
                    product.Delete();
                } catch (Exception e) {
                    response.message = "error";
                    response.object = e;
                }
                break;
            default:
                response.message = "error";
                response.object = "wrong request";
        }
        return response;
    }

    public void Create() throws SQLException {
        Connection db = database.getConnection();

        PreparedStatement ps = db.prepareStatement("insert into product values (NULL, ?, ?, ?, ?, ?)");

        ps.setString(1, this.getName());
        ps.setInt(2, this.getPrice());
        ps.setInt(3, this.getQuantity());
        ps.setString(4, this.getType());
        ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

        ps.execute();
    }

    public static Vector<Product> List() throws SQLException {
        Connection db = database.getConnection();

        Statement statement = db.createStatement();

        ResultSet rs = statement.executeQuery("select * from product order by created_at DESC");

        Vector<Product> result = new Vector<Product>();

        while (rs.next()) {
            Product p = new Product();
            p.setId(rs.getInt(1));
            p.setName(rs.getString(2));
            p.setPrice(rs.getInt(3));
            p.setQuantity(rs.getInt(4));
            p.setType(rs.getString(5));
            p.setCreatedAt(rs.getTimestamp(6));

            result.add(p);
        }

        rs.close();

        return result;
    }

    public void Update() throws SQLException {
        Connection db = database.getConnection();

        PreparedStatement ps = db
                .prepareStatement("update product set name = ?, price = ?, quantity = ?, type = ? where id = ?");

        ps.setString(1, this.getName());
        ps.setInt(2, this.getPrice());
        ps.setInt(3, this.getQuantity());
        ps.setString(4, this.getType());
        ps.setInt(5, this.getId());

        ps.execute();
    }

    public void Delete() throws SQLException {
        Connection db = database.getConnection();

        PreparedStatement ps = db.prepareStatement("delete from product where id = ?");

        ps.setInt(1, this.getId());

        ps.execute();
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer Price) {
        this.Price = Price;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer Quantity) {
        this.Quantity = Quantity;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public Timestamp getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        CreatedAt = createdAt;
    }
}