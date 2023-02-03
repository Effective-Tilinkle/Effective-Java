package com.study.effectivejava.item1;

import java.sql.*;

public class ServiceProviderFramework {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver"); // 클래스 로드
        /*
        // 로드시 아래와 같은 초기화 진행

        com.mysql.cj.jdbc.Driver 소스중 일부

        static {
            try {
                java.sql.DriverManager.registerDriver(new Driver()); // 1
            } catch (SQLException E) {
                throw new RuntimeException("Can't register driver!");
            }
        }

        ...

        1. 여기서 DriverManager.registerDriver는 "제공자 등록 API"
            그리고 자기자신의 인스턴스를 파라미터로 제공해주는데, 이 Driver가 "서비스 제공자 인터페이스"이며, 이때 만들어지는 Driver 구현체가 벤더사마다 만들어지게된다
            그리고 Driver를 통해서 실질적인 Connection이 만들어지나, 사용자는 이 Driver를 직접 접근하거나 알 필요가없다.
        */

        Connection connection = DriverManager.getConnection("url", "user", "password");
        /*
        DriverManager.getConnection "서비스 접근 API".
        Connection은 "서비스 인터페이스" 이다. Connection을 만들기위해서는 위의 Driver 구현체를 통해서 만들어지기때문에 각 벤더사의 Driver가 필수적이며,
        여러 Driver들이 등록될 수 있기때문에 url에 따라 적절한 driver의 Connection을 전달해준다.
        driver.connect() 를 통해서 Connection을 리턴해주는데, 인터페이스를 리턴해주기때문에, 내부적으로 하나의 Connection의 구현체들(SINGLE_CONNECTION 용, FAILOVER_CONNECTION 용, REPLICATION_CONNECTION 용)을 용도에 맞게 커넥션 구현체들을 리턴해준다

        벤더사가 바로 Connection을 만들어서 등록할 수도 있겠지만(확장은 프록시 적절하게 사용하여..), Driver 라는 서비스 제공자 인터페이스를 둠으로써 서비스 제공자 프레임워크에서 필수적으로 필요로 하는 정보들을 정의하도록 할 수 있다. 프레임워크로써 동작하기수월하도록..
         */

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select~~");

    }
}
