package tests.superadmin;

import base.BaseTest;
import constants.UserRole;
import io.qameta.allure.*;
import listeners.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.superadmin.SuperAdminPage;

@Epic("Role-Based Access")
@Feature("Super Admin")
public class SuperAdminTest extends BaseTest {

    @Test(groups = {"super_admin", "smoke"}, description = "Super Admin can login and see admin panel",
          retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.BLOCKER)
    public void testSuperAdminLogin() {
        LoginPage login = new LoginPage(driver);
        login.loginAs(UserRole.SUPER_ADMIN);

        DashboardPage dashboard = new DashboardPage(driver);
        Assert.assertTrue(dashboard.isDashboardLoaded(), "Dashboard should load for Super Admin");
    }

    @Test(groups = {"super_admin", "regression"}, description = "Super Admin can access Manage Users")
    @Severity(SeverityLevel.CRITICAL)
    public void testSuperAdminManageUsers() {
        new LoginPage(driver).loginAs(UserRole.SUPER_ADMIN);

        SuperAdminPage adminPage = new SuperAdminPage(driver);
        Assert.assertTrue(adminPage.isSuperAdminPanelVisible(), "Super Admin panel should be visible");

        adminPage.openManageUsers();
        Assert.assertTrue(adminPage.isUserListDisplayed(), "User list should be displayed");
    }

    @Test(groups = {"super_admin", "regression"}, description = "Super Admin can access System Settings")
    @Severity(SeverityLevel.NORMAL)
    public void testSuperAdminSystemSettings() {
        new LoginPage(driver).loginAs(UserRole.SUPER_ADMIN);
        new SuperAdminPage(driver).openSystemSettings();
        Assert.assertTrue(driver.getCurrentUrl().contains("settings"), "Should navigate to settings");
    }

    @Test(groups = {"super_admin", "regression"}, description = "Super Admin can view Audit Logs")
    @Severity(SeverityLevel.NORMAL)
    public void testSuperAdminAuditLogs() {
        new LoginPage(driver).loginAs(UserRole.SUPER_ADMIN);
        new SuperAdminPage(driver).openAuditLogs();
        Assert.assertTrue(driver.getCurrentUrl().contains("audit"), "Should navigate to audit logs");
    }

    @Test(groups = {"super_admin", "smoke"}, description = "Super Admin can logout successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void testSuperAdminLogout() {
        new LoginPage(driver).loginAs(UserRole.SUPER_ADMIN);
        new DashboardPage(driver).logout();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Should return to login after logout");
    }
}
