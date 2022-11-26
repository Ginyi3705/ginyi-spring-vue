package ginyi.server.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
public class Test1 {

    @Autowired
    private DataSource dataSource;

    @Test
    public void test1() throws SQLException {
        System.out.println("====>>>" + dataSource.getConnection());
    }
}
