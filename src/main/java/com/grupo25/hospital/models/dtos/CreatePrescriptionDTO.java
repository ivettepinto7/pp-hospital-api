package com.grupo25.hospital.models.dtos;

public class CreatePrescriptionDTO {
	private int id_paciente;
	private String nombre;
	private int cantidad_diaria;
	private int cantidad;
	private String indicacion;
	public CreatePrescriptionDTO(int id_paciente, String nombre, int cantidad_diaria, int cantidad, String indicacion) {
		super();
		this.id_paciente = id_paciente;
		this.nombre = nombre;
		this.cantidad_diaria = cantidad_diaria;
		this.cantidad = cantidad;
		this.indicacion = indicacion;
	}
	public int getId_paciente() {
		return id_paciente;
	}
	public void setId_paciente(int id_paciente) {
		this.id_paciente = id_paciente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCantidad_diaria() {
		return cantidad_diaria;
	}
	public void setCantidad_diaria(int cantidad_diaria) {
		this.cantidad_diaria = cantidad_diaria;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getIndicacion() {
		return indicacion;
	}
	public void setIndicacion(String indicacion) {
		this.indicacion = indicacion;
	}
	
	
}
