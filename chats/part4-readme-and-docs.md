# שיחה 4 – הכנת ה‑README והוראות ההרצה

> **כלי:** GitHub Copilot – Agent mode בתוך VS Code
> **תאריך:** 24.06.2026
> **משתתף:** User (צוות ori_and_avi)
> **מטרה:** לכתוב `README.md` ברור שמסביר מי חברי הצוות ואיך מריצים את כל הבדיקות מאפס.

---

**User:**
תכתוב לי `README.md` לפרויקט. הוא צריך לכלול את חברי הצוות ואת **כל** השלבים שמשתמש צריך לעשות כדי להריץ את הבדיקות – החל משכפול הפרויקט (`git clone`) ועד ההרצה בפועל. הצוות הוא ori_and_avi: אורי אוחיון ואבי כליפה. תניח שלמשתמש אין Gradle מותקן גלובלית. שיהיה מסודר.

**GitHub Copilot:**
בטח. מאחר שאין הנחה שיש Gradle מותקן, אשתמש ב‑Gradle Wrapper (`./gradlew` / `gradlew.bat`) שכבר נמצא ב‑repo – כך אף אחד לא צריך להתקין Gradle ידנית. אכלול: דרישות מקדימות (JDK), שלבי clone, הרצה בלינוקס/מק ובווינדוס, איפה לראות את דוח הבדיקות, ומבנה הפרויקט. שים לב לנקודה אחת חשובה: ה‑`build.gradle.kts` מגדיר Java toolchain בגרסה 21, אז כדאי לציין שצריך JDK 21 (או שה‑foojay‑resolver יוריד אותו אוטומטית).

הנה הטיוטה:

```markdown
# 26B-10142 – Unit Testing (Gradle + JUnit 5)
## Team ori_and_avi
- אורי אוחיון
- אבי כליפה

## דרישות מקדימות
- Git
- JDK 21 (או חיבור לאינטרנט – Gradle יוריד את ה‑toolchain אוטומטית)

## הרצה
git clone <repo-url>
cd 26b-10142-unittesting-ori_and_avi
./gradlew test        # Linux / macOS
gradlew.bat test      # Windows
```

רוצה שאוסיף סעיף שמסביר איך לקרוא את דוח התוצאות ה‑HTML?

**User:**
כן, תוסיף את הסעיף של איפה רואים את הדוח, וגם משפט קצר שמסביר מה הבדיקות בודקות. תכתוב את הקובץ הסופי.

**GitHub Copilot:**
הוספתי סעיף "תוצאות והדוח" שמפנה ל‑`app/build/reports/tests/test/index.html`, וסעיף קצר שמתאר שהבדיקות מכסות את כל המתודות ב‑`App.java` כולל מקרי קצה וחריגות. כתבתי את הקובץ:

```
Edited README.md
```

ה‑README מוכן. כדאי להריץ פעם אחת `./gradlew test` כדי לאמת שהכול ירוק לפני commit.

**User:**
מצוין, תודה.

**GitHub Copilot:**
בכיף! בהצלחה. 🚀
