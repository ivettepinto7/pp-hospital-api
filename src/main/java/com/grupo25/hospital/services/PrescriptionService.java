package com.grupo25.hospital.services;


import java.util.List;

import com.grupo25.hospital.models.entities.Prescription;

public interface PrescriptionService {
	
	public List<Prescription> getPatientPrescriptions(Long id) throws Exception;;

}
