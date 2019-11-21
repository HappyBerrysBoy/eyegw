package kr.gmtc.eyesmonitoring.eyesmonitoring;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

class HttpPostTest {

	@Test
	void test() throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:8081/tgw/statuses");
        httpPost.addHeader("custom-key", "test"); // 헤더 정보 테스트
        
        String input = "{\"Header\":{\"Method\":\"get\",\"Time\":\"2019-10-22 16:42:28.423\",\"MsgGroup\":\"0\",\"MsgID\":\"1\", \"ApplicationServer\":\"EYESV_TSS\"},\"Data\":{}}";
        ByteArrayEntity entity = new ByteArrayEntity(input.getBytes(), ContentType.APPLICATION_JSON);
        
        httpPost.setEntity(entity);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
	}

}
