Scenario case1:登录后新增服务，状态=空闲
    When createServerTest
    【userName=admin】
    【userPass=123456】
    【serverStatus=free】
    Then checkMsg
    【expectMsg=操作成功】
    Then checkServerDB
    【sql="select server_name,server_status from server where server_name =? and server_status='free'"】
Scenario case2:登录后新增服务，状态=使用中
    When createServerTest
    【userName=admin】
    【userPass=123456】
    【serverStatus=used】
     Then checkMsg
    【expectMsg=操作成功】
    Then checkServerDB
    【sql="select * from server where server_name =? and server_status='used'"】