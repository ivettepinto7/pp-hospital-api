package com.grupo25.hospital.models.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PrescriptionDTO {
	@NotNull
	private Long medicine;
	
	@NotNull
	private Float doses;
	
	@Min(1)
	private Integer quantity;

	public PrescriptionDTO() {
		super();
	}

	public PrescriptionDTO(@NotNull Long medicine, @NotNull Float doses, @Min(1) Integer quantity) {
		super();
		this.medicine = medicine;
		this.doses = doses;
		this.quantity = quantity;
	}

	public Long getMedicine() {
		return medicine;
	}

	public void setMedicine(Long medicine) {
		this.medicine = medicine;
	}

	public Float getDoses() {
		return doses;
	}

	public void setDoses(Float doses) {
		this.doses = doses;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	
}