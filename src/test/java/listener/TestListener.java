package listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult iTestResult) {
        logTestStart( iTestResult );
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        logTestEnd( iTestResult,"Success" );
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        logTestEnd( iTestResult,"Failed" );
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        logTestEnd( iTestResult,"Skipped" );

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        logTestEnd( iTestResult,"onTestFailedButWithinSuccessPercentage" );
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        return;
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        return;
    }

    // 在用例执行结束时，打印用例的执行结果信息
    protected void logTestEnd(ITestResult tr, String result) {
        Reporter.log(String.format("=============执行结果: %s=============", result), true);
    }

    // 在用例开始时，打印用例的一些信息，比如@Test对应的方法名，用例的描述等等
    protected void logTestStart(ITestResult tr) {
        Reporter.log(String.format("=============执行用力方法: %s===============", tr.getMethod()), true);
       // Reporter.log(String.format("用例描述: %s, 优先级: %s", tr.getMethod().getDescription(), tr.getMethod().getPriority()),
              //  true);
        return;
    }
}
