import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CheckFormTests {

    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
    }

    //проверяем только обязательные поля
    @Test
    void positiveOnlyRequiredFieldsTest() {
        System.out.println("Test only required fields ");
        open("https://demoqa.com/automation-practice-form");
        $("#firstName").setValue("Evgeny");
        $("#lastName").setValue("Usatenko");
        $(byText("Male")).click(); //радио, кликаем по тексту
        $("#userNumber").setValue("1111111111");
        $("#submit").scrollIntoView(true).click();

        //проверки
        $("tbody").$(byText("Student Name")).parent().shouldHave(text("Evgeny Usatenko"));
        $("tbody").$(byText("Gender")).parent().shouldHave(text("Male"));
        $("tbody").$(byText("Mobile")).parent().shouldHave(text("1111111111"));
    }

    //проверяем все поля
    @Test
    void positiveAllRequiredFieldsTest() {
        System.out.println("Test all fields");
        open("https://demoqa.com/automation-practice-form");
        $("#firstName").setValue("Evgeny");
        $("#lastName").setValue("Usatenko");
        $("#userEmail").setValue("Usatenko@sdasd.ru");
        $(byText("Male")).click(); //радио, кликаем по тексту
        $("#userNumber").setValue("1234567890");

        //выбор даты в календаре
        $("#dateOfBirthInput").click();
        $("#dateOfBirthInput").sendKeys(Keys.CONTROL, "a");
        $("#dateOfBirthInput").sendKeys(Keys.SPACE);
        $("#dateOfBirthInput").setValue("04 05 1984").sendKeys(Keys.ENTER);

        $("#subjectsInput").setValue("Maths").sendKeys(Keys.ENTER);
        $(byText("Sports")).click();
        $(byText("Reading")).click();
        $(byText("Music")).click();
        $("#uploadPicture").uploadFile(new File("src/test/resources/pic.jpg"));
        $("#currentAddress").setValue("street Test");

        //выбор штата и города
        $(byId("state")).scrollIntoView(true).click();
        //$(byId("state")).click();
        $(byText("NCR")).click();
        $(byId("city")).click();
        $(byText("Delhi")).click();

        $("#submit").scrollIntoView(true).click();

        //проверки
        $("tbody").$(byText("Student Name")).parent().shouldHave(text("Evgeny Usatenko"));
        $("tbody").$(byText("Student Email")).parent().shouldHave(text("Usatenko@sdasd.ru"));
        $("tbody").$(byText("Gender")).parent().shouldHave(text("Male"));
        $("tbody").$(byText("Mobile")).parent().shouldHave(text("1234567890"));
        $("tbody").$(byText("Date of Birth")).parent().shouldHave(text("05 April,1984"));
        $("tbody").$(byText("Subjects")).parent().shouldHave(text("Maths"));
        $("tbody").$(byText("Hobbies")).parent().shouldHave(text("Sports, Reading, Music"));
        $("tbody").$(byText("Picture")).parent().shouldHave(text("pic.JPG"));
        $("tbody").$(byText("Address")).parent().shouldHave(text("street Test"));
        $("tbody").$(byText("State and City")).parent().shouldHave(text("NCR Delhi"));

    }
}