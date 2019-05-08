package com.chengxuxiaoba.video.util;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

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
		JsonNode jsonNode = convertToJsonNode(jsonStr);

		if(jsonNode == null)
			return null;

		T deserualizeObject = objectMapper.readValue(jsonNode.toString(), clazz);

		return deserualizeObject;
	}

	/**
	 * deserialize JSON String to be the specify genre
	 * @param jsonStr
	 * @param typeReference
	 * @param <T>
	 * @return
	 * @throws IOException
	 */
	public static <T> T deserializeJSONStr(String jsonStr, TypeReference typeReference) throws IOException {

		JsonNode jsonNode = convertToJsonNode(jsonStr);

		if(jsonNode == null)
			return null;

		T deserualizeObject = objectMapper.readValue(jsonNode.toString(), typeReference);

		return deserualizeObject;
	}

	public static List<String> convertToList(String arrayStr)
	{
		ObjectMapper mapper = new ObjectMapper();
		List<String> imageNameList = new ArrayList<>();
		try {
			JsonNode nameNode = mapper.readTree(arrayStr);
			for (JsonNode _node : nameNode) {
				imageNameList.add(_node.textValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageNameList;
	}

	/**
	 * convert To JsonNode object
	 * @param jsonStr
	 * @return
	 * @throws IOException
	 */
	private static JsonNode convertToJsonNode (String jsonStr)
			throws IOException {
		if (StringUtil.isNullOrEmpty(jsonStr))
			return null;

		JsonNode jsonNode = objectMapper.readTree(jsonStr);

		if (jsonNode == null)
			return null;

		return jsonNode;
	}
}
