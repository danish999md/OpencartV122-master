package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String reportName;

    // =========================
    // REPORT INITIALIZATION
    // =========================

    @Override
    public void onStart(ITestContext context) {

        String timeStamp =
                new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
                        .format(new Date());

        reportName = "Test-Report-" + timeStamp + ".html";

        sparkReporter =
                new ExtentSparkReporter(
                        System.getProperty("user.dir")
                                + "\\reports\\"
                                + reportName);

        // Report UI Config

        sparkReporter.config().setDocumentTitle("Automation Report");

        sparkReporter.config().setReportName("OpenCart Functional Testing");

        sparkReporter.config().setTheme(Theme.DARK);

        // Attach Report

        extent = new ExtentReports();

        extent.attachReporter(sparkReporter);

        // System Info

        extent.setSystemInfo("Application", "OpenCart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Tester", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        String os =
                context.getCurrentXmlTest()
                        .getParameter("os");

        extent.setSystemInfo("Operating System", os);

        String browser =
                context.getCurrentXmlTest()
                        .getParameter("browser");

        extent.setSystemInfo("Browser", browser);

        List<String> groups =
                context.getCurrentXmlTest()
                        .getIncludedGroups();

        if (!groups.isEmpty()) {

            extent.setSystemInfo("Groups", groups.toString());
        }
    }

    // =========================
    // TEST START
    // =========================

    @Override
    public void onTestStart(ITestResult result) {

        test =
                extent.createTest(
                        result.getMethod().getMethodName());

        test.assignCategory(
                result.getMethod().getGroups());
    }

    // =========================
    // TEST SUCCESS
    // =========================

    @Override
    public void onTestSuccess(ITestResult result) {

        test.log(
                Status.PASS,
                result.getName() + " executed successfully");
    }

    // =========================
    // TEST FAILURE
    // =========================

    @Override
    public void onTestFailure(ITestResult result) {

        test.log(
                Status.FAIL,
                result.getName() + " got failed");

        test.log(
                Status.INFO,
                result.getThrowable());

        try {

            String imgPath =
                    BaseClass.captureScreen(
                            result.getName());

            if (imgPath != null) {

                test.addScreenCaptureFromPath(imgPath);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =========================
    // TEST SKIPPED
    // =========================

    @Override
    public void onTestSkipped(ITestResult result) {

        test.log(
                Status.SKIP,
                result.getName() + " got skipped");

        test.log(
                Status.INFO,
                result.getThrowable());
    }

    // =========================
    // REPORT FINISH
    // =========================

    @Override
    public void onFinish(ITestContext context) {

        extent.flush();

        String reportPath =
                System.getProperty("user.dir")
                        + "\\reports\\"
                        + reportName;

        File extentReport = new File(reportPath);

        try {

            Desktop.getDesktop()
                    .browse(extentReport.toURI());

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}