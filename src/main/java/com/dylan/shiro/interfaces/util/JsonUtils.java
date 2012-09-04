package com.dylan.shiro.interfaces.util;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import com.google.common.collect.Lists;
import com.youboy.util.ExceptionUtils;

public class JsonUtils {
	

	private static final ObjectMapper MAPPER = new ObjectMapper();
	static {
		MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		MAPPER.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		MAPPER.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
	}
	public static String toJsonString(Object bean) {
		try {
			return MAPPER.writeValueAsString(bean);
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}

	public static <T> T fromJsonString(String jsonString, Class<T> clazz) {
		try {
			return MAPPER.readValue(jsonString, clazz);
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}
	
	/*public static List<Parameters> mapToParameters(List<Map<String, String>> listMap) {
		List<Parameters> parameters = new ArrayList<Parameters>();
		for (Map<String, String> map : listMap) {
			Parameters parameter = MAPPER.convertValue(map, Parameters.class);
			parameters.add(parameter);
		}
		return parameters;
	}*/
	/**
	 * 
	 * @param listMap
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> mapToEntity(List<Map<String,String>> listMap,Class<T> clazz){
		List<T> tes = Lists.newArrayList();
		for(Map<String,String> map : listMap){
			T t = MAPPER.convertValue(map, clazz);
			tes.add(t);
		}
		return tes;
	}
}
