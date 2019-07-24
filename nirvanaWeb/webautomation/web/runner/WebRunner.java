package web.runner;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.ScreenshotException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import common.resources.CignitiProperties;
import common.utilities.ReportGeneration;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.Before;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.presentation.PresentationMode;

@CucumberOptions(features = "webautomation/web/features", tags = { "not @Ignore" }, monochrome = true, plugin = {
		"pretty", "html:target/cucumber-report/webresult", "json:target/cucumber-report/webresult.json",
		"rerun:target/webrerun.txt" }, glue = { "web/step_definition" }

)
public class WebRunner extends AbstractTestNGCucumberTests {

	static CignitiProperties properties;
	static WebDriver webDriver = null;
	DesiredCapabilities capabilities;
	ReportGeneration generateReport = new ReportGeneration();

	public WebDriver getWebDriver() {
		return webDriver;
	}

	@BeforeSuite
	public void connectToSauceLab() {
		properties = new CignitiProperties();

		String userName = properties.getProperty("userName");
		String accessKey = properties.getProperty("accessKey");
		String browserName = properties.getProperty("browserName");
		String webPlatform = properties.getProperty("webPlatform");
		String runOn = properties.getProperty("webRunAs");

		capabilities = new DesiredCapabilities();
		capabilities.setBrowserName(browserName);
		capabilities.setCapability("platform", webPlatform);

		if (runOn.trim().equalsIgnoreCase("Local")) {
			WebDriverManager.chromedriver().setup();
			webDriver = new ChromeDriver();
			webDriver.manage().window().maximize();
		} else {
			try {
				webDriver = new RemoteWebDriver(
						new URL("https://" + userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub"),
						capabilities);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Quit the webdriver
	 */
	@AfterSuite
	public void stopServer() {
		if (webDriver != null) {
			webDriver.quit();
		}
		generateReport.generateSummaryReport();
	}

	public void takeScreenshot(Scenario scenario) {
		try {
			final byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png"); // ... and embed it in the
														// report.
		} catch (ScreenshotException se) {
			se.getMessage();
		}
	}

	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}
}
