package com.luxoft.instrument.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="INSTRUMENT_PRICE_MODIFIER")
public class InstrumentPriceModifier {

	/**
	 * autogenerated id.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	/**
	 * name of the instrument
	 */
	@Column(length = 32000, nullable = false)
	private String name;
	
	/**
	 * double multiplier. Using the primitive 'double' type, since it does not
	 * accept null values.
	 */
	@Column(length = 100, scale = 2, nullable = false)
	private double multiplier;
	
	/**
	 * empty constructor for generating instances.
	 */
	public InstrumentPriceModifier() {
		
	}

	/**
	 * This constructor creates new object instances for validation and persistence.
	 * @param name of the instrument
	 * @param multiplier of the instrument. It will be saved in a new one, but multiplied in an update
	 */
	public InstrumentPriceModifier(String name, double multiplier) {
		this.name = name;
		this.multiplier = multiplier;
	}
	
	/**
	 * This is the full constructor. 
	 * @param id of the instrument
	 * @param name of the instrument
	 * @param multiplier of the instrument. It will be saved in a new one, but multiplied in an update
	 */
	public InstrumentPriceModifier(Long id, String name, double multiplier) {
		this.id = id;
		this.name = name;
		this.multiplier = multiplier;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}
	
	/**
	 * 
	 * AEclipse auto-generated hashCode() for comparing each attribute.
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @return
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(multiplier);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * 
	 * Eclipse auto-generated equals() for comparing each attribute.
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @param obj sent to compare
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstrumentPriceModifier other = (InstrumentPriceModifier) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(multiplier) != Double.doubleToLongBits(other.multiplier))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getName();
	}
}
