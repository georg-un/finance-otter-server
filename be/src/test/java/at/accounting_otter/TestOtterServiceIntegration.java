package at.accounting_otter;

import at.accounting_otter.entity.User;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


import javax.inject.Inject;

@RunWith(Arquillian.class)
public class TestOtterServiceIntegration {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class).addPackage("at.accounting_otter")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        // System.out.println("This: " + jar.toString(true)); TODO: refine
        return jar;
    }


    @Inject
    private OtterService otterService;

    @Test
    public void getUser1() {
        User user = otterService.getUser(15);

        System.out.println("Username: " + user.getUsername());

        Assert.assertNotNull(user);
    }


    /*
    @Test
    public void test1() {
        User user = otterService.createUser("test_user");

        Assert.assertNotNull( otterService.getUser(user.getUserId()) );
    }
    */
}
