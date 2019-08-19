Scenario case1:输入正确用户名、密码
    When login
    【userName=admin】
    【userPass=123456】
    Then checkName
    【expectName=admin】
    Then checkMsg
    【expectMsg=操作成功】
Scenario case2:输入错误用户名
    When login
    【userName=admin1】
    【userPass=123456】
    Then checkMsg
    【expectMsg=用户名不存在！】
Scenario case3:输入错误密码
    When login
    【userName=admin】
    【userPass=1234567】
    Then checkMsg
    【expectMsg=密码不正确！】
Scenario case5:输入错误密码123
    When login
    【userName=admin】
    【userPass=1234567】
    Then checkMsg
    【expectMsg=密码不正确！】