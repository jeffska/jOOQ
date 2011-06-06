/**
 * This class is generated by jOOQ
 */
package org.jooq.test.ingres.generatedclasses.tables.records;


import java.sql.SQLException;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.test.ingres.generatedclasses.enums.TLanguage;
import org.jooq.test.ingres.generatedclasses.tables.TAuthor;
import org.jooq.test.ingres.generatedclasses.tables.TBook;
import org.jooq.test.ingres.generatedclasses.tables.TBookToBookStore;


/**
 * This class is generated by jOOQ.
 */
@Generated(value    = "http://jooq.sourceforge.net",
           comments = "This class is generated by jOOQ")
public class TBookRecord extends UpdatableRecordImpl<TBookRecord> {

	private static final long serialVersionUID = -873623615;

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public void setId(Integer value) {
		setValue(TBook.ID, value);
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public Integer getId() {
		return getValue(TBook.ID);
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public List<TBookToBookStoreRecord> fetchTBookToBookStoreList() throws SQLException {
		return create()
			.selectFrom(TBookToBookStore.T_BOOK_TO_BOOK_STORE)
			.where(TBookToBookStore.BOOK_ID.equal(getValue(TBook.ID)))
			.fetch()
			.getRecords();
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
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
			.where(TBookToBookStore.BOOK_ID.equal(getValue(TBook.ID)))
			.fetch()
			.getRecords();
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [test.t_book.author_id]
	 * REFERENCES t_author [test.t_author.id]
	 * </pre></code>
	 */
	public void setAuthorId(Integer value) {
		setValue(TBook.AUTHOR_ID, value);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [test.t_book.author_id]
	 * REFERENCES t_author [test.t_author.id]
	 * </pre></code>
	 */
	public Integer getAuthorId() {
		return getValue(TBook.AUTHOR_ID);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [test.t_book.author_id]
	 * REFERENCES t_author [test.t_author.id]
	 * </pre></code>
	 */
	public TAuthorRecord fetchTAuthorByAuthorId() throws SQLException {
		return create()
			.selectFrom(TAuthor.T_AUTHOR)
			.where(TAuthor.ID.equal(getValue(TBook.AUTHOR_ID)))
			.fetchOne();
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [test.t_book.author_id]
	 * REFERENCES t_author [test.t_author.id]
	 * </pre></code>
	 *
	 * @deprecated Because of risk of ambiguity (#187), code generation for this<br/>
	 *             method will not be supported anymore, soon.<br/><br/>
	 *             If you wish to remove this method, adapt your configuration:<br/>
	 *             <code>generator.generate.deprecated=false</code>
	 */
	@Deprecated
	public TAuthorRecord getTAuthorByAuthorId() throws SQLException {
		return create()
			.selectFrom(TAuthor.T_AUTHOR)
			.where(TAuthor.ID.equal(getValue(TBook.AUTHOR_ID)))
			.fetchOne();
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [test.t_book.co_author_id]
	 * REFERENCES t_author [test.t_author.id]
	 * </pre></code>
	 */
	public void setCoAuthorId(Integer value) {
		setValue(TBook.CO_AUTHOR_ID, value);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [test.t_book.co_author_id]
	 * REFERENCES t_author [test.t_author.id]
	 * </pre></code>
	 */
	public Integer getCoAuthorId() {
		return getValue(TBook.CO_AUTHOR_ID);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [test.t_book.co_author_id]
	 * REFERENCES t_author [test.t_author.id]
	 * </pre></code>
	 */
	public TAuthorRecord fetchTAuthorByCoAuthorId() throws SQLException {
		return create()
			.selectFrom(TAuthor.T_AUTHOR)
			.where(TAuthor.ID.equal(getValue(TBook.CO_AUTHOR_ID)))
			.fetchOne();
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [test.t_book.co_author_id]
	 * REFERENCES t_author [test.t_author.id]
	 * </pre></code>
	 *
	 * @deprecated Because of risk of ambiguity (#187), code generation for this<br/>
	 *             method will not be supported anymore, soon.<br/><br/>
	 *             If you wish to remove this method, adapt your configuration:<br/>
	 *             <code>generator.generate.deprecated=false</code>
	 */
	@Deprecated
	public TAuthorRecord getTAuthorByCoAuthorId() throws SQLException {
		return create()
			.selectFrom(TAuthor.T_AUTHOR)
			.where(TAuthor.ID.equal(getValue(TBook.CO_AUTHOR_ID)))
			.fetchOne();
	}

	/**
	 * An uncommented item
	 */
	public void setDetailsId(Integer value) {
		setValue(TBook.DETAILS_ID, value);
	}

	/**
	 * An uncommented item
	 */
	public Integer getDetailsId() {
		return getValue(TBook.DETAILS_ID);
	}

	/**
	 * An uncommented item
	 */
	public void setTitle(String value) {
		setValue(TBook.TITLE, value);
	}

	/**
	 * An uncommented item
	 */
	public String getTitle() {
		return getValue(TBook.TITLE);
	}

	/**
	 * An uncommented item
	 */
	public void setPublishedIn(Integer value) {
		setValue(TBook.PUBLISHED_IN, value);
	}

	/**
	 * An uncommented item
	 */
	public Integer getPublishedIn() {
		return getValue(TBook.PUBLISHED_IN);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [test.t_book.language_id]
	 * REFERENCES t_language [test.t_language.id]
	 * </pre></code>
	 */
	public void setLanguageId(TLanguage value) {
		setValue(TBook.LANGUAGE_ID, value);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [test.t_book.language_id]
	 * REFERENCES t_language [test.t_language.id]
	 * </pre></code>
	 */
	public TLanguage getLanguageId() {
		return getValue(TBook.LANGUAGE_ID);
	}

	/**
	 * An uncommented item
	 */
	public void setContentText(String value) {
		setValue(TBook.CONTENT_TEXT, value);
	}

	/**
	 * An uncommented item
	 */
	public String getContentText() {
		return getValue(TBook.CONTENT_TEXT);
	}

	/**
	 * An uncommented item
	 */
	public void setContentPdf(byte[] value) {
		setValue(TBook.CONTENT_PDF, value);
	}

	/**
	 * An uncommented item
	 */
	public byte[] getContentPdf() {
		return getValue(TBook.CONTENT_PDF);
	}
	/**
	 * Create a detached TBookRecord
	 */
	public TBookRecord() {
		super(TBook.T_BOOK);
	}

	/**
	 * Create an attached TBookRecord
	 */
	public TBookRecord(Configuration configuration) {
		super(TBook.T_BOOK, configuration);
	}
}