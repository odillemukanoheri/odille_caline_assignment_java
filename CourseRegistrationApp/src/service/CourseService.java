package service;

import db.DBConnection;
import model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourseService {

    // ===== ADD COURSE =====
    public void addCourse(Course c) {
        String sql = "INSERT INTO course(course_name, course_code, credit) VALUES(?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getCourseName());
            ps.setString(2, c.getCourseCode());
            ps.setInt(3, c.getCredit());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== GET ALL COURSES =====
    public List<Course> getAllCourses() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM course";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getString("course_code"),
                        rs.getInt("credit")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== UPDATE COURSE =====
    public void updateCourse(Course c) {
        String sql = "UPDATE course SET course_name=?, course_code=?, credit=? WHERE course_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getCourseName());
            ps.setString(2, c.getCourseCode());
            ps.setInt(3, c.getCredit());
            ps.setInt(4, c.getCourseId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== DELETE COURSE =====
    public void deleteCourse(int id) {
        String sql = "DELETE FROM course WHERE course_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}