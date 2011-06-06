/**
 * This class is generated by jOOQ
 */
package org.jooq.test.sybase.generatedclasses.enums;


import javax.annotation.Generated;

import org.jooq.MasterDataType;


/**
 * This class is generated by jOOQ.
 *
 * An entity holding language master data
 */
@Generated(value    = "http://jooq.sourceforge.net",
           comments = "This class is generated by jOOQ")
public enum TLanguage implements MasterDataType<Integer> {

	/**
	 * English
	 */
	en(1, "en", "English"),

	/**
	 * Deutsch
	 */
	de(2, "de", "Deutsch"),

	/**
	 * Français
	 */
	fr(3, "fr", "Français"),

	/**
	 * null
	 */
	pt(4, "pt", null),
	;

	private final Integer id;
	private final String cd;
	private final String description;

	private TLanguage(Integer id, String cd, String description) {
		this.id = id;
		this.cd = cd;
		this.description = description;
	}

	@Override
	public Integer getPrimaryKey() {
		return id;
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public final Integer getId() {
		return id;
	}

	/**
	 * An uncommented item
	 */
	public final String getCd() {
		return cd;
	}

	/**
	 * An uncommented item
	 */
	public final String getDescription() {
		return description;
	}
}