package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.MySQLConnector;
import model.Product;

public class ProductDAO {
    private final Connection connection;
    private String query;
    private PreparedStatement statement;
    private int insertedLines = 0;

    public ProductDAO() throws SQLException {
        connection = MySQLConnector.getConnection();
    }

    public boolean createProduct(Product product) throws SQLException {
        query = "INSERT INTO Product VALUES(?, ?, ?, ?)";
        statement = connection.prepareStatement(query);
        statement.setString(1, null);
        statement.setString(2, product.getName());
        statement.setString(3, product.getCategory());
        statement.setString(4, product.getPrice());
        insertedLines = statement.executeUpdate();
        return(insertedLines != 0);
    }

    public String[][] readProductsTableData() throws SQLException {
        int rows = 0, columns = 4, aux = 0;
        ResultSet resultRows = connection.prepareStatement("SELECT COUNT(*) FROM Product").executeQuery();
        if(resultRows.next()) rows = resultRows.getInt(1);

        query = "SELECT * FROM Product";
        statement = connection.prepareStatement(query);
        ResultSet result = statement.executeQuery();
        List<String> productsList = new ArrayList<>();
        while(result.next()){
            for(int i = 1; i <= columns; i++){
                productsList.add(result.getString(i));
            }
        }

        String[][] products = new String[rows][columns];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                products[i][j] = productsList.get(aux++);
            }
        }
        return products;
    }

    public boolean updateProduct(Product product) throws SQLException {
        query = "UPDATE Product SET name = ?, category = ?, price = ? WHERE id = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, product.getName());
        statement.setString(2, product.getCategory());
        statement.setString(3, product.getPrice());
        statement.setInt(4, product.getId());
        insertedLines = statement.executeUpdate();
        return(insertedLines != 0);
    }

    public boolean deleteProduct(int id) throws SQLException {
        query = "DELETE FROM Product WHERE id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        insertedLines = statement.executeUpdate();
        return(insertedLines != 0);
    }

    public void close() throws SQLException {
        connection.close();
    }
}
