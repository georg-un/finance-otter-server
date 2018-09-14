package at.accounting_otter;

import at.accounting_otter.entity.User;
import at.accounting_otter.util.TestHelper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.wildfly.swarm.arquillian.DefaultDeployment;

import javax.inject.Inject;
import java.io.File;

@RunWith(Arquillian.class)
@DefaultDeployment(type = DefaultDeployment.Type.JAR)
public class TestOtterServiceIntegration {

    /*
    @Deployment
    public static WebArchive create() {
        return ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(fetchLibraries())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    private static File[] fetchLibraries() {
        return TestHelper.resolveDependencies("at.accounting_otter:be:"+TestHelper.resolveProjectVersion());
    }
    */


    /*
    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class).addPackage("at.accounting_otter")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println("This: " + jar.toString(true));
        return jar;
    }
    */

    /*
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClass(OtterService.class);
    }
    */


    @Inject
    private OtterService otterService;

    @Test
    public void test1() {
        User user = otterService.createUser("test_user");

        Assert.assertNotNull( otterService.getUser(user.getUserId()) );
    }
}
