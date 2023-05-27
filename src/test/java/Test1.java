
import io.qameta.allure.*;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Epic("Тестирование сайта пиццерии ДоДоПица")
@Link("https://dodopizza.ru")
@Feature("\"Проверка открытия первой страницы, \" +\n" +
        "            \"создали объект главной страницы \\\"МainPage\\\", \" +\n" +
        "            \"Нажатие на регион во вслывающем окне. Выбрать регион Москва")
public class Test1 {
    public static WebDriver driver;


    @BeforeAll
    public static void start() {
        System.out.println("=======Starting junit 5 tests========");
    }

    @BeforeEach
    public void setup() {

        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();
        //     System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        System.out.println("=======Setting up the prerequisites========");

    }
    @Issue(value = "Case_0_1")
    @Owner("Архипова Надежда")
    @DisplayName("Перейти на главную страницу")
    @Description("создали объект главной страницы \"МainPage\", " +
            "Нажатие на регион во вслывающем окне. Выбрать регион «Москва»")
    @Test
    void test1_FirstTest() throws InterruptedException {

        driver.get(ConfProperties.getProperty("syteDodoPizza"));
        Thread.sleep(5000);
        //driver.findElement(By.xpath("//*[@href='/moscow']"));
        driver.findElement(By.xpath("//*[@id=\"react-app\"]/nav/div/ul/li[9]/a"));
        //*[@id="react-app"]/nav/div/ul/li[9]/a
        Thread.sleep(5000);
        clicButton(driver);
     //   driver.findElement(By.xpath("//*[@id=\"react-app\"]/nav/div/ul/li[9]/a")).click();
        Thread.sleep(5000);
        System.out.println(Thread.currentThread().getStackTrace()[1]
                .getMethodName()+" => executed successfully");
    }
    @Step("STEP Нажимаем на кномпу О нас драйвер {driver}")
    private static void clicButton(WebDriver driver) {
        driver.findElement(By.xpath("//*[@id=\"react-app\"]/nav/div/ul/li[9]/a")).click();
    }


    @AfterEach
    public void takeSkreenshot(TestInfo info) throws IOException {
        File source =((ChromeDriver)driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + "/screenshots/"+info.getDisplayName().toLowerCase() + ".jpeg");
        FileHandler.copy(source, dest);
        try {
            Allure.addAttachment("PageView", FileUtils.openInputStream(source));
        } catch (IOException | NoSuchSessionException e) {

        }finally {

        }
        System.out.println("Делаем скриншот");
    }

    @AfterAll
    public static void end() {
        System.out.println("All the tests are executed");
        driver.quit();

    }
}