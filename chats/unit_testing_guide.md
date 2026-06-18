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
