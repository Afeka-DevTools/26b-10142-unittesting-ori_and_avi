# שיחה 2 – למידה עצמית: כתיבת בדיקות יחידה ב‑Java (JUnit 5)

> **כלי:** Claude (claude.ai) – שיחה דרך הדפדפן
> **תאריך:** 22.06.2026
> **משתתף:** User (צוות ori_and_avi)
> **מטרה:** להבין מאפס איך כותבים בדיקות יחידה ב‑Java לפני שניגשים לכתוב את הבדיקות לפרויקט.

---

**User:**
יש לי פרויקט Java עם Gradle ואני צריך לכתוב לו בדיקות יחידה. יש מחלקה בשם `App` עם הרבה פונקציות סטטיות (חיבור, בדיקת מספר ראשוני, היפוך מחרוזת, פקטוריאל וכו') וקובץ בשם `AppTest` שמשתמש ב‑JUnit. אף פעם לא כתבתי unit tests, אפשר שתסביר לי מההתחלה מה זה בכלל ואיך זה עובד ב‑Java? עדיף מסודר שלב‑שלב ועם דוגמת קוד אם אפשר.

**Claude:**
בכיף, בוא נבנה את זה מהיסוד.

**מה זה בדיקת יחידה (Unit Test)?**
בדיקת יחידה היא קטע קוד קצר שבודק "יחידה" אחת של לוגיקה – בדרך כלל מתודה אחת – בבידוד מהשאר. הרעיון הוא: נותנים למתודה קלט ידוע, ובודקים שהפלט שהיא מחזירה הוא בדיוק מה שציפינו. אם המתודה מחזירה משהו אחר – הבדיקה "נכשלת" (fails) ואתה יודע מיד שמשהו נשבר.

**למה זה חשוב?**
1. **רשת ביטחון** – כשתשנה קוד בעתיד (refactoring), הבדיקות יתפסו אם שברת משהו שעבד.
2. **תיעוד חי** – בדיקה טובה מתארת בדיוק מה המתודה אמורה לעשות.
3. **איכות** – זה מכריח אותך לחשוב על מקרי קצה (קלט ריק, שלילי, null) כבר בזמן הפיתוח.
4. **מהירות** – במקום להריץ ידנית ולבדוק עם העיניים, מריצים את כל הבדיקות בלחיצת כפתור.

**הכלי הסטנדרטי ב‑Java: JUnit 5 (Jupiter)**
ב‑Gradle הוא נוסף כתלות (`dependency`) של בדיקות. בפרויקט שלך זה כנראה כבר מוגדר ב‑`build.gradle.kts` בערך כך:

```kotlin
dependencies {
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
```

**המבנה של בדיקה – תבנית AAA**
כל בדיקה טובה בנויה משלושה שלבים:
1. **Arrange** – מכינים את הנתונים והאובייקטים.
2. **Act** – מפעילים את המתודה שבודקים.
3. **Assert** – מוודאים שהתוצאה היא מה שציפינו.

**דוגמה מלאה** – נניח שיש מתודה שמחברת שני מספרים:

```java
package org.example;

public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}
```

קובץ הבדיקה ייראה כך:

```java
package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    @Test
    void add_twoPositiveNumbers_returnsSum() {
        // Arrange
        Calculator calc = new Calculator();
        // Act
        int result = calc.add(2, 3);
        // Assert
        assertEquals(5, result, "2 + 3 צריך להיות 5");
    }
}
```

שלוש נקודות חשובות:
- `@Test` מסמן ש‑JUnit צריך להריץ את המתודה הזו כבדיקה.
- שם המתודה מתאר את התרחיש: `מה_נבדק_באיזה_תנאי_מה_מצופה`.
- `assertEquals(expected, actual)` – הפרמטר **הראשון** הוא הערך המצופה והשני הוא התוצאה בפועל. קל להתבלבל בסדר.

רוצה שאמשיך ל‑annotations הנפוצים ולסוגי ה‑assertions השונים?

---

**User:**
כן, בדיוק. תפרט לי בבקשה את כל סוגי ה‑assertions שקיימים, אני רוצה להשתמש בכמה שיותר מהם. וגם תסביר לי את ה‑annotations שכדאי להכיר.

**Claude:**
מצוין, זה בדיוק הלב של העניין. אחלק לך לשתי טבלאות.

**Annotations עיקריים (מ‑`org.junit.jupiter.api`):**

| Annotation | מתי רץ / למה משמש |
|---|---|
| `@Test` | מסמן מתודת בדיקה רגילה. |
| `@BeforeEach` | רץ לפני כל בדיקה – טוב לאתחול אובייקט משותף. |
| `@AfterEach` | רץ אחרי כל בדיקה – לניקוי משאבים. |
| `@BeforeAll` / `@AfterAll` | רצים פעם אחת לכל המחלקה (חייבים `static`). |
| `@DisplayName("...")` | נותן שם קריא לבדיקה בדוח. |
| `@Nested` | מקבץ בדיקות של אותו נושא במחלקה פנימית – מסדר את הקובץ. |
| `@ParameterizedTest` | מריץ את אותה בדיקה על הרבה קלטים שונים. |
| `@Disabled` | מנטרל בדיקה זמנית. |

**Assertions עיקריים (מ‑`org.junit.jupiter.api.Assertions`):**

| Assertion | מה הוא בודק |
|---|---|
| `assertEquals(expected, actual)` | שוויון בין שני ערכים. |
| `assertNotEquals(a, b)` | שאי‑שוויון. |
| `assertTrue(cond)` / `assertFalse(cond)` | שתנאי בוליאני מתקיים / לא. |
| `assertNull(x)` / `assertNotNull(x)` | ש‑x הוא null / אינו null. |
| `assertThrows(Exc.class, () -> ...)` | שהקוד **זורק** חריגה מסוג מסוים. |
| `assertDoesNotThrow(() -> ...)` | שהקוד **לא** זורק חריגה. |
| `assertArrayEquals(a1, a2)` | שוויון בין מערכים. |
| `assertIterableEquals(l1, l2)` | שוויון בין אוספים (List וכו') לפי סדר. |
| `assertSame` / `assertNotSame` | שמדובר באותו אובייקט בזיכרון (reference). |
| `assertAll(...)` | מריץ קבוצת assertions ביחד ומדווח על כל הכשלונות יחד. |

טיפ חשוב: כשבודקים **double** (מספר עשרוני) משתמשים ב‑`assertEquals(expected, actual, delta)` עם סטיה קטנה כמו `1e-9`, כי השוואת עשרוניים מדויקת לא אמינה.

---

**User:**
מעולה. עכשיו ספציפית – הרבה מהמתודות שלי אמורות לזרוק חריגה כשהקלט לא חוקי (למשל פקטוריאל של מספר שלילי זורק `IllegalArgumentException`). איך בודקים את זה נכון? וגם – מה ההבדל בין `assertThrows` למקרה רגיל?

**Claude:**
בדיוק בשביל זה קיים `assertThrows`. במקום לבדוק ערך מוחזר, אתה מוודא שהקוד **נכשל בכוונה** בצורה הצפויה. זה נראה כך:

```java
@Test
void factorial_negativeInput_throwsException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> App.factorial(-3)
    );
}
```

מה שקורה כאן:
- הפרמטר הראשון הוא **סוג** החריגה שאתה מצפה לה.
- הפרמטר השני הוא `lambda` – קטע קוד שמופעל בתוך try/catch פנימי של JUnit. שים לב שאסור לקרוא ל‑`App.factorial(-3)` ישירות, חייבים לעטוף ב‑`() -> ...`, אחרת החריגה תיזרק לפני ש‑assertThrows יספיק לתפוס אותה.

טיפ מתקדם: `assertThrows` **מחזיר** את אובייקט החריגה, אז אפשר גם לבדוק את ההודעה:

```java
IllegalArgumentException ex = assertThrows(
    IllegalArgumentException.class,
    () -> App.factorial(-3)
);
assertEquals("Negative number", ex.getMessage());
```

ולהפך – אם אתה רוצה לוודא שקלט **חוקי** לא מפיל חריגה, יש `assertDoesNotThrow(() -> App.factorial(5))`.

---

**User:**
הבנתי. שאלה אחרונה לגבי המבנה – המחלקה שלי `App` היא בעצם אוסף של מתודות סטטיות (utility class). איך הכי נכון לארגן קובץ בדיקות גדול עם הרבה מתודות כאלה, שלא ייצא בלגן? וגם – יש דרך לבדוק אותה מתודה על הרבה קלטים בלי לשכפל קוד?

**Claude:**
שתי שאלות מצוינות, וזה בדיוק מה שמבדיל קובץ בדיקות "מסודר" מ"ערימה".

**1. ארגון עם `@Nested`:**
כשיש מחלקת utility עם הרבה מתודות סטטיות, נוח לקבץ את הבדיקות לפי המתודה הנבדקת, כל מתודה במחלקה פנימית:

```java
@DisplayName("App unit tests")
class AppTest {

    @Nested
    @DisplayName("factorial(int)")
    class FactorialTests {

        @Test
        @DisplayName("0 ו‑1 מחזירים 1")
        void baseCases() {
            assertEquals(1, App.factorial(0));
            assertEquals(1, App.factorial(1));
        }

        @Test
        @DisplayName("קלט שלילי זורק חריגה")
        void negativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> App.factorial(-1));
        }
    }

    @Nested
    @DisplayName("isPrime(int)")
    class IsPrimeTests {
        // ...
    }
}
```

הדוח שמתקבל קריא מאוד: רואים "factorial(int) > קלט שלילי זורק חריגה ✓".

**2. בדיקה על הרבה קלטים – `@ParameterizedTest`:**
במקום לשכפל את אותה בדיקה, מזינים רשימת קלטים:

```java
@ParameterizedTest(name = "{0} הוא ראשוני")
@ValueSource(ints = {2, 3, 5, 7, 11, 13, 97})
void primes(int n) {
    assertTrue(App.isPrime(n));
}

@ParameterizedTest(name = "factorial({0}) = {1}")
@CsvSource({ "0, 1", "1, 1", "5, 120", "10, 3628800" })
void factorialValues(int n, int expected) {
    assertEquals(expected, App.factorial(n));
}
```

צריך לוודא ש‑`junit-jupiter` (ה‑aggregator) מופיע בתלויות – הוא כבר כולל את החבילה `junit-jupiter-params`, אז `@ParameterizedTest`, `@ValueSource` ו‑`@CsvSource` יעבדו בלי תוספת.

**סיכום ההמלצות שלי:**
1. תבנית AAA לכל בדיקה.
2. שמות בדיקות שמתארים תרחיש.
3. כיסוי לכל ענף: מקרה רגיל, גבול (0, 1, ריק), קלט לא חוקי (`assertThrows`).
4. שימוש מגוון ב‑assertions (assertEquals/True/False/Throws/ArrayEquals/Null...).
5. ארגון ב‑`@Nested` ושימוש ב‑`@ParameterizedTest` היכן שיש הרבה קלטים דומים.
6. הרצה עם `./gradlew test` ובדיקת הדוח ב‑`app/build/reports/tests/test/index.html`.

זה בסיס מצוין כדי לגשת לכתיבת הבדיקות בפועל. בהצלחה!

---

**User:**
תודה רבה, זה ממש סידר לי את הראש. עכשיו אני מבין גם את ה‑annotations, גם את ה‑assertions, גם איך בודקים חריגות וגם איך מסדרים את הקובץ. אני ניגש לכתוב.

**Claude:**
מעולה, שמח לעזור. רק זכור את החוק הזהב: כל ענף לוגי בקוד (כל `if`, כל `throw`, כל לולאה) צריך לפחות בדיקה אחת שמכסה אותו. אם תעקוב אחרי זה – תהיה במצב טוב. בהצלחה! 💪
