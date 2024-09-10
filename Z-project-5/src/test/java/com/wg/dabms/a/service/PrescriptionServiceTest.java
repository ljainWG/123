package com.wg.dabms.a.service;

import com.wg.dabms.a.dao.PrescriptionDAO;
import com.wg.dabms.a.dao.UserDAO;
import com.wg.dabms.model.Prescription;
import com.wg.dabms.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PrescriptionServiceTest {

    @Mock
    private PrescriptionDAO prescriptionDAO;

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private PrescriptionService prescriptionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    } 

    @Test
    public void testGetPrescriptionsByUser() {
        String userId = "user-id";
        List<Prescription> prescriptions = new ArrayList<>();
        when(prescriptionDAO.getPrescriptionsByUser(userId)).thenReturn(prescriptions);

        List<Prescription> result = prescriptionService.getPrescriptionsByUser(userId);
        assertEquals(prescriptions, result, "Should return prescriptions for the user");
        verify(prescriptionDAO).getPrescriptionsByUser(userId);
    }

    @Test
    public void testGetPrescriptionById() {
        String uuid = "prescription-uuid";
        Prescription prescription = new Prescription();
        when(prescriptionDAO.getPrescriptionById(uuid)).thenReturn(prescription);

        Prescription result = prescriptionService.getPrescriptionById(uuid);
        assertEquals(prescription, result, "Should return the prescription by UUID");
        verify(prescriptionDAO).getPrescriptionById(uuid);
    }

    @Test
    public void testGetAllPrescriptions() {
        List<Prescription> prescriptions = new ArrayList<>();
        when(prescriptionDAO.getAllPrescriptions()).thenReturn(prescriptions);

        List<Prescription> result = prescriptionService.getAllPrescriptions();
        assertEquals(prescriptions, result, "Should return all prescriptions");
        verify(prescriptionDAO).getAllPrescriptions();
    }

    @Test
    public void testCreatePrescription() {
        Prescription prescription = new Prescription();
        when(prescriptionDAO.createPrescription(prescription)).thenReturn(true);

        boolean result = prescriptionService.createPrescription(prescription);
        assertTrue(result, "Should return true when prescription is created");
        verify(prescriptionDAO).createPrescription(prescription);
    }

    @Test
    public void testUpdatePrescription() {
        Prescription prescription = new Prescription();
        when(prescriptionDAO.updatePrescription(prescription)).thenReturn(true);

        boolean result = prescriptionService.updatePrescription(prescription);
        assertTrue(result, "Should return true when prescription is updated");
        verify(prescriptionDAO).updatePrescription(prescription);
    }

    @Test
    public void testDeletePrescription() {
        String uuid = "prescription-uuid";
        when(prescriptionDAO.deletePrescription(uuid)).thenReturn(true);

        boolean result = prescriptionService.deletePrescription(uuid);
        assertTrue(result, "Should return true when prescription is deleted");
        verify(prescriptionDAO).deletePrescription(uuid);
    }

    @Test
    public void testFindUsersByUsername() {
        String username = "username";
        List<User> users = new ArrayList<>();
        when(userDAO.findUsersByUsername(username)).thenReturn(users);

        List<User> result = prescriptionService.findUsersByUsername(username);
        assertEquals(users, result, "Should return users by username");
        verify(userDAO).findUsersByUsername(username);
    }

    @Test
    public void testGetUserNameById() {
        String userId = "user-id";
        User user = new User();
        user.setUsername("test-username");
        when(userDAO.getUserById(userId)).thenReturn(user);

        String result = prescriptionService.getUserNameById(userId);
        assertEquals("test-username", result, "Should return username by user ID");
        verify(userDAO).getUserById(userId);
    }

    @Test
    public void testGetUserById() {
        String userId = "user-id";
        User user = new User();
        when(userDAO.getUserById(userId)).thenReturn(user);

        User result = prescriptionService.getUserById(userId);
        assertEquals(user, result, "Should return user by ID");
        verify(userDAO).getUserById(userId);
    }
}
