# Seminar Registration System

A mini project using HTML, CSS, JavaScript, Spring Boot, Spring Data JPA and MySQL.

Students can choose a seminar, submit their details and get a registration number. The same email cannot register for the same seminar twice.

## Folder structure

```text
Seminar-Registration/
├── frontend/
│   ├── index.html
│   ├── style.css
│   ├── script.js
│   └── images/
│       └── seminar.jpg
├── backend/
│   ├── pom.xml
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── .mvn/wrapper/
│   └── src/main/
│       ├── java/com/example/seminar/
│       │   ├── SeminarApplication.java
│       │   ├── Registration.java
│       │   ├── RegistrationController.java
│       │   └── RegistrationRepository.java
│       └── resources/
│           └── application.properties
├── database.sql
├── .gitignore
└── README.md
```

The frontend and backend are separate folders. All Java classes are in one package, without extra service or DTO folders.

### How the frontend connects

`frontend/script.js` sends a POST request to `/register`. The Java controller validates the data and uses the repository to save it in MySQL.

The small `<resources>` section in `backend/pom.xml` includes the separate frontend folder when Maven runs/builds the app. This lets Spring Boot serve the page and API from the same address. There is only one original copy of each frontend file, in `frontend/`.

**Run the page through Spring Boot, not VS Code Live Server.** This differs from examples that use two servers and a hardcoded localhost API URL. It avoids CORS/address problems without changing the separate frontend/backend source layout.

## Run the project

Requirements: Java 17 or newer and MySQL running locally on port 3306.

### 1. Create the database

Run `database.sql` in MySQL Workbench:

```sql
CREATE DATABASE IF NOT EXISTS seminar_db;
```

The Java application creates the `seminar_registrations` table when it starts.

### 2. Start the backend

Open a terminal in `Seminar-Registration/backend`.

Windows PowerShell:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="YOUR_MYSQL_PASSWORD"
.\mvnw.cmd spring-boot:run
```

Windows Command Prompt:

```bat
set DB_USERNAME=root
set DB_PASSWORD=YOUR_MYSQL_PASSWORD
mvnw.cmd spring-boot:run
```

macOS / Linux:

```bash
export DB_USERNAME='root'
export DB_PASSWORD='YOUR_MYSQL_PASSWORD'
chmod +x mvnw
./mvnw spring-boot:run
```

Use your own local MySQL account. Do not put the real password in source code or commit it to GitHub. The first run needs internet to download Maven dependencies.

For IntelliJ/Eclipse: import `backend/pom.xml` as a Maven project, select Java 17+, and use the Maven `spring-boot:run` goal after setting the database environment variables. The Maven goal copies the frontend files before starting the app.

### 3. Open the website

Visit **http://localhost:8080**.

If you change a frontend file, stop the app with Ctrl+C and run the Maven command again to copy your updated file.

## Check the database

Submit sample details, then run:

```sql
USE seminar_db;
SELECT * FROM seminar_registrations;
```

Test these cases:

- Empty fields should show a validation message.
- Invalid email or mobile number should be rejected.
- Valid details should produce a success message and a row in MySQL.
- Same email + same seminar should be blocked, including uppercase email.
- Same email + different seminar should work.
- Clear should reset the fields and message.
- The layout should stack on a phone-sized screen.
- The stored row should remain after restarting the application.

## What each file does

| File | Purpose |
|---|---|
| index.html | Welcome section, seminar list and registration form |
| style.css | Colours, layout, form styling and responsive rules |
| script.js | JavaScript validation and the fetch request |
| SeminarApplication.java | Starts Spring Boot |
| Registration.java | Defines the database fields and generated ID |
| RegistrationRepository.java | Provides JPA save and duplicate-check operations |
| RegistrationController.java | Handles POST /register, validates and saves |
| application.properties | MySQL connection settings |

Request flow:

```text
Form → JavaScript → Spring Boot controller → JPA repository → MySQL
```

This is a small educational project, not a production service. There is no login, admin page, email sender, payment system or certificate feature. The success message appears on the page; no email is sent. Use sample personal details while testing.

## Upload to GitHub

Create an empty repository. Run these commands from the main `Seminar-Registration` folder, not just `backend`:

```bash
git init
git add .
git status
git commit -m "Add seminar registration project"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/seminar-registration-system.git
git push -u origin main
```

Check for passwords before committing. Upload both source folders, not just a ZIP. Keep `.mvn`, `mvnw` and `mvnw.cmd`; do not upload `backend/target`.

Keep the assigned project title unchanged. Understand and personalise the code, and follow your instructor's rules on outside assistance.

## Photo credit

`frontend/images/seminar.jpg`: Christina Morillo, “Group of People on a Conference Room”, via Pexels.

Source: https://www.pexels.com/photo/group-of-people-on-a-conference-room-1181406/

Licence: https://www.pexels.com/license/

A resized real photograph is used for illustration, not as a claim that the pictured people attended or endorsed these sample seminars. No classmate's photo or code is included.
