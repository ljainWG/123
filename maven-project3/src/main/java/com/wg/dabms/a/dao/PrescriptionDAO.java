package com.wg.dabms.a.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.wg.dabms.model.Prescription;

public class PrescriptionDAO extends GenericDAO<Prescription> {

    @Override
    protected Prescription mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Prescription prescription = new Prescription();
        prescription.setUuid(resultSet.getString("uuid"));
        prescription.setAppointmentId(resultSet.getString("appointment_id"));
        prescription.setDescription(resultSet.getString("description"));
        prescription.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        prescription.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
        return prescription;
    }
    
    public List<Prescription> getPrescriptionsByUser(String userId) {
        String query = "SELECT p.* FROM prescriptions p " +
                       "JOIN appointments a ON p.appointment_id = a.uuid " +
                       "WHERE a.patient_id = ? OR a.doctor_id = ?";
        return executeGetAllQuery(query, userId, userId);
    }
    public Prescription getPrescriptionById(String uuid) {
        String query = "SELECT * FROM prescriptions WHERE uuid = ?";
        return executeGetQuery(query, uuid);
    }

    public List<Prescription> getAllPrescriptions() {
        String query = "SELECT * FROM prescriptions";
        return executeGetAllQuery(query);
    }

    public boolean createPrescription(Prescription prescription) {
        String query = "INSERT INTO prescriptions (uuid, appointment_id, description, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        return executeUpdate(query, prescription.getUuid(), prescription.getAppointmentId(), prescription.getDescription(), prescription.getCreatedAt(), prescription.getUpdatedAt());
    }

    public boolean updatePrescription(Prescription prescription) {
        String query = "UPDATE prescriptions SET appointment_id = ?, description = ?, created_at = ?, updated_at = ? WHERE uuid = ?";
        return executeUpdate(query, prescription.getAppointmentId(), prescription.getDescription(), prescription.getCreatedAt(), prescription.getUpdatedAt(), prescription.getUuid());
    }

    public boolean deletePrescription(String uuid) {
        String query = "DELETE FROM prescriptions WHERE uuid = ?";
        return executeDelete(query, uuid);
    }
}
