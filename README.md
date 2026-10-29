<!-- ===================== Animated Project Header ===================== -->
<div align="center">

<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&height=230&color=0:0F766E,45:14B8A6,100:99F6E4&text=Expense%20Tracker%20API&fontColor=FFFFFF&fontSize=42&fontAlignY=38&desc=Track%20smarter.%20Spend%20wiser.&descAlignY=58&descSize=18&animation=fadeIn" alt="Expense Tracker API banner" />

<a href="https://github.com/AK-Elshamy/expense-tracker-api">
  <img src="https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 25" />
</a>
<a href="https://spring.io/projects/spring-boot">
  <img src="https://img.shields.io/badge/Spring%20Boot-4.1.2-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot" />
</a>
<a href="https://www.mysql.com/">
  <img src="https://img.shields.io/badge/MySQL-Database-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
</a>
<a href="https://spring.io/projects/spring-security">
  <img src="https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security and JWT" />
</a>

<br />

<img src="https://readme-typing-svg.demolab.com?font=Fira+Code&weight=500&size=18&duration=2800&pause=800&color=14B8A6&center=true&vCenter=true&width=650&lines=A+secure+REST+API+for+personal+expenses;JWT+Authentication+%7C+Pagination+%7C+Date+Filtering;Spending+summaries+%26+category+statistics" alt="Animated project summary" />

<p>
  <a href="#-features">Features</a> •
  <a href="#-tech-stack">Tech Stack</a> •
  <a href="#-getting-started">Getting Started</a> •
  <a href="#-api-reference">API Reference</a> •
  <a href="#-project-structure">Project Structure</a>
</p>

</div>

---

## 💡 About the Project

**Expense Tracker API** is a Java and Spring Boot backend for managing personal expenses through a RESTful API. It provides secure user authentication, expense CRUD operations, pagination, date-based filtering, and spending summaries with category breakdowns.

The goal is straightforward: help users understand where their money goes — before it disappears.


## ✨ Features

<table>
  <tr>
    <td width="50%" valign="top">
      <h3>🔐 Authentication & Security</h3>
      <ul>
        <li>User registration and login</li>
        <li>JWT-based authentication</li>
        <li>Spring Security protection for secured routes</li>
        <li>Authenticated-user information endpoint</li>
      </ul>
    </td>
    <td width="50%" valign="top">
      <h3>💳 Expense Management</h3>
      <ul>
        <li>Create, list, update, and delete expenses</li>
        <li>Amount, description, category, and expense date</li>
        <li>Input validation and structured errors</li>
        <li>User-specific expense access checks</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td valign="top">
      <h3>📅 Filtering & Pagination</h3>
      <ul>
        <li>Filter expenses by a relative period</li>
        <li>Filter by custom start and end dates</li>
        <li>Page through results with a page query parameter</li>
      </ul>
    </td>
    <td valign="top">
      <h3>📊 Spending Insights</h3>
      <ul>
        <li>Total amount spent and transaction count</li>
        <li>Earliest and latest expense dates</li>
        <li>Category totals and transaction counts</li>
        <li>Percentage breakdown by category</li>
      </ul>
    </td>
  </tr>
</table>

## 🧱 Tech Stack

| Technology | Role |
|---|---|
| **Java 25** | Core programming language |
| **Spring Boot 4.1.2** | Application framework |
| **Spring Web MVC** | REST API endpoints |
| **Spring Data JPA** | Data access and persistence |
| **Spring Security** | Authentication and authorization |
| **JWT / JJWT** | Stateless token-based authentication |
| **Bean Validation** | Validating request data |
| **MySQL** | Relational database |
| **Maven** | Build and dependency management |
| **Postman** | Manual API testing |

## 🏁 Getting Started

### Prerequisites

Make sure you have:

- JDK 25
- MySQL Server
- Maven (or the Maven Wrapper if included in your checkout)
- Git
- Postman or another HTTP client

### 1. Clone the repository

```bash
git clone https://github.com/AK-Elshamy/expense-tracker-api.git
cd expense-tracker-api
```

### 2. Create a database

Create a local MySQL database. This example uses `expense_tracker` — use the database name configured in your local application properties.

```sql
CREATE DATABASE expense_tracker;
```

### 3. Configure your environment

Set the database URL, username, and password expected by your `application.properties` or `application.yml` file.

The application uses these JWT settings:

```properties
app.jwt.secret=${JWT_SECRET}
app.jwt.expiration-ms=900000
```

Set `JWT_SECRET` before launching the app. For HS256, use a strong, randomly generated Base64-encoded secret containing at least 32 bytes of key material. Never commit real secrets or database credentials to Git.

**Windows PowerShell** — set the JWT secret for the current terminal session:

```powershell
$env:JWT_SECRET = "<your-strong-base64-secret>"
```

> ℹ️ Check the local application configuration for the exact database environment-variable names. They may differ between development environments.

### 4. Run the application

Start with Maven:

```bash
mvn spring-boot:run
```

Or package and run the application:

```bash
mvn clean package
java -jar target/*.jar
```

The default local base URL is usually:

```text
http://localhost:8080
```

If you configured another server port, use that port instead.

### 5. Make your first request

1. Register through `POST /api/auth/register`.
2. Log in through `POST /api/auth/login` to receive a JWT.
3. In Postman, select **Authorization → Bearer Token** and paste the token.
4. Call a protected `/api/expenses` endpoint.

