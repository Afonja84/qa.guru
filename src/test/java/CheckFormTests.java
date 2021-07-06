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
        String name = "Evgeny";
        String lastName = "Usatenko";
        String mobile = "1111111111";
        String gender = "Male";

        open("https://demoqa.com/automation-practice-form");
        $("#firstName").setValue(name);
        $("#lastName").setValue(lastName);
        $(byText(gender)).click(); //радио, кликаем по тексту
        $("#userNumber").setValue(mobile);
        $("#submit").scrollIntoView(true).click();

        //проверки
        $("tbody").$(byText("Student Name")).parent().shouldHave(text(name+" "+lastName));
        $("tbody").$(byText("Gender")).parent().shouldHave(text(gender));
        $("tbody").$(byText("Mobile")).parent().shouldHave(text(mobile));
    }

    //проверяем все поля
    @Test
    void positiveAllRequiredFieldsTest() {
        String name = "Evgeny";
        String lastName = "Usatenko";
        String mobile = "1234567890";
        String gender = "Male";
        String email = "Usatenko@sdasd.ru";

        open("https://demoqa.com/automation-practice-form");
        $("#firstName").setValue(name);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $(byText(gender)).click(); //радио, кликаем по тексту
        $("#userNumber").setValue(mobile);

        //выбор даты в календаре
        $("#dateOfBirthInput").click();
        $("#dateOfBirthInput").sendKeys(Keys.CONTROL, "a");
        $("#dateOfBirthInput").sendKeys(" ");
        $("#dateOfBirthInput").setValue("04 05 1984").sendKeys(Keys.ENTER);

        $("#subjectsInput").setValue("Maths").pressEnter(); // вместо sendKeys(Keys.ENTER);
        $(byText("Sports")).click();
        $(byText("Reading")).click();
        $(byText("Music")).click();
        $("#uploadPicture").uploadFromClasspath("pic.jpg") ;//uploadFile(new File("src/test/resources/pic.jpg"));
        $("#currentAddress").setValue("street Test");

        //выбор штата и города
        $("#state").scrollIntoView(true).click();
        $(byText("NCR")).click();
        $("#city").click();
        $(byText("Delhi")).click();
        $("#submit").click(); //вместо scrollIntoView(true)

        //проверки
        $("tbody").$(byText("Student Name")).parent().shouldHave(text(name+" "+lastName));
        $("tbody").$(byText("Student Email")).parent().shouldHave(text(email));
        $("tbody").$(byText("Gender")).parent().shouldHave(text(gender));
        $("tbody").$(byText("Mobile")).parent().shouldHave(text(mobile));
        $("tbody").$(byText("Date of Birth")).parent().shouldHave(text("05 April,1984"));
        $("tbody").$(byText("Subjects")).parent().shouldHave(text("Maths"));
        $("tbody").$(byText("Hobbies")).parent().shouldHave(text("Sports, Reading, Music"));
        $("tbody").$(byText("Picture")).parent().shouldHave(text("pic.JPG"));
        $("tbody").$(byText("Address")).parent().shouldHave(text("street Test"));
        $("tbody").$(byText("State and City")).parent().shouldHave(text("NCR Delhi"));

    }
}