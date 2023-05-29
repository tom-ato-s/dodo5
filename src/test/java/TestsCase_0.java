import Pages.MainPage;
import Pages.SityPage;
import io.qameta.allure.*;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
@Epic("Тестирование сайта пиццерии ДоДоПица")
@Link("https://dodopizza.ru")
@Feature("Step1: Проверка открытия первой страницы, " +
        "Step2: Проверить общее кол-во товаров в РАЗДЕЛЕ \"Пицца\" " +
        "Step3: В шапке страницы, рядом с надписью \"Доставка пиццы \", отображается наименование региона")
public class TestsCase_0 {
    private static WebDriver driver;
    private MainPage mainPage;
    private SityPage sityPage;


    @BeforeAll
    public static void start() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver();
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
    @Issue(value = "Case_0")
    @Owner("Архипова Надежда")
    @DisplayName("Step2: Проверить общее кол-во товаров в РАЗДЕЛЕ \"Пицца\" ")
    @Description("Получение количества товаров в разделе \"Пицца\"" +
            " Сравнение количества отображаемых элементов и ожидаемых 33")
    public void test2() {
        mainPage = new MainPage(driver);
        System.out.println("2dfgdfgdf");
        int calk = mainPage.quantityPizzasInSection();
        calcElementStep(33, calk);
    }
    @Step ("Сравнение количества отображенных элементов в разделе Пицца. Ожидается {expected}, реально {actual}")
    public static void calcElementStep(int expected, int actual) {
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    @Issue(value = "Case_0")
    @Owner("Архипова Надежда")
    @DisplayName("Step3: В шапке страницы, рядом с надписью \"Доставка пиццы \", отображается наименование региона")
    @Description(" в шапке страницы, рядом с надписью \"Доставка пиццы \" получить значение названия региона" +
            "Сравнение значений поля региона и ожидаемого «Москва»")
    public void test3() {
        mainPage = new MainPage(driver);
        System.out.println("3dfgdfgdf");
        String region = mainPage.getNameRegion();
        Assertions.assertEquals("Москва", region);
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
        driver.close();
        driver.quit();
    }
}
