package com.wg.dabms.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wg.dabms.dao.PrescriptionDAO;
import com.wg.dabms.dao.UserDAO;
import com.wg.dabms.model.Prescription;
import com.wg.dabms.model.User;

public class PrescriptionService {

    private static final Logger LOGGER = Logger.getLogger(PrescriptionService.class.getName());
    private PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
    private UserDAO userDAO = new UserDAO();

    public PrescriptionService() {
        LOGGER.info("PrescriptionService instantiated.");
    }
    
    public List<Prescription> getPrescriptionsByUser(String userId) {
        LOGGER.log(Level.INFO, "Fetching prescriptions for user ID: {0}", userId);
        return prescriptionDAO.getPrescriptionsByUser(userId);
    }

    public Prescription getPrescriptionById(String uuid) {
        LOGGER.log(Level.INFO, "Fetching prescription by ID: {0}", uuid);
        return prescriptionDAO.getPrescriptionById(uuid);
    }

    public List<Prescription> getAllPrescriptions() {
        LOGGER.info("Fetching all prescriptions.");
        return prescriptionDAO.getAllPrescriptions();
    }

    public boolean createPrescription(Prescription prescription) {
        LOGGER.log(Level.INFO, "Creating prescription: {0}", prescription);
        boolean result = prescriptionDAO.createPrescription(prescription);
        LOGGER.log(Level.INFO, "Prescription creation successful: {0}", result);
        return result;
    }

    public boolean updatePrescription(Prescription prescription) {
        LOGGER.log(Level.INFO, "Updating prescription: {0}", prescription);
        boolean result = prescriptionDAO.updatePrescription(prescription);
        LOGGER.log(Level.INFO, "Prescription update successful: {0}", result);
        return result;
    }

    public boolean deletePrescription(String uuid) {
        LOGGER.log(Level.INFO, "Deleting prescription with ID: {0}", uuid);
        boolean result = prescriptionDAO.deletePrescription(uuid);
        LOGGER.log(Level.INFO, "Prescription deletion successful: {0}", result);
        return result;
    }
    
    public List<User> findUsersByUsername(String username) {
        LOGGER.log(Level.INFO, "Finding users by username: {0}", username);
        return userDAO.findUsersByUsername(username);
    }

    public String getUserNameById(String userId) {
        LOGGER.log(Level.INFO, "Fetching username for user ID: {0}", userId);
        User user = userDAO.getUserById(userId);
        String username = user != null ? user.getUsername() : "Unknown";
        LOGGER.log(Level.INFO, "Username fetched: {0}", username);
        return username;
    }

    public User getUserById(String userId) {
        LOGGER.log(Level.INFO, "Fetching user by ID: {0}", userId);
        return userDAO.getUserById(userId);
    }
}
