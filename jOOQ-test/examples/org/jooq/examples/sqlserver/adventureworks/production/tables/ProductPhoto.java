/**
 * This class is generated by jOOQ
 */
package org.jooq.examples.sqlserver.adventureworks.production.tables;

/**
 * This class is generated by jOOQ.
 */
public class ProductPhoto extends org.jooq.impl.UpdatableTableImpl<org.jooq.examples.sqlserver.adventureworks.production.tables.records.ProductPhoto> {

	private static final long serialVersionUID = 726539585;

	/**
	 * The singleton instance of Production.ProductPhoto
	 */
	public static final org.jooq.examples.sqlserver.adventureworks.production.tables.ProductPhoto ProductPhoto = new org.jooq.examples.sqlserver.adventureworks.production.tables.ProductPhoto();

	/**
	 * The class holding records for this type
	 */
	private static final java.lang.Class<org.jooq.examples.sqlserver.adventureworks.production.tables.records.ProductPhoto> __RECORD_TYPE = org.jooq.examples.sqlserver.adventureworks.production.tables.records.ProductPhoto.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.jooq.examples.sqlserver.adventureworks.production.tables.records.ProductPhoto> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public final org.jooq.TableField<org.jooq.examples.sqlserver.adventureworks.production.tables.records.ProductPhoto, java.lang.Integer> ProductPhotoID = createField("ProductPhotoID", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<org.jooq.examples.sqlserver.adventureworks.production.tables.records.ProductPhoto, byte[]> ThumbNailPhoto = createField("ThumbNailPhoto", org.jooq.impl.SQLDataType.VARBINARY, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<org.jooq.examples.sqlserver.adventureworks.production.tables.records.ProductPhoto, java.lang.String> ThumbnailPhotoFileName = createField("ThumbnailPhotoFileName", org.jooq.impl.SQLDataType.NVARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<org.jooq.examples.sqlserver.adventureworks.production.tables.records.ProductPhoto, byte[]> LargePhoto = createField("LargePhoto", org.jooq.impl.SQLDataType.VARBINARY, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<org.jooq.examples.sqlserver.adventureworks.production.tables.records.ProductPhoto, java.lang.String> LargePhotoFileName = createField("LargePhotoFileName", org.jooq.impl.SQLDataType.NVARCHAR, this);

	/**
	 * An uncommented item
	 */
	public final org.jooq.TableField<org.jooq.examples.sqlserver.adventureworks.production.tables.records.ProductPhoto, java.sql.Timestamp> ModifiedDate = createField("ModifiedDate", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * No further instances allowed
	 */
	private ProductPhoto() {
		super("ProductPhoto", org.jooq.examples.sqlserver.adventureworks.production.Production.Production);
	}

	/**
	 * No further instances allowed
	 */
	private ProductPhoto(java.lang.String alias) {
		super(alias, org.jooq.examples.sqlserver.adventureworks.production.Production.Production, org.jooq.examples.sqlserver.adventureworks.production.tables.ProductPhoto.ProductPhoto);
	}

	@Override
	public org.jooq.Identity<org.jooq.examples.sqlserver.adventureworks.production.tables.records.ProductPhoto, java.lang.Integer> getIdentity() {
		return org.jooq.examples.sqlserver.adventureworks.production.Keys.IDENTITY_ProductPhoto;
	}

	@Override
	public org.jooq.UniqueKey<org.jooq.examples.sqlserver.adventureworks.production.tables.records.ProductPhoto> getMainKey() {
		return org.jooq.examples.sqlserver.adventureworks.production.Keys.PK_ProductPhoto_ProductPhotoID;
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.UniqueKey<org.jooq.examples.sqlserver.adventureworks.production.tables.records.ProductPhoto>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<org.jooq.examples.sqlserver.adventureworks.production.tables.records.ProductPhoto>>asList(org.jooq.examples.sqlserver.adventureworks.production.Keys.PK_ProductPhoto_ProductPhotoID);
	}

	@Override
	public org.jooq.examples.sqlserver.adventureworks.production.tables.ProductPhoto as(java.lang.String alias) {
		return new org.jooq.examples.sqlserver.adventureworks.production.tables.ProductPhoto(alias);
	}
}