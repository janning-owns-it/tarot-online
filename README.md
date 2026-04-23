<h1 align="center">🌙 Tarot Online – Spring Boot API ☀️</h1>


<p align="center">
A RESTful API built in <strong>Java 25</strong> with <strong>Spring Boot</strong> to generate intelligent Tarot readings using the <strong>OpenAI API</strong>.<br>
The system receives the querent’s question, draws cards, interprets the result, and returns a coherent narrative with mystical tone.
</p>

---

## ✨ Features

- 🔮 **AI-generated Tarot reading** (OpenAI)
- 🚦 **Daily IP limit** (Redis + TTL until midnight in Dublin)
- 🏗️ **Clean package-based architecture**
- 🌍 **Timezone support: Europe/Dublin**

---

## 🛠️ Technologies Used

| Technology | Version |
|-----------|---------|
| Java | **25** |
| Spring Boot | **3.5.7+** |
| Redis | **8.2.3** |
| Apache Maven | **3.9.11+** |
| OpenAI API | **GPT-4.6.1** |
| Docker | **28.5.2** |

---

## 📂 Package Structure

```txt
src/main/java/com/janning_owns_it/tarot
│
├── component
│   └── PromptManager
│
├── config
│   ├── OpenAiConfig
│   └── RedisConfig
│
├── controller
│   └── TarotController
│
├── exception
│   ├── ApiException
│   └── ApiExceptionHandler
│
├── helper
│   └── RedisTTLHelper
│
├── model
│   ├── Deck
│   └── TarotReadingResponse
│
├── service
│   ├── IpRateLimitService
│   ├── OpenAiIntegration
│   ├── ShufflerService
│   └── TarotService
│
└── TarotApplication
```

---

## 🗄️ Redis – TTL Until Midnight (Dublin)

The IP key expires **exactly at midnight in Ireland**, regardless of the server’s local time.

```java
public class RedisTTLHelper {

    private static final ZoneId IRELAND_ZONE = ZoneId.of("Europe/Dublin");

    public static long getSecondsUntilMidnight() {
        ZonedDateTime now = ZonedDateTime.now(IRELAND_ZONE);
        return Duration.between(now, now.toLocalDate().plusDays(1)
                .atStartOfDay(IRELAND_ZONE)).getSeconds();
    }
}
```
---

## 🚦 Daily IP Limit

Each IP can perform a limited number of requests per day.
The counter is stored in Redis and expires at midnight in Dublin.

If exceeded:

```json
{
    "error": "Daily usage limit of 3 requests per IP has been reached."
}
```

---

## 🔑 OpenAI API Key Configuration

Create a `.env` file in the root of your project containing the following environment variables:

```.env
OPENAI_API_KEY=your-openai-api-key-here
OPENAI_MAX_TOKENS=1000
OPENAI_CHAT_MODEL=gpt-4.1
```

These variables will be automatically loaded in your application using `Dotenv`:

```java
public class OpenAiConfig {
    private static final Dotenv dotenv = Dotenv.load();

    public static final String API_KEY = dotenv.get("OPENAI_API_KEY");
    public static final String MODEL = dotenv.get("OPENAI_CHAT_MODEL");
    public static final int MAX_TOKENS = Integer.parseInt(dotenv.get("OPENAI_MAX_TOKENS"));
}
```

---

## ⚙️ How to Run the Project

### 1. Clone the repository

```bash
git clone https://github.com/janning-owns-it/tarot-online.git
cd tarot-online
```
### 2. Run the project

Before running the project, make sure you have configured your OpenAI settings in the previous step.

You can run the project in **two different ways**:

#### > Run with Docker (recommended for production)

```bash
docker compose build && docker compose up -d
```
##### OR

#### > Run with Maven (local environment)

```bash
mvn clean install && mvn spring-boot:run
```

---

## 📡 Endpoint

### `GET /tarot-online`

### Parameters

| Name              | Type   | Required | Description               |
|-------------------|--------|----------|---------------------------|
| querentsQuestion  | String | ✔        | The querent’s question    |

### Example Request

Always use URL encoded parameters when calling this endpoint.

Example:

```bash
GET /tarot-online?querentsQuestion=Will%20I%20find%20my%20true%20love%20soon%3F
```

Response:

```json
{
    "arcaneResponse": "The Judgement card in the past reveals a period of introspection and self-evaluation that has prepared you for a new beginning in love. The Knight of Cups reversed in the present suggests that you may need to work on balancing your emotions and approach to relationships before finding true love. The 5 of Swords reversed in the future indicates that letting go of past conflicts and embracing self-empowerment will pave the way for a deep and meaningful connection with your true love. Trust in the process and be open to new experiences with an open heart.",
    "sortedCardsInOrder": [
        "Judgement - Major Arcana",
        "Knight of Cups - Minor Arcana (Reversed)",
        "5 of Swords - Minor Arcana (Reversed)"
    ]
}
```