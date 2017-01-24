/*
 * This file is generated by jOOQ.
 */
package main.java.com.jooqpoc.local.tables.records;

import javax.annotation.Generated;

import main.java.com.jooqpoc.local.tables.Client;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@Generated(value = { "http://www.jooq.org", "jOOQ version:3.9.0" }, comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ClientRecord extends UpdatableRecordImpl<ClientRecord> implements
		Record3<Integer, String, String> {

	private static final long serialVersionUID = -874267078;

	/**
	 * Create a detached ClientRecord
	 */
	public ClientRecord() {
		super(Client.CLIENT);
	}

	/**
	 * Create a detached, initialised ClientRecord
	 */
	public ClientRecord(Integer id, String firstName, String lastName) {
		super(Client.CLIENT);

		set(0, id);
		set(1, firstName);
		set(2, lastName);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	public Field<Integer> field1() {
		// TODO Auto-generated method stub
		return null;
	}

	// -------------------------------------------------------------------------
	// Record3 type implementation
	// -------------------------------------------------------------------------

	public Field<String> field2() {
		// TODO Auto-generated method stub
		return null;
	}

	public Field<String> field3() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Row3<Integer, String, String> fieldsRow() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Getter for <code>library.client.first_name</code>.
	 */
	public String getFirstName() {
		return (String) get(1);
	}

	/**
	 * Getter for <code>library.client.id</code>.
	 */
	public Integer getId() {
		return (Integer) get(0);
	}

	/**
	 * Getter for <code>library.client.last_name</code>.
	 */
	public String getLastName() {
		return (String) get(2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	/**
	 * Setter for <code>library.client.first_name</code>.
	 */
	public void setFirstName(String value) {
		set(1, value);
	}

	/**
	 * Setter for <code>library.client.id</code>.
	 */
	public void setId(Integer value) {
		set(0, value);
	}

	/**
	 * Setter for <code>library.client.last_name</code>.
	 */
	public void setLastName(String value) {
		set(2, value);
	}

	public Integer value1() {
		// TODO Auto-generated method stub
		return null;
	}

	public Record3<Integer, String, String> value1(Integer value) {
		// TODO Auto-generated method stub
		return null;
	}

	public String value2() {
		// TODO Auto-generated method stub
		return null;
	}

	public Record3<Integer, String, String> value2(String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public String value3() {
		// TODO Auto-generated method stub
		return null;
	}

	public Record3<Integer, String, String> value3(String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public Record3<Integer, String, String> values(Integer t1, String t2,
			String t3) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Integer, String, String> valuesRow() {
		return (Row3) super.valuesRow();
	}
}