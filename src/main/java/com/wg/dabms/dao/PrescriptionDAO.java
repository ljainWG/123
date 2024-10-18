package com.wg.dabms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wg.dabms.model.Prescription;

public class PrescriptionDAO extends GenericDAO<Prescription> {

    private static final Logger LOGGER = Logger.getLogger(PrescriptionDAO.class.getName());

    @Override
    protected Prescription mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Prescription prescription = new Prescription();
        prescription.setUuid(resultSet.getString("uuid"));
        prescription.setAppointmentId(resultSet.getString("appointment_id"));
        prescription.setDescription(resultSet.getString("description"));
        prescription.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        prescription.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
        
        LOGGER.log(Level.INFO, "Mapped ResultSet to Prescription entity: {0}", prescription);
        return prescription;
    }
    
    public List<Prescription> getPrescriptionsByUser(String userId) {
        LOGGER.log(Level.INFO, "Getting prescriptions for user ID: {0}", userId);
        String query = "SELECT p.* FROM prescription p " +
                       "JOIN appointment a ON p.appointment_id = a.uuid " +
                       "WHERE a.patient_id = ? OR a.doctor_id = ?";
        return executeGetAllQuery(query, userId, userId);
    }
    
    public Prescription getPrescriptionById(String uuid) {
        LOGGER.log(Level.INFO, "Getting prescription by ID: {0}", uuid);
        String query = "SELECT * FROM prescription WHERE uuid = ?";
        return executeGetQuery(query, uuid);
    }

    public List<Prescription> getAllPrescriptions() {
        LOGGER.info("Getting all prescriptions");
        String query = "SELECT * FROM prescription";
        return executeGetAllQuery(query);
    }

    public boolean createPrescription(Prescription prescription) {
        LOGGER.log(Level.INFO, "Creating prescription: {0}", prescription);
        String query = "INSERT INTO prescription (uuid, appointment_id, description, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        boolean result = executeUpdate(query, prescription.getUuid(), prescription.getAppointmentId(), prescription.getDescription(), prescription.getCreatedAt(), prescription.getUpdatedAt());
        LOGGER.log(Level.INFO, "Prescription creation successful: {0}", result);
        return result;
    }

    public boolean updatePrescription(Prescription prescription) {
        LOGGER.log(Level.INFO, "Updating prescription: {0}", prescription);
        String query = "UPDATE prescription SET appointment_id = ?, description = ?, created_at = ?, updated_at = ? WHERE uuid = ?";
        boolean result = executeUpdate(query, prescription.getAppointmentId(), prescription.getDescription(), prescription.getCreatedAt(), prescription.getUpdatedAt(), prescription.getUuid());
        LOGGER.log(Level.INFO, "Prescription update successful: {0}", result);
        return result;
    }

    public boolean deletePrescription(String uuid) {
        LOGGER.log(Level.INFO, "Deleting prescription with ID: {0}", uuid);
        String query = "DELETE FROM prescription WHERE uuid = ?";
        boolean result = executeDelete(query, uuid);
        LOGGER.log(Level.INFO, "Prescription deletion successful: {0}", result);
        return result;
    }
}
