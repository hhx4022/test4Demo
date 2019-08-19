package sence;

import Dto.ServerDto;
import JDBCUtil.JdbcHelper;
import com.alibaba.fastjson.JSON;
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
import util.RandomUtil2;
import util.baseCase;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class createServerTest extends baseCase {
    @SpringBeanByName
    @Test(dataProvider = "story",groups = "createServer",description = "新增服务")
    public void runScenario(IScenario iScenario) throws Throwable {
        this.run(iScenario  );
    }

        String result=null;
        JSONObject jsonObject=null;
        String serverName=null;



    public void loginSys(final @Named( "userName" ) String userName,
                      final @Named( "userPass" ) String userPass) throws Exception {
       // login( userName, userPass);
        Map<String,Object> map=new HashMap<String, Object>(  );
        map.put( "userName", userName);
        map.put( "userPass", userPass);
        result=HttpUtil.loginPost(loginUrl,map  );
        System.out.println("=========="+result);
    }

    @When
    public void createServerTest(final @Named( "userName" ) String userName,
                                 final @Named( "userPass" ) String userPass,
                                 final @Named( "serverStatus" ) String serverStatus) throws Exception {
        serverName="自动化服务名" +RandomUtil2.generateOnlyNumber( 5 );
        ServerDto serverDto=new ServerDto();
        serverDto.setServerName( serverName );
        serverDto.setServerStatus(serverStatus  );
        Object Object= JSON.toJSON(serverDto  );
        System.out.println();
        result=HttpUtil.doPost(userName,userPass, createServerUrl,Object );
        jsonObject=JSONObject.parseObject(result  );
        System.out.println(result);

    }

    @Then
    public  void checkMsg(final @Named( "expectMsg" ) String expectMsg){
        String actualMsg=JsonPath.read( jsonObject,"$.message" );
        Assert.assertEquals(expectMsg, actualMsg );

    }

    @Then
    public void checkServerDB(final @Named( "sql" ) String sql) throws SQLException {
        Map<Integer,Object> map=new HashMap<Integer, Object>(  );
        map.put( 1,serverName );
        //map.put( 2,serverName );
        List resultlist=JdbcHelper.query( sql ,map);
        //JSONObject jsonObject= (JSONObject) JSON.toJSON( resultlist.get( 0 ) );
       // System.out.println(resultlist.toString());
        //System.out.println(jsonObject.toJSONString());
        Assert.assertEquals( 1,getResultCount(resultlist) );
        Assert.assertEquals( serverName,getMap( resultlist,"server_name" ) );
        //Assert.assertEquals( serverName);


    }

}
