package qss.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class JsonUtil {

	private final static ObjectMapper mapper = new ObjectMapper();
	private final static ObjectMapper prettyMapper = new ObjectMapper();

	static {
		prettyMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
	}

	public static String encode(Object obj) throws Exception {
		return mapper.writeValueAsString(obj);
	}

	public static String encodePrettily(Object obj) throws Exception {
		return prettyMapper.writeValueAsString(obj);
	}

	@SuppressWarnings("unchecked")
	public static <T> T decodeValue(String str, Class<?> clazz)
			throws Exception {
		return (T) mapper.readValue(str, clazz);
	}

}