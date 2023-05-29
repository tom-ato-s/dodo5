

import Pages.ContactsPage;
import Pages.MainPage;
import Pages.SityPage;
import io.qameta.allure.*;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
@Epic("Тестирование сайта пиццерии ДоДоПица")
@Link("https://dodopizza.ru")
@Feature("Step1: Перейти на 'https://dodopizza.ru/'" +
        "Step2: Нажать на кнопку “Контакты”" +
        "Step3: Проверить что телефон и email верны и отображаются в блоке")
public class TestsСase_3 {
    private static WebDriver driver;
    private MainPage mainPage;
    private SityPage sityPage;
    private ContactsPage contactsPage;
    WebDriverWait webDriverWait;

    @BeforeAll
    public static void start() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    @Order(1)
    @Issue(value = "Case_0")
    @Owner("Архипова Надежда")
    @DisplayName("Step1: Проверка открытия первой страницы")
    @Description("Проверка: открытия первой станицы сайта" +
            "Нажатие на регион во всплывающем окне. Выбрать регион «Москва»")
    public void test1() throws InterruptedException {
        System.out.println("1dfgdfgdf");
        mainPage = new MainPage(driver);
        sityPage = new SityPage(driver);

        driver.get(ConfProperties.getProperty("syteDodoPizza"));
        Thread.sleep(5000);
        Assertions.assertNotNull(driver.getTitle());
        Thread.sleep(500);
        sityPage.clickCityBtn();
    }


    @Test
    @Order(2)
    @Issue(value = "Case_3")
    @Owner("Архипова Надежда")
    @DisplayName("Step2: Нажать на кнопку “Контакты”")
    @Description("Нажать на кнопку “Контакты”" +
            "Проверка открытия страницы “Контакты”")
    public void test2 () throws InterruptedException {
        contactsPage = new ContactsPage(driver);
        mainPage = new MainPage(driver);

//        webDriverWait = new WebDriverWait(driver, 15);
//        webDriverWait.until(ExpectedConditions.visibilityOf(mainPage.getElementContact()));
        // Нажать на кнопку “Контакты”
        mainPage.clickBtnContacts();
        // Проверка открытия страницы “Контакты”
        Assertions.assertNotNull(driver.getTitle());
    }
    @Test
    @Order(3)
    @Issue(value = "Case_3")
    @Owner("Архипова Надежда")
    @DisplayName("Step3: Проверить что телефон и email верны и отображаются в блоке")
    @Description("Сравить значение поля «Телефон» и Значения = 8 800 302-00-60" +
            "Сравить значение поля «Почта» и Значения =  feedback@dodopizza.com")
    public void test3 () {
        contactsPage = new ContactsPage(driver);
        Assertions.assertEquals("8 800 302-00-60", contactsPage.getTelephone());
        Assertions.assertEquals(contactsPage.getMail(), "feedback@dodopizza.com");
        Assertions.assertTrue(contactsPage.visiblTelefon());
        Assertions.assertTrue(contactsPage.visiblMail());
    }

    @AfterEach
    public void takeSkreenshot(TestInfo info) throws IOException {
        File source =((ChromeDriver)driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + "/screenshots/"+info.getDisplayName().toLowerCase() + ".jpeg");
        FileHandler.copy(source, dest);
        try {
            Allure.addAttachment("PageView", FileUtils.openInputStream(source));
        } catch (IOException | NoSuchSessionException e) {
            System.out.println(e);
        }
        System.out.println("Делаем скриншот");
    }
    @AfterAll
    public static void tearDown() {
        System.out.println("All the tests are executed");
        driver.close();
        driver.quit();
    }
}

