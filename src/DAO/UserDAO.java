package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class UserDAO {

	//hàm kiểm tra đăng nhập
    public static boolean checkLogin(String email, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //Hàm kiểm tra quyền theo email
    public static String getRoleByEmailAndPassword(String email, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT role FROM User WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    //Hàm kiểm tra email trùng
    public static boolean isEmailExists(String email) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM User WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return true; 
        }
    }

    //Hầm đăng ký người dùng mới
    public static boolean registerUser(String name, String email, String phone, String password,
                                       String gender, LocalDate date) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO User (name, email, phone, password, gender, date, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, password);
            stmt.setString(5, gender);
            stmt.setDate(6, java.sql.Date.valueOf(date));
            stmt.setString(7, "user"); 
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
