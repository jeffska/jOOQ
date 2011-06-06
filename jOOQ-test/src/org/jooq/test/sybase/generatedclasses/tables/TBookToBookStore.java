/**
 * This class is generated by jOOQ
 */
package org.jooq.test.sybase.generatedclasses.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.SQLDialect;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableFieldImpl;
import org.jooq.impl.UpdatableTableImpl;
import org.jooq.test.sybase.generatedclasses.Dba;
import org.jooq.test.sybase.generatedclasses.Keys;
import org.jooq.test.sybase.generatedclasses.tables.records.TBookToBookStoreRecord;
import org.jooq.util.sybase.SybaseDataType;


/**
 * This class is generated by jOOQ.
 *
 * An m:n relation between books and book stores
 */
@Generated(value    = "http://jooq.sourceforge.net",
           comments = "This class is generated by jOOQ")
public class TBookToBookStore extends UpdatableTableImpl<TBookToBookStoreRecord> {

	private static final long serialVersionUID = 1431523904;

	/**
	 * The singleton instance of t_book_to_book_store
	 */
	public static final TBookToBookStore T_BOOK_TO_BOOK_STORE = new TBookToBookStore();

	/**
	 * The class holding records for this type
	 */
	private static final Class<TBookToBookStoreRecord> __RECORD_TYPE = TBookToBookStoreRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TBookToBookStoreRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [dba.t_book_to_book_store.book_store_name]
	 * REFERENCES t_book_store [dba.t_book_store.name]
	 * </pre></code>
	 */
	public static final TableField<TBookToBookStoreRecord, String> BOOK_STORE_NAME = new TableFieldImpl<TBookToBookStoreRecord, String>(SQLDialect.SYBASE, "book_store_name", SybaseDataType.VARCHAR, T_BOOK_TO_BOOK_STORE);

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [dba.t_book_to_book_store.book_id]
	 * REFERENCES t_book [dba.t_book.ID]
	 * </pre></code>
	 */
	public static final TableField<TBookToBookStoreRecord, Integer> BOOK_ID = new TableFieldImpl<TBookToBookStoreRecord, Integer>(SQLDialect.SYBASE, "book_id", SybaseDataType.INTEGER, T_BOOK_TO_BOOK_STORE);

	/**
	 * An uncommented item
	 */
	public static final TableField<TBookToBookStoreRecord, Integer> STOCK = new TableFieldImpl<TBookToBookStoreRecord, Integer>(SQLDialect.SYBASE, "stock", SybaseDataType.INTEGER, T_BOOK_TO_BOOK_STORE);

	/**
	 * No further instances allowed
	 */
	private TBookToBookStore() {
		super(SQLDialect.SYBASE, "t_book_to_book_store", Dba.DBA);
	}

	@Override
	public UniqueKey<TBookToBookStoreRecord> getMainKey() {
		return Keys.t_book_to_book_store_pk_b2bs;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UniqueKey<TBookToBookStoreRecord>> getKeys() {
		return Arrays.<UniqueKey<TBookToBookStoreRecord>>asList(Keys.t_book_to_book_store_pk_b2bs);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ForeignKey<TBookToBookStoreRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<TBookToBookStoreRecord, ?>>asList(Keys.t_book_to_book_store_fk_b2bs_bs_name, Keys.t_book_to_book_store_fk_b2bs_b_id);
	}
}