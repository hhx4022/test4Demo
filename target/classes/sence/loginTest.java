package sence;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import org.test4j.module.spring.annotations.SpringBeanByName;
import org.test4j.spec.annotations.Named;
import org.test4j.spec.annotations.Then;
import org.test4j.spec.annotations.When;
import org.test4j.spec.inner.IScenario;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.HttpUtil;
import util.baseCase;

import java.util.HashMap;
import java.util.Map;

public class loginTest extends baseCase {

    @SpringBeanByName
    @Test(dataProvider = "story",groups = "login")
    public void runScenario(IScenario iScenario) throws Throwable {
        this.run(iScenario  );
    }
    String result=null;
    JSONObject jsonObject=null;



    @When
    public void login(final @Named( "userName" ) String userName,
                      final @Named( "userPass" ) String userPass) throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("userName" ,userName );
        map.put("userPass" ,userPass );
        System.out.println(loginUrl);
        result=HttpUtil.loginPost(loginUrl,map  );
        jsonObject= JSONObject.parseObject(result  );
        System.out.println(result);

    }
    @Then
    public void checkName(final @Named( "expectName" ) String expectName){
        String actualName=JsonPath.read( jsonObject,"$.data.userName" );
       // String actualMsg=JsonPath.read( jsonObject,"$.message" );
        Assert.assertEquals(expectName, actualName );
       // Assert.assertEquals(expectMsg, actualMsg );

    }
    @Then
    public void checkMsg(final @Named( "expectMsg" ) String expectMsg){
        //String actualName=JsonPath.read( jsonObject,"$.data.userName" );
        String actualMsg=JsonPath.read( jsonObject,"$.message" );

        Assert.assertEquals(expectMsg, actualMsg );

    }

}
