# LEARNING.md – תיעוד השיחות מחלק 2 (למידה עצמית)

מסמך זה מתעד את שלב **הלמידה העצמית** של התרגיל: שיחה עם כלי LLM שמטרתה להבין כיצד כותבים
בדיקות יחידה בשפת Java. השיחה המלאה נמצאת גם בתיקיית `chats/` בקובץ
[`part2-learning-junit5.md`](../chats/part2-learning-junit5.md).

---

## פרטי השיחה
- **כלי LLM:** Claude (claude.ai), שיחה דרך הדפדפן.
- **תאריך:** 22.06.2026.
- **משתתף:** User, צוות ori_and_avi.
- **בקשה מרכזית:** הסבר מסודר, שלב‑שלב, על כתיבת בדיקות יחידה ב‑Java עם JUnit 5, כולל דוגמת קוד ופורמט ברור.

## מה הוגדר בבקשה (הקשר + פורמט)
כפי שנדרש בסעיף 2.ב, ניהלנו שיח ברור שמגדיר את הבקשה: ציינו את ההקשר (פרויקט Gradle קיים,
מחלקת `App` עם פונקציות סטטיות, קובץ `AppTest` עם JUnit), וביקשנו פורמט מסוים – הסבר מסודר
שלב‑שלב עם טבלאות ודוגמת קוד.

## סיכום הנקודות שנלמדו
1. **מהי בדיקת יחידה ולמה** – בדיקה קצרה למתודה אחת בבידוד; משמשת כרשת ביטחון, תיעוד חי ושיפור איכות.
2. **JUnit 5 (Jupiter) ב‑Gradle** – נוסף כ‑`testImplementation(libs.junit.jupiter)` עם `useJUnitPlatform()`.
3. **תבנית AAA** – Arrange (הכנה), Act (הפעלה), Assert (אימות).
4. **Annotations** – `@Test`, `@BeforeEach`/`@AfterEach`, `@BeforeAll`/`@AfterAll`, `@DisplayName`,
   `@Nested`, `@ParameterizedTest`, `@Disabled`.
5. **Assertions** – `assertEquals/assertNotEquals`, `assertTrue/assertFalse`,
   `assertNull/assertNotNull`, `assertThrows/assertDoesNotThrow`, `assertArrayEquals`,
   `assertIterableEquals`, `assertSame/assertNotSame`, `assertAll`. ל‑double משתמשים ב‑delta.
6. **בדיקת חריגות** – `assertThrows(Type.class, () -> ...)`, ואפשר לאמת גם את ההודעה דרך אובייקט
   החריגה המוחזר.
7. **ארגון** – שימוש ב‑`@Nested` לקיבוץ בדיקות לפי מתודה, ו‑`@ParameterizedTest` (`@ValueSource`,
   `@CsvSource`) לבדיקת הרבה קלטים בלי שכפול.
8. **חוק הזהב** – כל ענף לוגי בקוד (כל `if`, `throw`, לולאה) צריך לפחות בדיקה אחת שמכסה אותו.

---

## תמלול השיחה המלא

