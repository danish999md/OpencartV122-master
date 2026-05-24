package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{

	
	public HomePage(WebDriver driver)
	{
		super(driver);
	}
	
@FindBy(xpath="//span[normalize-space()='My Account']") 
WebElement lnkMyaccount;

@FindBy(xpath="//a[normalize-space()='Register']") 
WebElement lnkRegister;

@FindBy(linkText = "Login")   // Login link added in step5
WebElement linkLogin;

@FindBy(xpath="//input[@placeholder='Search']")
WebElement txtSearchBox;

@FindBy(xpath="//button[@class='btn btn-default btn-lg']")
WebElement btnSearch;

@FindBy(xpath=
"//p[contains(text(),'There is no product')]")
WebElement noProductMessage;


public void clickMyAccount()
{
	lnkMyaccount.click();
}

public void clickRegister()
{
	lnkRegister.click();
}

public void clickLogin()
{
	linkLogin.click();
}

public void clickSearchBar(String productName)
{
    txtSearchBox.clear();

    txtSearchBox.sendKeys(productName);
}

public void ClickSearchbtn()
{
    btnSearch.click();
}

public String getNoProductMessage()
{
    return noProductMessage.getText();
}

}
