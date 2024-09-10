package com.wg.dabms.a.service;

import java.util.List;

import com.wg.dabms.a.dao.PrescriptionDAO;
import com.wg.dabms.a.dao.UserDAO;
import com.wg.dabms.model.Prescription;
import com.wg.dabms.model.User;

public class PrescriptionService {

	private PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
    private UserDAO userDAO = new UserDAO();

    public PrescriptionService() {
    }
    
    public List<Prescription> getPrescriptionsByUser(String userId) {
        return prescriptionDAO.getPrescriptionsByUser(userId);
    }

    public Prescription getPrescriptionById(String uuid) {
        return prescriptionDAO.getPrescriptionById(uuid);
    }

    public List<Prescription> getAllPrescriptions() {
        return prescriptionDAO.getAllPrescriptions();
    }

    public boolean createPrescription(Prescription prescription) {
        return prescriptionDAO.createPrescription(prescription);
    }

    public boolean updatePrescription(Prescription prescription) {
        return prescriptionDAO.updatePrescription(prescription);
    }

    public boolean deletePrescription(String uuid) {
        return prescriptionDAO.deletePrescription(uuid);
    }
    
    public List<User> findUsersByUsername(String username) {
        return userDAO.findUsersByUsername(username);
    }

    public String getUserNameById(String userId) {
        User user = userDAO.getUserById(userId);
        return user != null ? user.getUsername() : "Unknown";
    }

    public User getUserById(String userId) {
        return userDAO.getUserById(userId);
    }
}
