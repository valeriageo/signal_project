package com.data_management;

import com.alerts.AlertGenerator;
import com.alerts.Alert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStorage {
    private static DataStorage instance;
    private Map<Integer, Patient> patientMap;

    public DataStorage() {
        this.patientMap = new HashMap<>();
    }

    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public void addPatientData(int patientId, double measurementValue, String recordType, long timestamp) {
        Patient patient = patientMap.get(patientId);
        if (patient == null) {
            patient = new Patient(patientId);
            patientMap.put(patientId, patient);
        }
        patient.addRecord(measurementValue, recordType, timestamp);
    }

    public List<PatientRecord> getRecords(int patientId, long startTime, long endTime) {
        Patient patient = patientMap.get(patientId);
        if (patient != null) {
            return patient.getRecords(startTime, endTime);
        }
        return new ArrayList<>();
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    public static void main(String[] args) {
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(1, 100.0, "BloodPressure", 1714376789050L);
        storage.addPatientData(1, 200.0, "BloodPressure", 1714376789051L);
        storage.addPatientData(1, 85.0, "BloodOxygen", 1714376789052L);

        AlertGenerator alertGenerator = new AlertGenerator(storage);
        for (Patient patient : storage.getAllPatients()) {
            alertGenerator.evaluateData(patient);
        }

        for (Alert alert : alertGenerator.getTriggeredAlerts()) {
            System.out.println(alert.toString());
        }
    }
}
