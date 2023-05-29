

import Pages.ContactsPage;
import Pages.MainPage;
import Pages.SityPage;
import io.qameta.allure.*;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    public static Logger log = LogManager.getLogger();
    private MainPage mainPage;
    private SityPage sityPage;
    private ContactsPage contactsPage;
    WebDriverWait webDriverWait;

    @BeforeAll
    public static void start() {
        log.info("Case_3 Подключаем драйвер, настройки");
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
        log.info(" Step1 Создаем объекты MainPage, SityPage ");
        mainPage = new MainPage(driver);
        sityPage = new SityPage(driver);
        log.info("Открывается сайт \"dodopizza.ru\"");
        driver.get(ConfProperties.getProperty("syteDodoPizza"));
        Thread.sleep(5000);
        log.info("Проверка: открытия первой станицы сайта");
        Assertions.assertNotNull(driver.getTitle());
        Thread.sleep(500);
        log.info("Нажатие на регион во всплывающем окне. Выбрать регион «Москва»");
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
        log.info("\"Step2: Нажать на кнопку “Контакты”");
        contactsPage = new ContactsPage(driver);
        mainPage = new MainPage(driver);

//        webDriverWait = new WebDriverWait(driver, 15);
//        webDriverWait.until(ExpectedConditions.visibilityOf(mainPage.getElementContact()));
        log.info("\"Нажать на кнопку “Контакты”\"");
        mainPage.clickBtnContacts();
        log.info("Проверка открытия страницы “Контакты”");
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
        log.info("Step3: Проверить что телефон и email верны и отображаются в блоке");
        contactsPage = new ContactsPage(driver);
        log.info("Сравить значение поля «Телефон» и Значения = 8 800 302-00-60");
        Assertions.assertEquals("8 800 302-00-60", contactsPage.getTelephone());
        log.info("Сравить значение поля «Почта» и Значения =  feedback@dodopizza.com");
        Assertions.assertEquals(contactsPage.getMail(), "feedback@dodopizza.com");
        Assertions.assertTrue(contactsPage.visiblTelefon());
        Assertions.assertTrue(contactsPage.visiblMail());
    }

    @AfterEach
    public void takeSkreenshot(TestInfo info) throws IOException {
        log.info("Делаем скриншот после окончания Step");
        File source =((ChromeDriver)driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + "/screenshots/"+info.getDisplayName().toLowerCase() + ".jpeg");
        FileHandler.copy(source, dest);
        try {
            Allure.addAttachment("PageView", FileUtils.openInputStream(source));
            log.info("Создан скриншот");
        } catch (IOException | NoSuchSessionException e) {

            log.error("Не удалось создать скриншот: \n" + e );
        }
        System.out.println("Делаем скриншот");
    }
    @AfterAll
    public static void tearDown() {
        log.info("Закрытие окна браузера");
        driver.close();
        log.info("Закрытие драйвера");
        driver.quit();
    }
}

