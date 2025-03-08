package ru.netology.patient.service.medical;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.service.alert.SendAlertServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicalServiceImplTest {

    @Test
    public void messageSendsIfPressureIsNotOk() {
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        PatientInfo patientInfo = new PatientInfo
                ("1", "Nikolay", "Ivanov", LocalDate.of(1980, 12, 5),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));
        Mockito.when(patientInfoFileRepository.getById("1")).thenReturn(patientInfo);
        SendAlertServiceImpl sendAlertService = Mockito.mock(SendAlertServiceImpl.class);
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());
        Mockito.doNothing().when(sendAlertService).send(message);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoFileRepository, sendAlertService);
        BloodPressure currentPressure = new BloodPressure(160, 120);
        medicalService.checkBloodPressure("1", currentPressure);
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        Mockito.verify(sendAlertService, Mockito.times(1)).send(message);
    }

    @Test
    public void messageDoesntSendIfPressureIsOk() {
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        PatientInfo patientInfo = new PatientInfo
                ("1", "Nikolay", "Ivanov", LocalDate.of(1980, 12, 5),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));
        Mockito.when(patientInfoFileRepository.getById("1")).thenReturn(patientInfo);
        SendAlertServiceImpl sendAlertService = Mockito.mock(SendAlertServiceImpl.class);
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());
        Mockito.doNothing().when(sendAlertService).send(message);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoFileRepository, sendAlertService);
        BloodPressure currentPressure = new BloodPressure(120, 80);
        medicalService.checkBloodPressure("1", currentPressure);
        Mockito.verify(sendAlertService, Mockito.times(0)).send(message);
    }

    //
    @Test
    public void messageSendsIfTempIsNotOk() {
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        PatientInfo patientInfo = new PatientInfo
                ("1", "Nikolay", "Ivanov", LocalDate.of(1980, 12, 5),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));
        Mockito.when(patientInfoFileRepository.getById("1")).thenReturn(patientInfo);
        SendAlertServiceImpl sendAlertService = Mockito.mock(SendAlertServiceImpl.class);
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());
        Mockito.doNothing().when(sendAlertService).send(message);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoFileRepository, sendAlertService);
        BigDecimal currentTemperature = new BigDecimal("35");
        medicalService.checkTemperature("1", currentTemperature);
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        Mockito.verify(sendAlertService, Mockito.times(1)).send(message);
    }

    @Test
    public void messageDoesntSendIfTempIsOk() {
        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        PatientInfo patientInfo = new PatientInfo
                ("1", "Nikolay", "Ivanov", LocalDate.of(1980, 12, 5),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));
        Mockito.when(patientInfoFileRepository.getById("1")).thenReturn(patientInfo);
        SendAlertServiceImpl sendAlertService = Mockito.mock(SendAlertServiceImpl.class);
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());
        Mockito.doNothing().when(sendAlertService).send(message);
        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoFileRepository, sendAlertService);
        BigDecimal currentTemperature = new BigDecimal("36");
        medicalService.checkTemperature("1", currentTemperature);
        Mockito.verify(sendAlertService, Mockito.times(0)).send(message);
    }
}
