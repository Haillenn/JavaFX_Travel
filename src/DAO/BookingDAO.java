package DAO;

import model.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

	//Hàm đặt tour
    public static boolean addBooking(Booking booking) {
        String sql = "INSERT INTO Booking(userEmail, tourName, bookingDate, people, note, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, booking.getUserEmail());
            stmt.setString(2, booking.getTourName());
            stmt.setString(3, booking.getBookingDate());
            stmt.setInt(4, booking.getPeople());
            stmt.setString(5, booking.getNote());
            stmt.setString(6, booking.getStatus()); // default: "Chờ xác nhận"

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Lấy thông tin đặt tour theo email user
    public static List<Booking> getBookingsByUser(String email) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM Booking WHERE userEmail=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Booking b = new Booking(
                        rs.getInt("bookingID"),
                        rs.getString("userEmail"),
                        rs.getString("tourName"),
                        rs.getString("bookingDate"),
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
    
    //hủy đặt tour
    public static boolean cancelBooking(int bookingID) {
        String sql = "UPDATE Booking SET status = 'Đã huỷ' WHERE bookingID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
