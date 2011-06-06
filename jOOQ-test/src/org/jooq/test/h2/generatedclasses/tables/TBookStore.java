/**
 * This class is generated by jOOQ
 */
package org.jooq.test.h2.generatedclasses.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.SQLDialect;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableFieldImpl;
import org.jooq.impl.UpdatableTableImpl;
import org.jooq.test.h2.generatedclasses.Keys;
import org.jooq.test.h2.generatedclasses.Public;
import org.jooq.test.h2.generatedclasses.tables.records.TBookStoreRecord;
import org.jooq.util.h2.H2DataType;


/**
 * This class is generated by jOOQ.
 *
 * A book store
 */
@Generated(value    = "http://jooq.sourceforge.net",
           comments = "This class is generated by jOOQ")
public class TBookStore extends UpdatableTableImpl<TBookStoreRecord> {

	private static final long serialVersionUID = -375142600;

	/**
	 * The singleton instance of T_BOOK_STORE
	 */
	public static final TBookStore T_BOOK_STORE = new TBookStore();

	/**
	 * The class holding records for this type
	 */
	private static final Class<TBookStoreRecord> __RECORD_TYPE = TBookStoreRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TBookStoreRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 */
	public static final TableField<TBookStoreRecord, Integer> ID = new TableFieldImpl<TBookStoreRecord, Integer>(SQLDialect.H2, "ID", H2DataType.INTEGER, T_BOOK_STORE);

	/**
	 * The books store name
	 */
	public static final TableField<TBookStoreRecord, String> NAME = new TableFieldImpl<TBookStoreRecord, String>(SQLDialect.H2, "NAME", H2DataType.VARCHAR, T_BOOK_STORE);

	/**
	 * No further instances allowed
	 */
	private TBookStore() {
		super(SQLDialect.H2, "T_BOOK_STORE", Public.PUBLIC);
	}

	@Override
	public Identity<TBookStoreRecord, Integer> getIdentity() {
		return Keys.IDENTITY_T_BOOK_STORE;
	}

	@Override
	public UniqueKey<TBookStoreRecord> getMainKey() {
		return Keys.UK_T_BOOK_STORE_NAME;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UniqueKey<TBookStoreRecord>> getKeys() {
		return Arrays.<UniqueKey<TBookStoreRecord>>asList(Keys.UK_T_BOOK_STORE_NAME);
	}
}