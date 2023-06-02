package qss.util;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.StringTypeHandler;

@MappedTypes(value = String.class)
@MappedJdbcTypes(value = { JdbcType.CHAR, JdbcType.VARCHAR })
public class StringCodingHandler extends StringTypeHandler {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
		try {
			parameter = new String(parameter.getBytes("UTF-8"), "iso8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ps.setString(i, parameter);
	}

	@Override
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String str = "";
		try {
			if (rs.getString(columnName) != null) {
				str = new String(rs.getBytes(columnName), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String str = "";
		try {
			str = new String(rs.getBytes(columnIndex), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String str = "";
		try {
			str = new String(cs.getBytes(columnIndex), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

}