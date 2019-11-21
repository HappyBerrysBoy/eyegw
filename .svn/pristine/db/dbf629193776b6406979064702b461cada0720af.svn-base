package kr.gmtc.eyesmonitoring.eyesmonitoring;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;

class JSonParserTest {

	@Test
	/**
	 * [EyeSV_TSS_SpringBoot]의 [RestManager] Rest Controller에서 사용하고 있는 구조
	 * @throws Exception
	 */
	void testJsonObject() throws Exception {
		String jsonString = "{\"Header\":{\"Method\":\"get\",\"Time\":\"2019-10-22 16:42:28.423\",\"MsgGroup\":\"0\",\"MsgID\":\"1\", \"ApplicationServer\":\"EYESV_TSS\"},\"Data\":{}}";
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(jsonString);
		JSONObject json = (JSONObject) obj;
		
		JSONObject header = (JSONObject) json.get("Header");
		String msgId = (String) header.get("MsgID");
		
		System.out.println(msgId);
	}

}
