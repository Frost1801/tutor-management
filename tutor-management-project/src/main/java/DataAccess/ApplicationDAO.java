package main.java.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import main.java.DataAccess.UsersDataAccess.CandidateDAO;
import main.java.DomainModel.Application;
import main.java.DomainModel.ApplicationResult;
import main.java.DomainModel.Users.Candidate;

public class ApplicationDAO implements DAO<Application> {
    CandidateDAO candidateDAO = new CandidateDAO();


    @Override
    public Application get(int id) throws SQLException {
        Connection connection = Database.getConnection();
        Application application = null;

        String sqlQueryText = "SELECT * FROM applications WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int candidateId = rs.getInt("candidate_id");
            Candidate applicant = candidateDAO.get(candidateId);
            String subject = rs.getString("subject");
            ApplicationResult result = ApplicationResult.valueOf(rs.getString("result"));

            application = new Application(id, applicant, subject);
            application.setResult(result);
        }

        ps.close();
        connection.close();

        return application;
    }

    @Override
    public List<Application> getAll() throws SQLException {
        Connection connection = Database.getConnection();
        List<Application> applications = new ArrayList<>();

        String sqlQueryText = "SELECT * FROM applications";
        ResultSet rs = connection.createStatement().executeQuery(sqlQueryText);

        while (rs.next()) {
            CandidateDAO candidateDAO = new CandidateDAO();
            int applicationId = rs.getInt("id");
            int candidateId = rs.getInt("candidate_id");
            Candidate applicant = candidateDAO.get(candidateId);
            String subject = rs.getString("subject");
            ApplicationResult result = ApplicationResult.valueOf(rs.getString("result"));

            Application application = new Application(applicationId, applicant, subject);
            application.setResult(result);

            applications.add(application);
        }

        connection.close();

        return applications;
    }

    @Override
    public int insert(Application application) throws SQLException {
        Connection connection = Database.getConnection();

        String sqlQueryText = "INSERT INTO applications (id, candidate_id, subject, result) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, application.getId());
        ps.setInt(2, application.getCandidate().getID());
        ps.setString(3, application.getSubject());
        ps.setString(4, application.getResult().name());

        int rowsAffected = ps.executeUpdate();

        ps.close();
        connection.close();

        if (rowsAffected == 0) {
            throw new SQLException("Insertion failed, no rows affected.");
        } else if (rowsAffected > 1) {
            throw new SQLException("Insertion failed, too many rows affected.");
        }

        return rowsAffected;
    }

    @Override
    public int update(Application application) throws SQLException {
        Connection connection = Database.getConnection();

        String sqlQueryText = "UPDATE applications SET candidate_id = ?, subject = ?, result = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, application.getCandidate().getID());
        ps.setString(2, application.getSubject());
        ps.setString(3, application.getResult().name());
        ps.setInt(4, application.getId());

        int rowsAffected = ps.executeUpdate();

        ps.close();
        connection.close();

        if (rowsAffected == 0) {
            throw new SQLException("Update failed, no rows affected.");
        } else if (rowsAffected > 1) {
            throw new SQLException("Update failed, too many rows affected.");
        }

        return rowsAffected;
    }

    @Override
    public int delete(int id) throws SQLException {
        Connection connection = Database.getConnection();

        String sqlQueryText = "DELETE FROM applications WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sqlQueryText);

        ps.setInt(1, id);

        int rowsAffected = ps.executeUpdate();

        ps.close();
        connection.close();

        if (rowsAffected == 0) {
            throw new SQLException("Deletion failed, no rows affected.");
        } else if (rowsAffected > 1) {
            throw new SQLException("Deletion failed, too many rows affected.");
        }

        return rowsAffected;
    }

    public int getNextId() throws SQLException {
        Connection connection = Database.getConnection();
        String query = "SELECT MAX(ID) FROM applicationss";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        int id; 
        if (rs.next()) {
            id = rs.getInt(1) + 1;
        } else {
            id = 1;
        }

        rs.close();
        statement.close();
        connection.close();
        return id;
    }

}
