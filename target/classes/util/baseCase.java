package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.test4j.module.spring.annotations.SpringBeanByName;
import org.test4j.spec.inner.IScenario;
import org.test4j.testng.JSpec;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class baseCase  extends JSpec {
    public static String  baseUrl="http://localhost:8081/api";
    public static String loginUrl=baseUrl+"/user/login";
    public static String createServerUrl=baseUrl+"/server/create";


    @SpringBeanByName
    @Test(dataProvider = "story")
    public void runScenario(IScenario iScenario) throws Throwable {
        this.run(iScenario  );
    }

    public void login(String userName,String userPass) throws Exception {
        Map<String,Object> map=new HashMap<String,Object>(  );
        map.put( "userName", userName);
        map.put( "userPass", userPass);
        String result=HttpClientUtils.doPost(loginUrl,map  );
        System.out.println("=========="+result);

    }

    public String getMap(List list, String key){
        JSONObject jsonObject= (JSONObject) JSON.toJSON( list.get( 0 ) );
        String  result=jsonObject.get( key ).toString();
        System.out.println("result===="+result);
        return result;

    }

    public int getResultCount(List list){
        return list.size();

    }
}
