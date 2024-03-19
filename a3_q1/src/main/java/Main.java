import java.sql.*;

public class Main {
    public static void main(String [] args) {
        String url = "jdbc:postgresql://localhost:5432/students";
        String user = "postgres";
        String password = "admin";

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            addStudent(connection, "Aurelie", "Ng", "aurelie.ng@example.com", Date.valueOf("2023-09-03"));
            getAllStudents(connection);
            updateStudentEmail(connection, 5, "aurelie.ng@domain.com");
            deleteStudent(connection, 5);
        }
        catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e);
        }
    }

    public static void getAllStudents(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("student_id") + ", " +
                    resultSet.getString("first_name") + ", " +
                    resultSet.getString("last_name") + ", " +
                    resultSet.getString("email") + ", " +
                    resultSet.getDate("enrollment_date"));
        }
    }

    public static void addStudent(Connection connection, String first_name, String last_name, String email, Date enrollment_date) throws SQLException {
        String SQL = "INSERT INTO students(first_name, last_name, email, enrollment_date) VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, first_name);
        preparedStatement.setString(2, last_name);
        preparedStatement.setString(3, email);
        preparedStatement.setDate(4, enrollment_date);
        preparedStatement.executeUpdate();
    }

    public static void updateStudentEmail(Connection connection, int student_id, String new_email) throws SQLException {
        String SQL = "UPDATE students SET email = ? WHERE student_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(1, new_email);
        preparedStatement.setInt(2, student_id);
        preparedStatement.executeUpdate();
    }

    public static void deleteStudent(Connection connection,int student_id) throws SQLException {
        String SQL = "DELETE FROM students WHERE student_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setInt(1, student_id);
        preparedStatement.executeUpdate();
    }
}




