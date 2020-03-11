package service;

import org.json.JSONObject;

import com.baidu.util.Base64Util;
import com.baidu.util.FileUtil;
import com.baidu.util.HttpUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class BaiduService {
	 /**
     * ��ȡȨ��token
     * @return ����ʾ����
     * {
     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
     * "expires_in": 2592000
     * }
     */
    public static String gettoken() {
        // ������ȡ�� API Key ����Ϊ��ע���
        String clientId = "FnpTD8PPyFG4G3jpXv4Xk1m3";
        // ������ȡ�� Secret Key ����Ϊ��ע���
        String clientSecret = "RcAj8EM3qO6CMlgIsNDtnUuWEe0mKFnp";
        return getAuth(clientId, clientSecret);
    }

    /**
     * ��ȡAPI����token
     * ��token��һ������Ч�ڣ���Ҫ���й�����ʧЧʱ�����»�ȡ.
     * @param ak - �ٶ��ƹ�����ȡ�� API Key
     * @param sk - �ٶ��ƹ�����ȡ�� Securet Key
     * @return assess_token ʾ����
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth(String ak, String sk) {
        // ��ȡtoken��ַ
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_typeΪ�̶�����
                + "grant_type=client_credentials"
                // 2. ������ȡ�� API Key
                + "&client_id=" + ak
                // 3. ������ȡ�� Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // �򿪺�URL֮�������
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // ��ȡ������Ӧͷ�ֶ�
            Map<String, List<String>> map = connection.getHeaderFields();
            // �������е���Ӧͷ�ֶ�
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // ���� BufferedReader����������ȡURL����Ӧ
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * ���ؽ��ʾ��
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("��ȡtokenʧ�ܣ�");
            e.printStackTrace(System.err);
        }
        return null;
    }
    
 
    public static String getnumbers(String filePath) {
        // ����url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/numbers";
        try {
            // �����ļ�·��
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // ע�������Ϊ�˼򻯱���ÿһ������ȥ��ȡaccess_token�����ϻ���access_token�й���ʱ�䣬 �ͻ��˿����л��棬���ں����»�ȡ��
            String accessToken = gettoken();

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getnumbersByIocr(String filePath){
    	String recogniseUrl = "https://aip.baidubce.com/rest/2.0/solution/v1/iocr/recognise";


        try {
                byte[] imgData = FileUtil.readFileByBytes(filePath);
                String imgStr = Base64Util.encode(imgData);
                // ����ģ�����
                String recogniseParams = "templateSign=121cad8a2f3b263f930e65b068e7bd84&image=" + URLEncoder.encode(imgStr, "UTF-8");
                // �������������
                String classifierParams = "classifierId=your_classfier_id&image=" + URLEncoder.encode(imgStr, "UTF-8");
                
                
                String accessToken = gettoken();
                // ����ģ��ʶ��
                String result = HttpUtil.post(recogniseUrl, accessToken, recogniseParams);
                // ���������ʶ��
                // String result = HttpUtil.post(recogniseUrl, accessToken, classifierParams);
                System.out.println(result);
                return result;
        } catch (Exception e) {
                e.printStackTrace();
                return null;
        }
		
    	
    }
}
