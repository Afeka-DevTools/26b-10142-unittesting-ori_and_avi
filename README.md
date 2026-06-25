# 26B-10142 – Unit Testing with Gradle & JUnit 5

תרגיל בית 2 בקורס **כלי פיתוח** (אפקה) – כתיבת בדיקות יחידה בשפת Java לפרויקט Gradle.
הפרויקט מכיל את המחלקה `org.example.App` (אוסף מתודות עזר) ואת קובץ הבדיקות `org.example.AppTest`
שמכסה את כל המתודות בעזרת JUnit 5.

## חברי הצוות – `ori_and_avi`
| שם | תפקיד |
|---|---|
| אורי אוחיון (Ori Ohayon) | ראש צוות |
| אבי כליפה (Avy Kalifa) | חבר צוות |

---

## דרישות מקדימות
- **Git** – לשכפול המאגר.
- **JDK 21** – הפרויקט מגדיר Java toolchain בגרסה 21 (`app/build.gradle.kts`).
  אם אין לכם JDK 21 מותקן, Gradle ינסה להוריד אותו אוטומטית דרך תוסף ה‑`foojay-resolver`
  (נדרש חיבור לאינטרנט בהרצה הראשונה).
- **אין צורך להתקין Gradle** – הפרויקט כולל Gradle Wrapper (`gradlew` / `gradlew.bat`)
  שמוריד את הגרסה הנכונה (Gradle 8.14) לבד.

## שכפול הפרויקט (Clone)
```bash
git clone https://github.com/Afeka-DevTools/26b-10142-unittesting-ori_and_avi.git
cd 26b-10142-unittesting-ori_and_avi
```

## הרצת כל הבדיקות
**Linux / macOS:**
```bash
./gradlew test
```

**Windows (PowerShell / CMD):**
```powershell
.\gradlew.bat test
```

בהרצה תקינה תראו בסוף:
```
BUILD SUCCESSFUL
```
כל הבדיקות (56 בדיקות) אמורות לעבור בהצלחה ללא כשלונות.

> טיפ: כדי להריץ "נקי" (ללא תוצרים מקומפלים קודמים) השתמשו ב‑`./gradlew clean test`.

## צפייה בדוח התוצאות
לאחר ההרצה נוצר דוח HTML מפורט. פתחו בדפדפן את הקובץ:
```
app/build/reports/tests/test/index.html
```
הדוח מציג כל בדיקה, מקובצת לפי המתודה הנבדקת (`@Nested`), עם ירוק/אדום לכל אחת.

## בדיקת כיסוי קוד (Code Coverage)
כדי לוודא שכל הנתיבים ומקרי הקצה אכן מכוסים, הפרויקט כולל את תוסף **JaCoCo**. הרצת הבדיקות
מפיקה אוטומטית גם דוח כיסוי. כדי לצפות בו:
```bash
./gradlew test jacocoTestReport
```
ופתחו בדפדפן את `app/build/reports/jacoco/test/html/index.html`.
הכיסוי הנוכחי למחלקה `App` הוא **100%** בכל המדדים: ענפים (Branch) 26/26, שורות (Line) 46/46,
מתודות (Method) 12/12 ופקודות (Instruction) 277/277 – כלומר כל הנתיבים ומקרי הקצה נבדקו.

---

## מה נבדק?
קובץ הבדיקות `AppTest.java` מכסה את **כל 11 המתודות** ב‑`App.java`, ולכל אחת: המקרה הרגיל,
ערכי הגבול, מקרי הקצה והחריגות. נעשה שימוש במגוון רחב של פונקציות assert של JUnit 5
(`assertEquals`, `assertTrue`, `assertFalse`, `assertThrows`, `assertDoesNotThrow`,
`assertArrayEquals`, `assertIterableEquals`, `assertNull`, `assertNotNull`, `assertAll`),
וכן ב‑`@ParameterizedTest` לכיסוי הרבה קלטים.

| מתודה | תיאור הבדיקות |
|---|---|
| `add` | חיבור חיובי/שלילי/אפס, קומוטטיביות, גלישת `int` |
| `isPrime` | ראשוניים, פריקים, מספרים קטנים מ‑2, גבול 2/3 |
| `reverse` | מחרוזת רגילה/ריקה/תו בודד, היפוך כפול, `null` |
| `factorial` | ערכים רגילים, גבול 0/1, קלט שלילי → חריגה |
| `isPalindrome` | פלינדרום עם פיסוק/רישיות, לא‑פלינדרום, ריק |
| `fibonacciUpTo` | רצף תקין, גבולות, קלט שלילי → חריגה |
| `charFrequency` | ספירת תווים, מחרוזת ריקה, תו חסר |
| `isAnagram` | אנגרמות, אורך/תווים שונים, רישיות ורווחים |
| `average` | ממוצע, שליליים, מערך ריק → חריגה |
| `filterEvens` | סינון זוגיים כולל 0 ושליליים, רשימה ריקה |
| `mostCommonWord` | המילה השכיחה, התעלמות מפיסוק/רישיות |

## מבנה הפרויקט
```
.
├── app/
│   ├── build.gradle.kts
│   └── src/
│       ├── main/java/org/example/App.java        # הקוד הנבדק
│       └── test/java/org/example/AppTest.java    # בדיקות היחידה
├── chats/                                        # תיעוד השיחות עם כלי ה‑LLM
│   ├── part2-learning-junit5.md
│   ├── part3-writing-tests-copilot.md
│   └── part4-readme-and-docs.md
├── logs/
│   ├── LEARNING.md                               # תיעוד שיחות חלק 2
│   └── COPILOT.md                                # תיעוד שיחות חלק 3
├── gradlew / gradlew.bat                         # Gradle Wrapper
└── README.md
```

## תיעוד השימוש בכלי LLM
בהתאם לדרישות התרגיל, תיעוד השיחות עם כלי ה‑LLM נמצא בתיקיות `chats/` ו‑`logs/`:
- **חלק 2 (למידה עצמית):** [`logs/LEARNING.md`](logs/LEARNING.md) ו‑[`chats/part2-learning-junit5.md`](chats/part2-learning-junit5.md).
- **חלק 3 (כתיבת הבדיקות):** [`logs/COPILOT.md`](logs/COPILOT.md) ו‑[`chats/part3-writing-tests-copilot.md`](chats/part3-writing-tests-copilot.md).
- **חלק 4 (README):** [`chats/part4-readme-and-docs.md`](chats/part4-readme-and-docs.md).
