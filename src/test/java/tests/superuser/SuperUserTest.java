package tests.superuser;

import base.BaseTest;
import constants.UserRole;
import io.qameta.allure.*;
import listeners.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.superuser.SuperUserPage;

@Epic("Role-Based Access")
@Feature("Super User")
public class SuperUserTest extends BaseTest {

    @Test(groups = {"super_user", "smoke"}, description = "Super User can login successfully",
          retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.BLOCKER)
    public void testSuperUserLogin() {
        new LoginPage(driver).loginAs(UserRole.SUPER_USER);
        Assert.assertTrue(new DashboardPage(driver).isDashboardLoaded(), "Dashboard should load for Super User");
    }

    @Test(groups = {"super_user", "regression"}, description = "Super User can access Reports")
    @Severity(SeverityLevel.CRITICAL)
    public void testSuperUserReports() {
        new LoginPage(driver).loginAs(UserRole.SUPER_USER);
        SuperUserPage superUserPage = new SuperUserPage(driver);
        Assert.assertTrue(superUserPage.isSuperUserPanelVisible(), "Super User panel should be visible");
        superUserPage.openReports();
        Assert.assertTrue(superUserPage.isReportsTableVisible(), "Reports table should display");
    }

    @Test(groups = {"super_user", "regression"}, description = "Super User can manage Admins")
    @Severity(SeverityLevel.NORMAL)
    public void testSuperUserManageAdmins() {
        new LoginPage(driver).loginAs(UserRole.SUPER_USER);
        new SuperUserPage(driver).openManageAdmins();
        Assert.assertTrue(driver.getCurrentUrl().contains("admins"), "Should navigate to admins page");
    }

    @Test(groups = {"super_user", "smoke"}, description = "Super User logout")
    @Severity(SeverityLevel.CRITICAL)
    public void testSuperUserLogout() {
        new LoginPage(driver).loginAs(UserRole.SUPER_USER);
        new DashboardPage(driver).logout();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Should return to login");
    }
}
