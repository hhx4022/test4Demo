package JDBCUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class testDB {
    public static void main(String[] args) throws SQLException {
        String sql="select * from user where user_name=? and user_pass=?";
        Map<Integer,Object> map=new HashMap<Integer, Object>(  );
        map.put(1,"admin" );
        map.put(2,"1234567" );

//        String sql="select * from user where user_name=? and user_pass=?";
//        Map<Integer,Object> map=new HashMap<Integer, Object>(  );
//        map.put(1,"admin" );
//        map.put(2,"1234567" );


        List list=JdbcHelper.query( "select * from user" );
        List list2=JdbcHelper.query(sql, map );

        System.out.println(list2.toString());
    }
}
