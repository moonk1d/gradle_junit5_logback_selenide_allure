package tests;

import static com.codeborne.selenide.Selenide.open;

import hooks.ConfigurationSetupHook;
import hooks.LogWatcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.HomePage;
import pages.SignInPage;

@ExtendWith({LogWatcher.class, ConfigurationSetupHook.class})
public class GrinferLoginTest {

  @Test
  public void loginHappyPass() {
    HomePage homePage = open("", HomePage.class);
    homePage.clickLinkByText("Log In");
    SignInPage signInPage = new SignInPage();
    signInPage.isLoaded();
  }

  @Test
  public void loginFailure() {
    // expected to fail
    SignInPage signInPage = open("/sign-in", SignInPage.class);
    signInPage.clickLinkByText("Log In");
  }

}
