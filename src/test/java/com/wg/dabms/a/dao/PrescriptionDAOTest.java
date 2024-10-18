package com.wg.dabms.a.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wg.dabms.dao.PrescriptionDAO;
import com.wg.dabms.model.Prescription;

public class PrescriptionDAOTest {

    @InjectMocks
    private PrescriptionDAO prescriptionDAO;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    public void testAddPrescription() throws SQLException, ClassNotFoundException {
        Prescription prescription = new Prescription();
        prescription.setUuid("1");
        prescription.setAppointmentId("appointment_1");
        prescription.setDescription("Take medication twice daily");
        prescription.setCreatedAt(LocalDateTime.now());
        prescription.setUpdatedAt(LocalDateTime.now());

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = prescriptionDAO.createPrescription(prescription);

        assertTrue(result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetPrescriptionById() throws SQLException, ClassNotFoundException {
        Prescription prescription = new Prescription();
        prescription.setUuid("1");
        prescription.setAppointmentId("appointment_1");
        prescription.setDescription("Take medication twice daily");
        prescription.setCreatedAt(LocalDateTime.now());
        prescription.setUpdatedAt(LocalDateTime.now());

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1");
        when(resultSet.getString("appointment_id")).thenReturn("appointment_1");
        when(resultSet.getString("description")).thenReturn("Take medication twice daily");
        when(resultSet.getTimestamp("created_at")).thenReturn(java.sql.Timestamp.valueOf(prescription.getCreatedAt()));
        when(resultSet.getTimestamp("updated_at")).thenReturn(java.sql.Timestamp.valueOf(prescription.getUpdatedAt()));

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Prescription result = prescriptionDAO.getPrescriptionById("1");

        assertNotNull(result);
        assertEquals("1", result.getUuid());
        assertEquals("appointment_1", result.getAppointmentId());
        assertEquals("Take medication twice daily", result.getDescription());
        assertEquals(prescription.getCreatedAt(), result.getCreatedAt());
        assertEquals(prescription.getUpdatedAt(), result.getUpdatedAt());
    }

    @Test
    public void testGetAllPrescriptions() throws SQLException, ClassNotFoundException {
        Prescription prescription1 = new Prescription();
        prescription1.setUuid("1");
        prescription1.setAppointmentId("appointment_1");
        prescription1.setDescription("Take medication twice daily");
        prescription1.setCreatedAt(LocalDateTime.now());
        prescription1.setUpdatedAt(LocalDateTime.now());

        Prescription prescription2 = new Prescription();
        prescription2.setUuid("2");
        prescription2.setAppointmentId("appointment_2");
        prescription2.setDescription("Take medication thrice daily");
        prescription2.setCreatedAt(LocalDateTime.now());
        prescription2.setUpdatedAt(LocalDateTime.now());

        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1").thenReturn("2");
        when(resultSet.getString("appointment_id")).thenReturn("appointment_1").thenReturn("appointment_2");
        when(resultSet.getString("description")).thenReturn("Take medication twice daily").thenReturn("Take medication thrice daily");
        when(resultSet.getTimestamp("created_at")).thenReturn(java.sql.Timestamp.valueOf(prescription1.getCreatedAt())).thenReturn(java.sql.Timestamp.valueOf(prescription2.getCreatedAt()));
        when(resultSet.getTimestamp("updated_at")).thenReturn(java.sql.Timestamp.valueOf(prescription1.getUpdatedAt())).thenReturn(java.sql.Timestamp.valueOf(prescription2.getUpdatedAt()));

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Prescription> result = prescriptionDAO.getAllPrescriptions();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getUuid());
        assertEquals("2", result.get(1).getUuid());
    }

    @Test
    public void testUpdatePrescription() throws SQLException, ClassNotFoundException {
        Prescription prescription = new Prescription();
        prescription.setUuid("1");
        prescription.setDescription("Updated description");

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = prescriptionDAO.updatePrescription(prescription);

        assertTrue(result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testDeletePrescription() throws SQLException, ClassNotFoundException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = prescriptionDAO.deletePrescription("1");

        assertTrue(result);
        verify(preparedStatement).executeUpdate();
    }
}
