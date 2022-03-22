import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        final String url = "jdbc:mysql://localhost:3306/myOrg";
        final String username = "root";
        final String password = "abhiraj2k2000";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected");
            try (Statement st = connection.createStatement();) {
                int modified;
                String addEmail = "ALTER TABLE worker ADD EMAIL VARCHAR(150)";
                modified = st.executeUpdate(addEmail);
                System.out.println("Modified " + modified + " row/s");

                String updateEmail = """
                        UPDATE worker
                        SET EMAIL = CONCAT(
                            LOWER(worker.FIRST_NAME),
                            LOWER(worker.LAST_NAME),
                            '@gmail.com'
                            )
                        WHERE WORKER_ID = ?""";
                ResultSet rs = st.executeQuery("SELECT WORKER_ID FROM worker");
                try (PreparedStatement ps = connection.prepareStatement(updateEmail);) {

                    while (rs.next()) {
                        ps.setInt(1, rs.getInt("WORKER_ID"));
                        modified = ps.executeUpdate();
                        System.out.println("Modified" + modified);
                    }
                }

                rs = st.executeQuery("SELECT WORKER_ID FROM worker");
                String replaceEmail = """
                        UPDATE worker
                        SET EMAIL = REPLACE(worker.EMAIL, '.com', '.in')
                        WHERE WORKER_ID = """;

                while (rs.next()) {
                    st.addBatch(replaceEmail + rs.getInt("WORKER_ID"));
                }

                int[] recordsAffected = st.executeBatch();
                System.out.println("Modified " + recordsAffected.length + " rows");

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
