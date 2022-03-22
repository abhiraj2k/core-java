package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
import java.util.List;

import model.Worker;
import util.DatabaseConnection;

public class WorkerDAOImplementation implements WorkerDAO {
    Connection connection;

    public WorkerDAOImplementation() throws SQLException, ClassNotFoundException {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public int add(Worker worker) throws SQLException {
        String sql = """
                INSERT INTO worker
                (WORKER_ID, FIRST_NAME, LAST_NAME, SALARY, JOINING_DATE, DEPARTMENT, EMAIL)
                VALUES (?,?,?,?,?,?,?)""";
        try (PreparedStatement ps = this.connection.prepareStatement(sql);) {
            ps.setInt(1, worker.getWorkerId());
            ps.setString(2, worker.getFirstName());
            ps.setString(3, worker.getLastName());
            ps.setString(4, worker.getSalary());
            ps.setObject(5, worker.getJoiningDate());
            ps.setString(6, worker.getDepartment());
            ps.setString(7, worker.getEmail());
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void delete(int workerId) throws SQLException {
        String sql = """
                DELETE FROM worker
                WHERE
                WORKER_ID = ? """;
        try (PreparedStatement ps = this.connection.prepareStatement(sql);) {
            ps.setInt(1, workerId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Worker getWorker(int workerId) throws SQLException {
        String sql = "SELECT * FROM worker WHERE WORKER_ID = ?";
        try (PreparedStatement ps = this.connection.prepareStatement(sql);) {
            ps.setInt(1, workerId);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                throw new SQLException("RECORD NOT FOUND");
            }
            // Date d = new SimpleDateFormat("dd/MM/yyyy").parse((String)
            // rs.getObject("JOINING_DATE"));

            // Not implemented Date
            rs.next();
            return this.getWorkerFromParams(rs.getInt("WORKER_ID"), rs.getString("FIRST_NAME"),
                    rs.getString("LAST_NAME"),
                    rs.getString("SALARY"), new Date(), rs.getString("DEPARTMENT"),
                    rs.getString("EMAIL"));

        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<Worker> getWorkers() throws SQLException {
        String sql = "SELECT * FROM worker";
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            List<Worker> workerList = new ArrayList();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                workerList.add(this.getWorkerFromParams(rs.getInt("WORKER_ID"), rs.getString("FIRST_NAME"),
                        rs.getString("LAST_NAME"),
                        rs.getString("SALARY"), new Date(), rs.getString("DEPARTMENT"),
                        rs.getString("EMAIL")));
            }
            return workerList;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void update(Worker emp) throws SQLException {
        String sql = """
                UPDATE worker
                SET
                FIRST_NAME = ?,
                LAST_NAME = ?,
                SALARY = ?,
                JOINING_DATE = ?,
                DEPARTMENT = ?,
                EMAIL = ?
                WHERE
                WORKER_ID = ?""";

        try (PreparedStatement ps = this.connection.prepareStatement(sql);) {

            ps.setString(1, emp.getFirstName());
            ps.setString(2, emp.getLastName());
            ps.setString(3, emp.getSalary());
            ps.setObject(4, new Date());
            ps.setString(5, emp.getDepartment());
            ps.setString(6, emp.getEmail());
            ps.setInt(7, emp.getWorkerId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }
    }

    private Worker getWorkerFromParams(int workerId, String firstName, String lastName, String salary,
            Date joiningDate, String department, String email) {
        return new Worker(workerId, firstName, lastName, salary, joiningDate, department, email);
    }

}
