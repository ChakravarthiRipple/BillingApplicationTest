# Role-Based Selenium Automation Framework

## Import into Eclipse
1. File → Import → Maven → Existing Maven Projects
2. Browse to this folder → Finish
3. Right-click project → Maven → Update Project (downloads all dependencies)

## Configure Your Application
Edit `src/test/resources/config.properties`:
- Set `base.url` to your application URL
- Fill in credentials for all 6 roles
- Set `headless=false` for local runs, `true` for Jenkins

## Update Page Locators
In each Page class, update `@FindBy` locators to match your actual application HTML.

## Run Tests

### From Eclipse
Right-click any `.xml` → Run As → TestNG Suite

### From Terminal
```bash
mvn test                              # runs all-roles suite
mvn test -Dsuite=smoke               # smoke tests only
mvn test -Dsuite=regression          # regression tests only
mvn test -Dheadless=true             # headless Chrome (for CI)
```

## Push to GitHub
```bash
git init
git add .
git commit -m "Initial framework setup"
git remote add origin https://github.com/YOUR_USERNAME/selenium-framework.git
git push -u origin main
```

## Jenkins Setup (Daily at 6 AM)
1. Install plugins: Git, Maven Integration, Allure, TestNG Results
2. New Item → Pipeline
3. Pipeline → Pipeline script from SCM → Git
4. Repo URL: your GitHub repo URL
5. Script Path: `Jenkinsfile`
6. The `cron('0 6 * * *')` trigger runs automatically every day at 6:00 AM

## Reports
- Extent HTML: `test-output/ExtentReport_<timestamp>.html`
- Allure:       run `allure serve allure-results`
- Logs:         `test-output/automation.log`

## Roles Covered
| Role             | Group            | Test Class        |
|------------------|------------------|-------------------|
| Super Admin      | super_admin      | SuperAdminTest    |
| Super User       | super_user       | SuperUserTest     |
| Admin Prepaid    | admin_prepaid    | AdminTest         |
| Admin Postpaid   | admin_postpaid   | AdminTest         |
| Consumer Prepaid | consumer_prepaid | ConsumerTest      |
| Consumer Postpaid| consumer_postpaid| ConsumerTest      |
