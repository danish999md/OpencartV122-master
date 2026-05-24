package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import testBase.BaseClass;

public class TC004_SearchTest extends BaseClass {

    @Test(groups = {"regression","master"})

    public void verify_search_negative() {

        logger.info(
                "**** Starting Negative Search Test ****");

        try {

            HomePage hp =
                    new HomePage(driver);

            // Invalid product name

            String invalidProduct =
                    "xyzinvalidproduct123";

            logger.info(
                    "Entering invalid product name");

            hp.clickSearchBar(invalidProduct);

            logger.info(
                    "Clicking search button");

            hp.ClickSearchbtn();

            // Validation

            //String expectedMessage =
              //      "There is no product that matches the search criteria.";

            String actualMessage =
                    hp.getNoProductMessage();

            logger.info(
                    "Validating error message");

            Assert.assertEquals(
                    actualMessage,
                    "Wrong Error Message");

            logger.info(
                    "Negative Search Test Passed");

        } catch (Exception e) {

            logger.error(
                    "Negative Search Test Failed : "
                            + e.getMessage());

            Assert.fail();

        } finally {

            logger.info(
                    "**** Finished Negative Search Test ****");
        }
    }
}