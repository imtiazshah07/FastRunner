package com.fast.runner.session.data;

import java.beans.PropertyDescriptor;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.dbutils.BeanProcessor;

/**
 * 
 * @author Syed Imtiaz
 * @contact imtiazshah07@gmail.com
 * @dated 27 September 2016
 * @since 1.0
 * 
 */

public class UsefulRowProcessor extends BeanProcessor {

	private static final int PROPERTY_NOT_FOUND = -1;

	protected int[] mapColumnsToProperties(ResultSetMetaData rsmd, PropertyDescriptor[] props) throws SQLException {

		int cols = rsmd.getColumnCount();
		int columnToProperty[] = new int[cols + 1];

		for (int col = 1; col <= cols; col++) {
			String columnName = rsmd.getColumnLabel(col);
			String underscores[] = columnName.split("_");
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < underscores.length; i++) {

				if (i == 0)
					buf.append(underscores[i]);
				else {
					char[] chars = underscores[i].toCharArray();
					chars[0] = Character.toUpperCase(underscores[i].charAt(0));
					buf.append(chars);
				}
			}
			columnName = buf.toString();
			for (int i = 0; i < props.length; i++) {

				if (columnName.equalsIgnoreCase(props[i].getName())) {
					columnToProperty[col] = i;
					break;

				} else {
					columnToProperty[col] = PROPERTY_NOT_FOUND;
				}
			}
		}

		return columnToProperty;
	}
}