**User:**
יש לי פרויקט Java עם Gradle ואני צריך לכתוב לו בדיקות יחידה. יש מחלקה בשם `App` עם הרבה פונקציות סטטיות (חיבור, בדיקת מספר ראשוני, היפוך מחרוזת, פקטוריאל וכו') וקובץ בשם `AppTest` שמשתמש ב‑JUnit. אף פעם לא כתבתי unit tests, אפשר שתסביר לי מההתחלה מה זה בכלל ואיך זה עובד ב‑Java? עדיף מסודר שלב‑שלב ועם דוגמת קוד אם אפשר.

**Claude:**
בכיף, בוא נבנה את זה מהיסוד.

בדיקת יחידה (Unit Test) היא קטע קוד קצר שבודק "יחידה" אחת של לוגיקה – בדרך כלל מתודה אחת – בבידוד מהשאר. נותנים למתודה קלט ידוע ובודקים שהפלט הוא בדיוק מה שציפינו; אם לא – הבדיקה נכשלת ואתה יודע מיד שמשהו נשבר.

למה זה חשוב: (1) רשת ביטחון בעת refactoring, (2) תיעוד חי של מה המתודה אמורה לעשות, (3) איכות – מכריח לחשוב על מקרי קצה, (4) מהירות – הרצה בלחיצת כפתור במקום בדיקה ידנית.

הכלי הסטנדרטי הוא JUnit 5 (Jupiter), שמוגדר ב‑Gradle כך:

```kotlin
dependencies {
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
tasks.named<Test>("test") { useJUnitPlatform() }
```

כל בדיקה בנויה בתבנית AAA: Arrange (הכנת נתונים), Act (הפעלת המתודה), Assert (אימות התוצאה). דוגמה:

```java
class CalculatorTest {
    @Test
    void add_twoPositiveNumbers_returnsSum() {
        Calculator calc = new Calculator();   // Arrange
        int result = calc.add(2, 3);           // Act
        assertEquals(5, result);               // Assert
    }
}
```

שים לב ש‑`assertEquals(expected, actual)` – הראשון הוא הצפוי והשני הוא בפועל. רוצה שאמשיך ל‑annotations ול‑assertions?

**User:**
כן, בדיוק. תפרט לי בבקשה את כל סוגי ה‑assertions שקיימים, אני רוצה להשתמש בכמה שיותר מהם. וגם תסביר לי את ה‑annotations שכדאי להכיר.

**Claude:**
ה‑Annotations העיקריים: `@Test` (בדיקה רגילה), `@BeforeEach`/`@AfterEach` (לפני/אחרי כל בדיקה),
`@BeforeAll`/`@AfterAll` (פעם אחת, `static`), `@DisplayName` (שם קריא), `@Nested` (קיבוץ),
`@ParameterizedTest` (הרצה על הרבה קלטים), `@Disabled` (נטרול זמני).

ה‑Assertions העיקריים: `assertEquals`/`assertNotEquals` (שוויון), `assertTrue`/`assertFalse`
(תנאי בוליאני), `assertNull`/`assertNotNull`, `assertThrows` (זריקת חריגה) /
`assertDoesNotThrow`, `assertArrayEquals` (מערכים), `assertIterableEquals` (אוספים),
`assertSame`/`assertNotSame` (אותו reference), `assertAll` (קבוצת assertions יחד).

טיפ: ל‑double משתמשים ב‑`assertEquals(expected, actual, delta)` עם סטיה קטנה כמו `1e-9`.

**User:**
מעולה. עכשיו ספציפית – הרבה מהמתודות שלי אמורות לזרוק חריגה כשהקלט לא חוקי (למשל פקטוריאל של מספר שלילי זורק `IllegalArgumentException`). איך בודקים את זה נכון? וגם – מה ההבדל בין `assertThrows` למקרה רגיל?

**Claude:**
בשביל זה קיים `assertThrows`: במקום לבדוק ערך מוחזר, מוודאים שהקוד נכשל בכוונה.

```java
@Test
void factorial_negativeInput_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> App.factorial(-3));
}
```

הפרמטר הראשון הוא סוג החריגה, השני הוא lambda – חובה לעטוף ב‑`() -> ...` כדי ש‑JUnit יתפוס את החריגה. `assertThrows` גם מחזיר את אובייקט החריגה, אז אפשר לבדוק את ההודעה:

```java
var ex = assertThrows(IllegalArgumentException.class, () -> App.factorial(-3));
assertEquals("Negative number", ex.getMessage());
```

ולהפך – `assertDoesNotThrow(() -> App.factorial(5))` מוודא שקלט חוקי לא מפיל חריגה.

**User:**
הבנתי. שאלה אחרונה לגבי המבנה – המחלקה שלי `App` היא בעצם אוסף של מתודות סטטיות (utility class). איך הכי נכון לארגן קובץ בדיקות גדול עם הרבה מתודות כאלה, שלא ייצא בלגן? וגם – יש דרך לבדוק אותה מתודה על הרבה קלטים בלי לשכפל קוד?

**Claude:**
שתי טכניקות. ראשית `@Nested` – מקבצים בדיקות לפי המתודה הנבדקת, כל מתודה במחלקה פנימית, והדוח יוצא קריא ("factorial(int) > קלט שלילי זורק חריגה ✓"). שנית `@ParameterizedTest` – במקום לשכפל, מזינים רשימת קלטים:

```java
@ParameterizedTest @ValueSource(ints = {2,3,5,7,11,13,97})
void primes(int n) { assertTrue(App.isPrime(n)); }

@ParameterizedTest @CsvSource({ "0,1", "1,1", "5,120", "10,3628800" })
void factorialValues(int n, int expected) { assertEquals(expected, App.factorial(n)); }
```

ה‑aggregator `junit-jupiter` כבר כולל את `junit-jupiter-params`, אז זה עובד ללא תוספת.

ההמלצות שלי: AAA לכל בדיקה, שמות שמתארים תרחיש, כיסוי לכל ענף (רגיל/גבול/קלט לא חוקי), שימוש מגוון ב‑assertions, ארגון ב‑`@Nested` ו‑`@ParameterizedTest`, והרצה עם `./gradlew test`.

**User:**
תודה רבה, זה ממש סידר לי את הראש. אני ניגש לכתוב.

**Claude:**
מעולה. זכור את חוק הזהב: כל ענף לוגי בקוד (כל `if`, `throw`, לולאה) צריך לפחות בדיקה אחת שמכסה אותו. בהצלחה!
