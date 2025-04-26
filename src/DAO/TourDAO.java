package DAO;

import model.Tour;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TourDAO {

	//Lấy thông tin tour
    public static List<Tour> getAllTours() {
        List<Tour> list = new ArrayList<>();
        String sql = "SELECT * FROM Tour";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Tour t = new Tour(
                        rs.getInt("tourID"),
                        rs.getString("name"),
                        rs.getString("time"),
                        rs.getString("description"),
                        rs.getString("price"),
                        rs.getString("image")
                );
                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
