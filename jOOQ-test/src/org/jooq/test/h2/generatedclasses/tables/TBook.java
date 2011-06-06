/**
 * This class is generated by jOOQ
 */
package org.jooq.test.h2.generatedclasses.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.SQLDialect;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableFieldImpl;
import org.jooq.impl.UpdatableTableImpl;
import org.jooq.test.h2.generatedclasses.Keys;
import org.jooq.test.h2.generatedclasses.Public;
import org.jooq.test.h2.generatedclasses.enums.TLanguage;
import org.jooq.test.h2.generatedclasses.tables.records.TBookRecord;
import org.jooq.util.h2.H2DataType;


/**
 * This class is generated by jOOQ.
 *
 * An entity holding books
 */
@Generated(value    = "http://jooq.sourceforge.net",
           comments = "This class is generated by jOOQ")
public class TBook extends UpdatableTableImpl<TBookRecord> {

	private static final long serialVersionUID = 1130257287;

	/**
	 * The singleton instance of T_BOOK
	 */
	public static final TBook T_BOOK = new TBook();

	/**
	 * The class holding records for this type
	 */
	private static final Class<TBookRecord> __RECORD_TYPE = TBookRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TBookRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * The book ID
	 * 
	 * PRIMARY KEY
	 */
	public static final TableField<TBookRecord, Integer> ID = new TableFieldImpl<TBookRecord, Integer>(SQLDialect.H2, "ID", H2DataType.INTEGER, T_BOOK);

	/**
	 * The author ID in entity 'author'
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [PUBLIC.T_BOOK.AUTHOR_ID]
	 * REFERENCES T_AUTHOR [PUBLIC.T_AUTHOR.ID]
	 * </pre></code>
	 */
	public static final TableField<TBookRecord, Integer> AUTHOR_ID = new TableFieldImpl<TBookRecord, Integer>(SQLDialect.H2, "AUTHOR_ID", H2DataType.INTEGER, T_BOOK);

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [PUBLIC.T_BOOK.CO_AUTHOR_ID]
	 * REFERENCES T_AUTHOR [PUBLIC.T_AUTHOR.ID]
	 * </pre></code>
	 */
	public static final TableField<TBookRecord, Integer> CO_AUTHOR_ID = new TableFieldImpl<TBookRecord, Integer>(SQLDialect.H2, "CO_AUTHOR_ID", H2DataType.INTEGER, T_BOOK);

	/**
	 * An uncommented item
	 */
	public static final TableField<TBookRecord, Integer> DETAILS_ID = new TableFieldImpl<TBookRecord, Integer>(SQLDialect.H2, "DETAILS_ID", H2DataType.INTEGER, T_BOOK);

	/**
	 * The book's title
	 */
	public static final TableField<TBookRecord, String> TITLE = new TableFieldImpl<TBookRecord, String>(SQLDialect.H2, "TITLE", H2DataType.VARCHAR, T_BOOK);

	/**
	 * The year the book was published in
	 */
	public static final TableField<TBookRecord, Integer> PUBLISHED_IN = new TableFieldImpl<TBookRecord, Integer>(SQLDialect.H2, "PUBLISHED_IN", H2DataType.INTEGER, T_BOOK);

	/**
	 * The language of the book
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [PUBLIC.T_BOOK.LANGUAGE_ID]
	 * REFERENCES T_LANGUAGE [PUBLIC.T_LANGUAGE.ID]
	 * </pre></code>
	 */
	public static final TableField<TBookRecord, TLanguage> LANGUAGE_ID = new TableFieldImpl<TBookRecord, TLanguage>(SQLDialect.H2, "LANGUAGE_ID", H2DataType.INTEGER.asMasterDataType(TLanguage.class), T_BOOK);

	/**
	 * Some textual content of the book
	 */
	public static final TableField<TBookRecord, String> CONTENT_TEXT = new TableFieldImpl<TBookRecord, String>(SQLDialect.H2, "CONTENT_TEXT", H2DataType.CLOB, T_BOOK);

	/**
	 * Some binary content of the book
	 */
	public static final TableField<TBookRecord, byte[]> CONTENT_PDF = new TableFieldImpl<TBookRecord, byte[]>(SQLDialect.H2, "CONTENT_PDF", H2DataType.BLOB, T_BOOK);

	/**
	 * No further instances allowed
	 */
	private TBook() {
		super(SQLDialect.H2, "T_BOOK", Public.PUBLIC);
	}

	@Override
	public UniqueKey<TBookRecord> getMainKey() {
		return Keys.PK_T_BOOK;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UniqueKey<TBookRecord>> getKeys() {
		return Arrays.<UniqueKey<TBookRecord>>asList(Keys.PK_T_BOOK);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ForeignKey<TBookRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<TBookRecord, ?>>asList(Keys.FK_T_BOOK_AUTHOR_ID, Keys.FK_T_BOOK_CO_AUTHOR_ID);
	}
}