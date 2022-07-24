package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.MySQLConnector;
import model.Sale;

public class SaleDAO {
    private final Connection connection;
    private String query;
    private PreparedStatement statement;
    private ResultSet result;
    private int insertedLines = 0;

    public SaleDAO() throws SQLException {
        connection = MySQLConnector.getConnection();
    }

    public boolean createSale(Sale sale) throws SQLException {
        query = "INSERT INTO Sale VALUES(?, ?, ?, ?)";
        statement = connection.prepareStatement(query);
        statement.setString(1, null);
        statement.setFloat(2, sale.getTotalCost());
        statement.setString(3, sale.getSellerUsername());
        statement.setString(4, sale.getDate());
        insertedLines = statement.executeUpdate();
        return(insertedLines != 0);
    }

    public Sale readSale(int id) throws SQLException {
        query = "SELECT * FROM Sale WHERE id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        result = statement.executeQuery();
        if(result.next()) {
            return new Sale(
                    result.getInt(1),
                    result.getFloat(2),
                    result.getString(3),
                    result.getString(4));
        }
        return null;
    }

    public boolean updateSale(Sale sale) throws SQLException {
        query = "UPDATE Sale SET total_cost = ?, seller_username = ?, date = ? WHERE id = ?";
        statement = connection.prepareStatement(query);
        statement.setFloat(1, sale.getTotalCost());
        statement.setString(2, sale.getSellerUsername());
        statement.setString(3, sale.getDate());
        statement.setInt(4, sale.getId());
        insertedLines = statement.executeUpdate();
        return(insertedLines != 0);
    }

    public boolean deleteSale(int id) throws SQLException {
        query = "DELETE FROM Sale WHERE id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        insertedLines = statement.executeUpdate();
        return(insertedLines != 0);
    }

    public String[][] getSalesTableData() throws SQLException {
        int rows = 0, columns = 4, aux = 0;
        ResultSet resultRows = connection.prepareStatement("SELECT COUNT(*) FROM Sale").executeQuery();
        if(resultRows.next()) rows = resultRows.getInt(1);

        query = "SELECT * FROM Sale";
        statement = connection.prepareStatement(query);
        result = statement.executeQuery();
        List<String> salesList = new ArrayList<>();
        while(result.next()){
            for(int i = 1; i <= columns; i++){
                salesList.add(result.getString(i));
            }
        }

        String[][] sales = new String[rows][columns];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                sales[i][j] = salesList.get(aux++);
            }
        }
        return sales;
    }

    public void close() throws SQLException {
        connection.close();
    }
}
