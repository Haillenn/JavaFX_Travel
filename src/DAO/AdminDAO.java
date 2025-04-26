package DAO;

import model.Tour;
import model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
	//Lấy thông tin tour
     public static List<Tour> getAllTours() {
        List<Tour> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Tour";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
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

     //Thêm tour
    public static void addTour(String name, String time, String price, String description, String image) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Tour(name, time, price, description, image) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, time);
            stmt.setString(3, price);
            stmt.setString(4, description);
            stmt.setString(5, image);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Sửa tour
    public static void updateTour(int id, String name, String time, String price, String description, String image) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE Tour SET name=?, time=?, price=?, description=?, image=? WHERE tourID=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, time);
            stmt.setString(3, price);
            stmt.setString(4, description);
            stmt.setString(5, image);
            stmt.setInt(6, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // xóa tour
    public static void deleteTour(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM Tour WHERE tourID=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Lấy thông tin đặt tour
    public static List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
        	String sql = "SELECT * FROM Booking WHERE status = 'Chờ xác nhận'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking b = new Booking(
                    rs.getInt("bookingID"),
                    rs.getString("tourName"),
                    rs.getDate("bookingDate").toString(),
                    rs.getString("userEmail"),
                    rs.getInt("people"),
                    rs.getString("note"),
                    rs.getString("status")
                );
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //Hàm thay đổi trạng thái đặt tour
    public static void updateBookingStatus(int bookingId, String newStatus) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE Booking SET status=? WHERE bookingID=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newStatus);
            stmt.setInt(2, bookingId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}