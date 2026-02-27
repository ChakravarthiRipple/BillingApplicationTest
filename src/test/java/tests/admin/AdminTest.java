package tests.admin;

import base.BaseTest;
import constants.UserRole;
import io.qameta.allure.*;
import listeners.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.admin.postpaid.AdminPostpaidPage;
import pages.admin.prepaid.AdminPrepaidPage;

@Epic("Role-Based Access")
@Feature("Admin Roles")
public class AdminTest extends BaseTest {

    // ── Admin Prepaid ─────────────────────────────────────────

    @Test(groups = {"admin_prepaid", "smoke"}, description = "Admin Prepaid login",
          retryAnalyzer = RetryAnalyzer.class)
    @Story("Admin Prepaid")
    @Severity(SeverityLevel.BLOCKER)
    public void testAdminPrepaidLogin() {
        new LoginPage(driver).loginAs(UserRole.ADMIN_PREPAID);
        Assert.assertTrue(new DashboardPage(driver).isDashboardLoaded(), "Dashboard should load for Admin Prepaid");
    }

    @Test(groups = {"admin_prepaid", "regression"}, description = "Admin Prepaid can view Prepaid Plans")
    @Story("Admin Prepaid")
    @Severity(SeverityLevel.CRITICAL)
    public void testAdminPrepaidViewPlans() {
        new LoginPage(driver).loginAs(UserRole.ADMIN_PREPAID);
        AdminPrepaidPage page = new AdminPrepaidPage(driver);
        Assert.assertTrue(page.isAdminPrepaidPanelVisible(), "Admin Prepaid panel should be visible");
        page.openPrepaidPlans();
        Assert.assertTrue(page.isPlansTableVisible(), "Prepaid plans table should be visible");
    }

    @Test(groups = {"admin_prepaid", "regression"}, description = "Admin Prepaid can manage consumers")
    @Story("Admin Prepaid")
    @Severity(SeverityLevel.NORMAL)
    public void testAdminPrepaidManageConsumers() {
        new LoginPage(driver).loginAs(UserRole.ADMIN_PREPAID);
        new AdminPrepaidPage(driver).openManageConsumers();
        Assert.assertTrue(driver.getCurrentUrl().contains("consumers"), "Should navigate to consumers");
    }

    @Test(groups = {"admin_prepaid", "smoke"}, description = "Admin Prepaid logout")
    @Story("Admin Prepaid")
    @Severity(SeverityLevel.CRITICAL)
    public void testAdminPrepaidLogout() {
        new LoginPage(driver).loginAs(UserRole.ADMIN_PREPAID);
        new DashboardPage(driver).logout();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Should return to login");
    }

    // ── Admin Postpaid ────────────────────────────────────────

    @Test(groups = {"admin_postpaid", "smoke"}, description = "Admin Postpaid login",
          retryAnalyzer = RetryAnalyzer.class)
    @Story("Admin Postpaid")
    @Severity(SeverityLevel.BLOCKER)
    public void testAdminPostpaidLogin() {
        new LoginPage(driver).loginAs(UserRole.ADMIN_POSTPAID);
        Assert.assertTrue(new DashboardPage(driver).isDashboardLoaded(), "Dashboard should load for Admin Postpaid");
    }

    @Test(groups = {"admin_postpaid", "regression"}, description = "Admin Postpaid can view Billing")
    @Story("Admin Postpaid")
    @Severity(SeverityLevel.CRITICAL)
    public void testAdminPostpaidBilling() {
        new LoginPage(driver).loginAs(UserRole.ADMIN_POSTPAID);
        AdminPostpaidPage page = new AdminPostpaidPage(driver);
        Assert.assertTrue(page.isAdminPostpaidPanelVisible(), "Admin Postpaid panel should be visible");
        page.openBilling();
        Assert.assertTrue(page.isBillingTableVisible(), "Billing table should be visible");
    }

    @Test(groups = {"admin_postpaid", "regression"}, description = "Admin Postpaid can view Invoices")
    @Story("Admin Postpaid")
    @Severity(SeverityLevel.NORMAL)
    public void testAdminPostpaidInvoices() {
        new LoginPage(driver).loginAs(UserRole.ADMIN_POSTPAID);
        new AdminPostpaidPage(driver).openInvoices();
        Assert.assertTrue(driver.getCurrentUrl().contains("invoice"), "Should navigate to invoices");
    }

    @Test(groups = {"admin_postpaid", "smoke"}, description = "Admin Postpaid logout")
    @Story("Admin Postpaid")
    @Severity(SeverityLevel.CRITICAL)
    public void testAdminPostpaidLogout() {
        new LoginPage(driver).loginAs(UserRole.ADMIN_POSTPAID);
        new DashboardPage(driver).logout();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Should return to login");
    }
}
