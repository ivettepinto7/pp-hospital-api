package com.grupo25.hospital.controllers;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo25.hospital.models.dtos.AddPrescriptionDTO;
import com.grupo25.hospital.models.dtos.AppoinmentIdDTO;
import com.grupo25.hospital.models.dtos.CreatePrescriptionDTO;
import com.grupo25.hospital.models.dtos.ExpedienteDTO;
import com.grupo25.hospital.models.dtos.MessageDTO;
import com.grupo25.hospital.models.dtos.UpdatePassDTO;
import com.grupo25.hospital.models.entities.Appointment;
import com.grupo25.hospital.models.entities.Drug;
import com.grupo25.hospital.models.entities.Person;
import com.grupo25.hospital.models.entities.Prescription;
import com.grupo25.hospital.repositories.AppointmentRepository;
import com.grupo25.hospital.repositories.DrugRepository;
import com.grupo25.hospital.repositories.PrescriptionRepository;
import com.grupo25.hospital.services.AppointmentService;
import com.grupo25.hospital.services.DrugService;
import com.grupo25.hospital.services.PersonService;

@RestController
@RequestMapping("/doctor")
@CrossOrigin(origins = "*")
public class DoctorContoller {
	@Autowired
	private PersonService personService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private PrescriptionRepository prescriptionRepository;
	
	@Autowired
	private DrugRepository drugRepository;
	
	@GetMapping("/drugs")
	public ResponseEntity<?> getAllDrugs(){
		try {
			List<Drug> drugs = DrugService.findAll();
			
			return new ResponseEntity<>(
						drugs,
						HttpStatus.OK
					);
		} catch (Exception e) {
			return new ResponseEntity<>(
						null,
						HttpStatus.INTERNAL_SERVER_ERROR
					);
		}
	}
	
	@GetMapping("/appointments/today")
	public ResponseEntity<?> getTodayAppointments(){
		LocalDateTime timestamp = (LocalDate.now().atStartOfDay());
		LocalDateTime timestamp2 = (timestamp.plusDays(1)).truncatedTo(ChronoUnit.DAYS);
		try {
			List<Appointment> appointments = appointmentService.findTodayAppointments(timestamp.truncatedTo(ChronoUnit.DAYS), timestamp2);
			
			if(appointments == null) {
				return new ResponseEntity<>(
						new MessageDTO("No hay citas para hoy"),
						HttpStatus.NOT_FOUND
					);
			}
			
			return new ResponseEntity<>(
					appointments,
					HttpStatus.OK
				);
		} catch (Exception e) {
			return new ResponseEntity<>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/my-info/updatepassword")
	public ResponseEntity<?> updateOwnPassword(@Valid @RequestBody UpdatePassDTO newPassInfo, BindingResult result){
		
		try {
			if(result.hasErrors()) {
				String errors = result.getAllErrors().toString();
				return new ResponseEntity<>(
						new MessageDTO("Errores en validacion" + errors),
						HttpStatus.BAD_REQUEST);
			}
			if(!newPassInfo.getNew_password().equals(newPassInfo.getConfirm_password())) {
				return new ResponseEntity<>(
						new MessageDTO("Contraseñas no son iguales"),
						HttpStatus.BAD_REQUEST);
			}
			Person foundPerson = personService.getPersonAuthenticated();
			if(personService.comparePassword(foundPerson, newPassInfo.getCurrent_password())==false) {
				return new ResponseEntity<>(
						new MessageDTO("Contraseña actual equivocada"),
						HttpStatus.BAD_REQUEST);
			}
			personService.updatePersonPassword(newPassInfo, foundPerson);
			return new ResponseEntity<>(
					new MessageDTO("Contraseña actualizada"),
					HttpStatus.OK
				);
			
		} catch (Exception e) {
			return new ResponseEntity<>(
					null,
					HttpStatus.INTERNAL_SERVER_ERROR
				);
		}
	}
	
	@PostMapping("/citas-dia/consulta/receta/crear")
	public ResponseEntity<MessageDTO> createPrescription(@RequestBody CreatePrescriptionDTO receta,BindingResult result){
		try {
			
			//TODO implementar logica de crear 
			return new ResponseEntity<>(
					new MessageDTO("Receta creada"),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new MessageDTO("Error interno"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*@GetMapping("/citas-dia")
	public ResponseEntity<List<CitasDiaDTO>> getDayAppointments(GetEntityDTO doctor ,BindingResult result){
		try {
			//TODO implementar logica de obtener usuarios
			List<CitasDiaDTO> listaCitas= new ArrayList<>();
			return new ResponseEntity<List<CitasDiaDTO>>(
					listaCitas,
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
	
	@GetMapping("/citas-dia/consulta/expediente/{id}")
	public ResponseEntity<List<ExpedienteDTO>> getUserExpediente(@PathVariable Long id){
		try {
			
			List<Appointment> GetAppointments = appointmentService.getAllAppointments(id);
			
			List<ExpedienteDTO> Expediente = new ArrayList<>();
			
			GetAppointments.forEach(appointment ->{
				
				ExpedienteDTO a = new ExpedienteDTO();
				a.setTipo(appointment.getId_appointment_type().getType_name());
				a.setDetallesCita(appointment.getAppointment_details());
				a.setFecha(appointment.getTimestamp());
				
				Expediente.add(a);
				
			});

			return new ResponseEntity<List<ExpedienteDTO>>(
					Expediente,
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@PostMapping("/citas-dia/consulta/agregar-prescripcion")
	public ResponseEntity<MessageDTO> getDayAppointments(@Valid @RequestBody AddPrescriptionDTO prescription,
			BindingResult result){
		try {
			//TODO implementar logica de obtener usuarios
			
			if(result.hasErrors()) {
				return new ResponseEntity<>(
						new MessageDTO("Error"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			Prescription a = new Prescription();
			
			
			
			a.setQuantity(prescription.getQuantity());
			a.setDaily_amount(prescription.getDaily_amount());
			a.setId_appointment(appointmentRepository.findById(prescription.getId_appoinment()).orElse(null));
			a.setIndication(prescription.getIndication());
			a.setId_drug(drugRepository.findById(prescription.getId_drug()).orElse(null));
			
			
			
			prescriptionRepository.save(a);
			
			
			
			
			return new ResponseEntity<>(
					new MessageDTO("Prescripcion agregada"),
					HttpStatus.OK);
			
		} catch (Exception e) {
			System.out.println(e.toString());
			return new ResponseEntity<>(
					new MessageDTO("Error interno"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@PutMapping("/citas-dia/consulta/finalizar")
	public ResponseEntity<MessageDTO> finishAppointment(@Valid @RequestBody AppoinmentIdDTO appoinment,BindingResult result){
		try {
			
			if(result.hasErrors()) {
				String errors = result.getAllErrors().toString();
				return new ResponseEntity<>(
						new MessageDTO("Errores en validacion" + errors),
	                    HttpStatus.BAD_REQUEST
	                );
	        }
			
			Appointment a = appointmentRepository.getAppointmentById(appoinment.getId_appointment());
			a.setStatus(false);
			appointmentRepository.save(a);
			
			return new ResponseEntity<>(
					new MessageDTO("Consulta finalizada"),
					HttpStatus.OK);
			
		} catch (Exception e) {
			System.out.println(e.toString());
			return new ResponseEntity<>(
					new MessageDTO("Error interno"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
