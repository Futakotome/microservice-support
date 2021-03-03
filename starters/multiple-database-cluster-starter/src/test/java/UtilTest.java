import io.futakotome.MHDatasource.util.JdbcUrlExtractor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UtilTest {
    @Test
    public void testJdbcExtract() {
        JdbcUrlExtractor.JdbcUrlInformation information = JdbcUrlExtractor.extractFromJdbcUrl("jdbc:mysql://10.21.210.67:3306/daqin_infocenter_new");
        System.out.println(information.getHost());
        System.out.println(information.getPort());
        System.out.println(information.getDatabase());
        System.out.println(information.getDriverPlatform());
        System.out.println(information.getParams());
    }

}