## 📚 API Reference

**Base URL:** `http://localhost:8080`

Protected endpoints need this header:

```http
Authorization: Bearer <your-jwt-token>
```

### 🔑 Authentication

| Method | Endpoint | Description | Auth required |
|---|---|---|---|
| `POST` | `/api/auth/register` | Register a user | No |
| `POST` | `/api/auth/login` | Log in and receive a JWT | No |
| `GET` | `/api/auth/me` | Get the authenticated user's ID and username | Yes |

**Registration request**

```json
{
  "username": "ahmed",
  "email": "ahmed@example.com",
  "password": "change-me-123"
}
```

**Login request**

```json
{
  "usernameOrEmail": "ahmed",
  "password": "change-me-123"
}
```

Use your own test credentials. The values above are examples only; never reuse example passwords in a real deployment.

### 💸 Expenses

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/expenses` | Create an expense |
| `GET` | `/api/expenses` | List the authenticated user's expenses |
| `GET` | `/api/expenses?period=week` | Filter by the `week` period option |
| `GET` | `/api/expenses?period=month` | Filter by the `month` period option |
| `GET` | `/api/expenses?period=last3months` | Filter by the `last3months` period option |
| `GET` | `/api/expenses?startDate=2026-10-01&endDate=2026-10-29` | Filter by a custom date range |
| `GET` | `/api/expenses?period=month&page=1` | Combine filtering with pagination |
| `GET` | `/api/expenses/summary` | Get a spending summary and category breakdown |
| `PUT` | `/api/expenses/{id}` | Update an expense |
| `DELETE` | `/api/expenses/{id}` | Delete an expense |

**Create expense request**

```json
{
  "amount": 245.50,
  "category": "GROCERIES",
  "description": "Weekly groceries",
  "expenseDate": "2026-10-29"
}
```

**Example summary response**

```json
{
  "totalSpent": 4500.00,
  "totalTransactions": 12,
  "startDate": "2026-10-01",
  "endDate": "2026-10-29",
  "categoryBreakdown": [
    {
      "category": "GROCERIES",
      "totalAmount": 1200.00,
      "count": 4,
      "percentage": 26.67
    }
  ]
}
```

The JSON above illustrates the response shape only. Values will depend on the authenticated user's real expense records.

### 🗂️ Expense Categories

The current category enum contains:

`GROCERIES` · `LEISURE` · `ELECTRONICS` · `UTILITIES` · `CLOTHING` · `HEALTH` · `OTHERS`

### 📄 Pagination

The API uses a zero-based `page` parameter. Page `0` is the first page, and the current service page size is **5 expenses**.

```http
GET /api/expenses?page=0
GET /api/expenses?page=1
GET /api/expenses?period=month&page=0
```

### 🚦 Common HTTP Status Codes

| Status | Meaning |
|---|---|
| `200 OK` | Request succeeded |
| `201 Created` | Resource created |
| `204 No Content` | Resource deleted successfully |
| `400 Bad Request` | Invalid parameters or request data |
| `401 Unauthorized` | Missing or invalid authentication |
| `403 Forbidden` | Access denied |
| `404 Not Found` | Resource not found |
| `409 Conflict` | Resource conflict, such as a duplicate account |
| `500 Internal Server Error` | Unexpected server-side error |

Actual status codes depend on the endpoint and the exception path.

## 🗺️ Project Structure

```text
src/main/java/com/elshamy/expensetracker/
├── config/        # Application and security configuration
├── controller/   # REST API endpoints
├── dto/          # Request and response data transfer objects
├── entity/       # JPA entities and category enum
├── exception/    # Custom exceptions and global error handling
├── repository/   # Spring Data JPA repositories
├── security/     # JWT and security components
├── service/      # Business logic
└── ExpenseTrackerApplication.java
```

The project uses a layered structure to keep HTTP handling, business rules, persistence, and data transfer models separate.

## 🔒 Security Notes

- Store passwords as secure hashes — never as plain text.
- Keep JWT secrets and database credentials outside source control.
- Ensure each authenticated user can only read or modify their own expense data.
- Use HTTPS and managed secrets when deploying to a server.
- Add automated tests for authentication, authorization, validation, and token expiry.

## 🧪 Testing

The endpoints have been manually exercised with Postman during development. Run the automated test suite with:

```bash
mvn test
```

A passing test suite is a useful check, but it is still important to cover database integration, invalid inputs, JWT expiry, and cross-user access with automated tests.

## 🛣️ Roadmap

- [ ] Expand automated service, controller, and security tests
- [ ] Add OpenAPI / Swagger documentation
- [ ] Add CI checks for the build and test suite
- [ ] Deploy the API and document the live base URL
- [ ] Add UI screenshots if/when a frontend becomes available

## 🤝 Contributing

Suggestions and improvements are welcome. For larger changes, open an issue first to discuss what you would like to change.

## 👨‍💻 Author

**Ahmed Khaled Elshamy**

- GitHub: [@AK-Elshamy](https://github.com/AK-Elshamy)
- Repository: [expense-tracker-api](https://github.com/AK-Elshamy/expense-tracker-api)

---

<div align="center">

<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&section=footer&height=125&color=0:99F6E4,50:14B8A6,100:0F766E" alt="Footer banner" />

**Made with ☕, Java, and a healthy respect for the monthly budget.**


</div>
