package com.chengxuxiaoba.video.util;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ReferenceType;

public class JSONUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * deserialize JSON String to be the specify genre
	 * @param jsonStr
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static <T> T deserializeJSONStr(String jsonStr, Class<T> clazz) throws IOException {

		if (StringUtil.isNullOrEmpty(jsonStr))
			return null;

		JsonNode jsonNode = objectMapper.readTree(jsonStr);

		if (jsonNode == null)
			return null;

		T deserualizeObject = objectMapper.readValue(jsonNode.toString(), clazz);

		return deserualizeObject;
	}
	/**
	 * deserialize JSON String to be the specify genre
	 * @param jsonStr
	 * @param referenceType
	 * @return
	 * @throws IOException
	 */
	public static <T> T deserializeJSONStr(String jsonStr, TypeReference typeReference) throws IOException {

		if (StringUtil.isNullOrEmpty(jsonStr))
			return null;

		JsonNode jsonNode = objectMapper.readTree(jsonStr);

		if (jsonNode == null)
			return null;

		T deserualizeObject = objectMapper.readValue(jsonNode.toString(), typeReference);

		return deserualizeObject;
	}
}
