package at.accounting_otter.util;

//import com.google.common.io.Resources;
import lombok.SneakyThrows;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.File;
import java.util.Properties;

/**
 * @author Stefan Paula
 */
public class TestHelper {

    public static File[] resolveDependencies(String mavenId) {
        return Maven.resolver()
                .resolve(mavenId)
                .withTransitivity()
                .asFile();
    }


    @SneakyThrows
    public static String resolveProjectVersion() {
        /*Properties properties = new Properties();
        properties.load(Resources.getResource("project.properties").openStream());
        return properties.getProperty("version");*/
        return "1.0-SNAPSHOT";
    }

}
