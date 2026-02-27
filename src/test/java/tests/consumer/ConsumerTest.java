package tests.consumer;

import base.BaseTest;
import constants.UserRole;
import io.qameta.allure.*;
import listeners.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.consumer.postpaid.ConsumerPostpaidPage;
import pages.consumer.prepaid.ConsumerPrepaidPage;

@Epic("Role-Based Access")
@Feature("Consumer Roles")
public class ConsumerTest extends BaseTest {

    // ── Consumer Prepaid ──────────────────────────────────────

    @Test(groups = {"consumer_prepaid", "smoke"}, description = "Consumer Prepaid login",
          retryAnalyzer = RetryAnalyzer.class)
    @Story("Consumer Prepaid")
    @Severity(SeverityLevel.BLOCKER)
    public void testConsumerPrepaidLogin() {
        new LoginPage(driver).loginAs(UserRole.CONSUMER_PREPAID);
        Assert.assertTrue(new DashboardPage(driver).isDashboardLoaded(), "Dashboard should load for Consumer Prepaid");
    }

    @Test(groups = {"consumer_prepaid", "regression"}, description = "Consumer Prepaid can see balance")
    @Story("Consumer Prepaid")
    @Severity(SeverityLevel.CRITICAL)
    public void testConsumerPrepaidBalance() {
        new LoginPage(driver).loginAs(UserRole.CONSUMER_PREPAID);
        ConsumerPrepaidPage page = new ConsumerPrepaidPage(driver);
        Assert.assertTrue(page.isConsumerPrepaidPanelVisible(), "Consumer Prepaid panel should be visible");
        Assert.assertTrue(page.isBalanceSectionVisible(), "Balance section should be visible");
    }

    @Test(groups = {"consumer_prepaid", "regression"}, description = "Consumer Prepaid can navigate to Recharge")
    @Story("Consumer Prepaid")
    @Severity(SeverityLevel.NORMAL)
    public void testConsumerPrepaidRecharge() {
        new LoginPage(driver).loginAs(UserRole.CONSUMER_PREPAID);
        new ConsumerPrepaidPage(driver).clickRecharge();
        Assert.assertTrue(driver.getCurrentUrl().contains("recharge"), "Should navigate to recharge page");
    }

    @Test(groups = {"consumer_prepaid", "regression"}, description = "Consumer Prepaid can view Plans")
    @Story("Consumer Prepaid")
    @Severity(SeverityLevel.NORMAL)
    public void testConsumerPrepaidViewPlans() {
        new LoginPage(driver).loginAs(UserRole.CONSUMER_PREPAID);
        new ConsumerPrepaidPage(driver).clickViewPlans();
        Assert.assertTrue(driver.getCurrentUrl().contains("plans"), "Should navigate to plans page");
    }

    @Test(groups = {"consumer_prepaid", "smoke"}, description = "Consumer Prepaid logout")
    @Story("Consumer Prepaid")
    @Severity(SeverityLevel.CRITICAL)
    public void testConsumerPrepaidLogout() {
        new LoginPage(driver).loginAs(UserRole.CONSUMER_PREPAID);
        new DashboardPage(driver).logout();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Should return to login");
    }

    // ── Consumer Postpaid ─────────────────────────────────────

    @Test(groups = {"consumer_postpaid", "smoke"}, description = "Consumer Postpaid login",
          retryAnalyzer = RetryAnalyzer.class)
    @Story("Consumer Postpaid")
    @Severity(SeverityLevel.BLOCKER)
    public void testConsumerPostpaidLogin() {
        new LoginPage(driver).loginAs(UserRole.CONSUMER_POSTPAID);
        Assert.assertTrue(new DashboardPage(driver).isDashboardLoaded(), "Dashboard should load for Consumer Postpaid");
    }

    @Test(groups = {"consumer_postpaid", "regression"}, description = "Consumer Postpaid can see Bill")
    @Story("Consumer Postpaid")
    @Severity(SeverityLevel.CRITICAL)
    public void testConsumerPostpaidBill() {
        new LoginPage(driver).loginAs(UserRole.CONSUMER_POSTPAID);
        ConsumerPostpaidPage page = new ConsumerPostpaidPage(driver);
        Assert.assertTrue(page.isConsumerPostpaidPanelVisible(), "Consumer Postpaid panel should be visible");
        Assert.assertTrue(page.isBillSectionVisible(), "Bill section should be visible");
    }

    @Test(groups = {"consumer_postpaid", "regression"}, description = "Consumer Postpaid can view Usage")
    @Story("Consumer Postpaid")
    @Severity(SeverityLevel.NORMAL)
    public void testConsumerPostpaidUsage() {
        new LoginPage(driver).loginAs(UserRole.CONSUMER_POSTPAID);
        new ConsumerPostpaidPage(driver).clickViewUsage();
        Assert.assertTrue(driver.getCurrentUrl().contains("usage"), "Should navigate to usage page");
    }

    @Test(groups = {"consumer_postpaid", "smoke"}, description = "Consumer Postpaid logout")
    @Story("Consumer Postpaid")
    @Severity(SeverityLevel.CRITICAL)
    public void testConsumerPostpaidLogout() {
        new LoginPage(driver).loginAs(UserRole.CONSUMER_POSTPAID);
        new DashboardPage(driver).logout();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Should return to login");
    }
}
