/**
 * This class is generated by jOOQ
 */
package org.jooq.test.h2.generatedclasses.tables.records;


import java.sql.SQLException;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.test.h2.generatedclasses.tables.TBookStore;
import org.jooq.test.h2.generatedclasses.tables.TBookToBookStore;


/**
 * This class is generated by jOOQ.
 *
 * A book store
 */
@Generated(value    = "http://jooq.sourceforge.net",
           comments = "This class is generated by jOOQ")
public class TBookStoreRecord extends UpdatableRecordImpl<TBookStoreRecord> {

	private static final long serialVersionUID = 1636969943;

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
	 * The books store name
	 */
	public void setName(String value) {
		setValue(TBookStore.NAME, value);
	}

	/**
	 * The books store name
	 */
	public String getName() {
		return getValue(TBookStore.NAME);
	}

	/**
	 * The books store name
	 */
	public List<TBookToBookStoreRecord> fetchTBookToBookStoreList() throws SQLException {
		return create()
			.selectFrom(TBookToBookStore.T_BOOK_TO_BOOK_STORE)
			.where(TBookToBookStore.BOOK_STORE_NAME.equal(getValue(TBookStore.NAME)))
			.fetch()
			.getRecords();
	}

	/**
	 * The books store name
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