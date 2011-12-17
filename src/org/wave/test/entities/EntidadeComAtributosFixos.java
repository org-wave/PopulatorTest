package org.wave.test.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class EntidadeComAtributosFixos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Version
	private Integer version;

	private Boolean active;

	private static String staticField;

	private transient String transientField;

	private static transient String staticAndTransientField;

	private String stringField;

	private Integer integerField;

	private Long longField;

	private BigDecimal bigDecimalField;

	private Boolean booleanField;

	private Calendar calendarField;

	private byte[] byteField;

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public Long getId() {
		return id;
	}

	public Integer getVersion() {
		return version;
	}

	public Boolean getActive() {
		return active;
	}

	public static String getStaticField() {
		return staticField;
	}

	public String getTransientField() {
		return transientField;
	}

	public static String getStaticAndTransientField() {
		return staticAndTransientField;
	}

	public String getStringField() {
		return stringField;
	}

	public Integer getIntegerField() {
		return integerField;
	}

	public Long getLongField() {
		return longField;
	}

	public BigDecimal getBigDecimalField() {
		return bigDecimalField;
	}

	public Boolean getBooleanField() {
		return booleanField;
	}

	public Calendar getCalendarField() {
		return calendarField;
	}

	public byte[] getByteField() {
		return byteField;
	}

}
