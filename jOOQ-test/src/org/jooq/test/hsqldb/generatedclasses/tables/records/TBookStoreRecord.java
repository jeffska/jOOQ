/**
 * This class is generated by jOOQ
 */
package org.jooq.test.hsqldb.generatedclasses.tables.records;


import java.sql.SQLException;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.test.hsqldb.generatedclasses.tables.TBookStore;
import org.jooq.test.hsqldb.generatedclasses.tables.TBookToBookStore;


/**
 * This class is generated by jOOQ.
 */
@Generated(value    = "http://jooq.sourceforge.net",
           comments = "This class is generated by jOOQ")
public class TBookStoreRecord extends UpdatableRecordImpl<TBookStoreRecord> {

	private static final long serialVersionUID = -76330518;

	/**
	 * An uncommented item
	 */
	public void setId(Integer value) {
		setValue(TBookStore.ID, value);
	}

	/**
	 * An uncommented item
	 */
	public Integer getId() {
		return getValue(TBookStore.ID);
	}

	/**
	 * An uncommented item
	 */
	public void setName(String value) {
		setValue(TBookStore.NAME, value);
	}

	/**
	 * An uncommented item
	 */
	public String getName() {
		return getValue(TBookStore.NAME);
	}

	/**
	 * An uncommented item
	 */
	public List<TBookToBookStoreRecord> fetchTBookToBookStoreList() throws SQLException {
		return create()
			.selectFrom(TBookToBookStore.T_BOOK_TO_BOOK_STORE)
			.where(TBookToBookStore.BOOK_STORE_NAME.equal(getValue(TBookStore.NAME)))
			.fetch()
			.getRecords();
	}

	/**
	 * An uncommented item
	 *
	 * @deprecated Because of risk of ambiguity (#187), code generation for this<br/>
	 *             method will not be supported anymore, soon.<br/><br/>
	 *             If you wish to remove this method, adapt your configuration:<br/>
	 *             <code>generator.generate.deprecated=false</code>
	 */
	@Deprecated
	public List<TBookToBookStoreRecord> getTBookToBookStoreList() throws SQLException {
		return create()
			.selectFrom(TBookToBookStore.T_BOOK_TO_BOOK_STORE)
			.where(TBookToBookStore.BOOK_STORE_NAME.equal(getValue(TBookStore.NAME)))
			.fetch()
			.getRecords();
	}
	/**
	 * Create a detached TBookStoreRecord
	 */
	public TBookStoreRecord() {
		super(TBookStore.T_BOOK_STORE);
	}

	/**
	 * Create an attached TBookStoreRecord
	 */
	public TBookStoreRecord(Configuration configuration) {
		super(TBookStore.T_BOOK_STORE, configuration);
	}
}