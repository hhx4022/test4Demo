import util.HttpClientUtils;

import java.util.HashMap;
import java.util.Map;

public class testHttpClient {

    public static void main(String[] args) {
        try {
        //String url="https://www.netcourt.gov.cn/suit/caseSearchRpc/filterCondition.json";
        String url="http://localhost:8080/api/user/login";
        //Map<String,Object> map=new HashMap<String, Object>( );
        //HttpClientUtils.doGet( "https://clm.chexiangpre.com/");
        //HttpClientUtils.doGet( "https://clm.chexiangpre.com/catpchaCode.json");
        Map<String, Object> content=new HashMap<String, Object>(  );
        content.put( "userName","admin" );
        content.put( "userPass","123456");




            String result = HttpClientUtils.doPost( url,content );
            System.out.println(result);
           // System.out.println(DigestUtils.md5Hex("1"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
