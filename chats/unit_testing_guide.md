```markdown
# מדריך בדיקות יחידה וכיסוי קוד ב-Java (JUnit 5 & JaCoCo)

מדריך זה מרכז את הבסיס לכתיבת בדיקות יחידה (Unit Tests) בשפת Java באמצעות JUnit 5, ובדיקת כיסוי נתיבים (Code Coverage) בסביבה המקומית וב-GitHub.

---

## 1. כתיבת בדיקת יחידה בסיסית (JUnit 5)

בדיקות יחידה נכתבות לפי תבנית ה-**AAA** (Arrange, Act, Assert):
1. **Arrange (הכנה):** יצירת האובייקטים והכנת הנתונים.
2. **Act (פעולה):** הפעלת המתודה שאותה בודקים.
3. **Assert (אימות):** וידוא שהתוצאה שהתקבלה היא התוצאה הצפויה.

### דוגמת קוד

נניח ויש לנו מחלקה פשוטה של מחשבון:
```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}
כך נכתוב עבורה את קובץ הבדיקה:

Java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    public void testAdd_PositiveNumbers_ReturnsCorrectSum() {
        // 1. Arrange
        Calculator calculator = new Calculator();

        // 2. Act
        int result = calculator.add(2, 3);

        // 3. Assert
        assertEquals(5, result, "2 + 3 should equal 5");
    }
}
2. אנוטציות ופונקציות אימות נפוצות
אנוטציות (Annotations) לניהול מחזור החיים של הטסט:
@Test - מסמנת מתודה כבדיקה להרצה.

@BeforeEach - רצה לפני כל טסט (מעולה לאיחול אובייקטים משותפים).

@AfterEach - רצה אחרי כל טסט (לניקוי משאבים).

פונקציות אימות (Assertions):
assertEquals(expected, actual) - בדיקת שוויון.

assertTrue(condition) / assertFalse(condition) - בדיקת תנאי בוליאני.

assertThrows(Exception.class, () -> { ... }) - וידוא שהקוד זורק חריגה (Exception) במקרה של קלט לא תקין.

3. בדיקת כיסוי קוד (Code Coverage)
כדי לוודא שבדקנו את כל נתיבי הריצה בפונקציות (למשל, כל תנאי ה-if וה-else), נשתמש במדד שנקרא Branch Coverage. כלי הסטנדרט ב-Java הוא JaCoCo.

בדיקה מקומית ב-IDE (IntelliJ IDEA):
לחץ קליק ימני על תיקיית הבדיקות.

בחר באפשרות Run 'All Tests' with Coverage.

לצד שורות הקוד יופיעו סימונים צבעוניים:

ירוק: הנתיב נבדק במלואו.

צהוב: חלק מנתיבי ה-if פוספסו.

אדום: השורה לא הורצה באף טסט.

4. אוטומציה ב-GitHub באמצעות GitHub Actions
כדי שדו"ח כיסוי הקוד יופק אוטומטית בכל פעם שמבצעים push או פותחים Pull Request, ניתן להשתמש ב-GitHub Actions.

יש ליצור קובץ בנתיב: .github/workflows/maven-coverage.yml עם התוכן הבא:

YAML
name: Java CI with Code Coverage

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    - name: Checkout repository
      uses: actions/checkout@v4

    - name: Set up JDK 17
      uses: actions/setup-java@v4
```markdown
# מדריך בדיקות יחידה וכיסוי קוד ב-Java (JUnit 5 & JaCoCo)

מדריך זה מרכז את הבסיס לכתיבת בדיקות יחידה (Unit Tests) בשפת Java באמצעות JUnit 5, ובדיקת כיסוי נתיבים (Code Coverage) בסביבה המקומית וב-GitHub.

---

## 1. כתיבת בדיקת יחידה בסיסית (JUnit 5)

בדיקות יחידה נכתבות לפי תבנית ה-**AAA** (Arrange, Act, Assert):
1. **Arrange (הכנה):** יצירת האובייקטים והכנת הנתונים.
2. **Act (פעולה):** הפעלת המתודה שאותה בודקים.
3. **Assert (אימות):** וידוא שהתוצאה שהתקבלה היא התוצאה הצפויה.

### דוגמת קוד

נניח ויש לנו מחלקה פשוטה של מחשבון:
```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}
כך נכתוב עבורה את קובץ הבדיקה:

Java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    public void testAdd_PositiveNumbers_ReturnsCorrectSum() {
        // 1. Arrange
        Calculator calculator = new Calculator();

        // 2. Act
        int result = calculator.add(2, 3);

        // 3. Assert
        assertEquals(5, result, "2 + 3 should equal 5");
    }
}
2. אנוטציות ופונקציות אימות נפוצות
אנוטציות (Annotations) לניהול מחזור החיים של הטסט:
@Test - מסמנת מתודה כבדיקה להרצה.

@BeforeEach - רצה לפני כל טסט (מעולה לאיחול אובייקטים משותפים).

@AfterEach - רצה אחרי כל טסט (לניקוי משאבים).

פונקציות אימות (Assertions):
assertEquals(expected, actual) - בדיקת שוויון.

assertTrue(condition) / assertFalse(condition) - בדיקת תנאי בוליאני.

assertThrows(Exception.class, () -> { ... }) - וידוא שהקוד זורק חריגה (Exception) במקרה של קלט לא תקין.

3. בדיקת כיסוי קוד (Code Coverage)
כדי לוודא שבדקנו את כל נתיבי הריצה בפונקציות (למשל, כל תנאי ה-if וה-else), נשתמש במדד שנקרא Branch Coverage. כלי הסטנדרט ב-Java הוא JaCoCo.

בדיקה מקומית ב-IDE (IntelliJ IDEA):
לחץ קליק ימני על תיקיית הבדיקות.

בחר באפשרות Run 'All Tests' with Coverage.

לצד שורות הקוד יופיעו סימונים צבעוניים:

ירוק: הנתיב נבדק במלואו.

צהוב: חלק מנתיבי ה-if פוספסו.

אדום: השורה לא הורצה באף טסט.

4. אוטומציה ב-GitHub באמצעות GitHub Actions
כדי שדו"ח כיסוי הקוד יופק אוטומטית בכל פעם שמבצעים push או פותחים Pull Request, ניתן להשתמש ב-GitHub Actions.

יש ליצור קובץ בנתיב: .github/workflows/maven-coverage.yml עם התוכן הבא:

YAML
name: Java CI with Code Coverage

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    - name: Checkout repository
      uses: actions/checkout@v4

    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'
        cache: maven

    # הרצת הטסטים ויצירת דו"ח JaCoCo
    - name: Build and Run Tests with Maven
      run: mvn clean verify

    # הוספת תגובה אוטומטית ל-Pull Request עם טבלת הכיסוי
    - name: Add Coverage PR Comment
      uses: madrapps/jacoco-report@v1.7.1
      with:
        paths: ${{ github.workspace }}/target/site/jacoco/jacoco.xml
        token: ${{ secrets.GITHUB_TOKEN }}
        min-coverage-percentage: 80
