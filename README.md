# Yellowfin-Java-REST

Implementation of the YellowfinBI REST API with classes and methods for use in Java.

# Getting Started

To get started, please visit https://sandbox.yellowfinbi.com/ and follow the link to register.
You will then receive an email with credentials to the developer sandbox so you can start playing with pulling the demo content into your Java applications.

The best place to start is using Single Sign-on to provide a quick link between your application and Yellowfin.

# Yellowfin-Java-REST

The **Yellowfin Java REST** repo provides a Java-ready wrapper for the **Yellowfin BI REST API (v4)** — designed for Yellowfin **9.15 and later**.
It makes it simple to integrate Yellowfin analytics features directly into your Java applications using clean, interface-based code.

This library mirrors the structure and naming conventions of the [Yellowfin Delphi REST](https://github.com/DelphiABall/Yellowfin-Delphi-REST), [Yellowfin C# REST](https://github.com/DelphiABall/Yellowfin-CSharp-REST), and [Yellowfin TypeScript REST](https://github.com/DelphiABall/Yellowfin-TypeScript-REST) wrappers, making it easy for developers to move between languages and recognise the same patterns and API coverage.

---

## Requirements

- **Java 11+** (uses `java.net.http.HttpClient`)
- **Maven 3.8+** for building
- Only two runtime dependencies: `org.json` and `junit`

---

## Setup

```bash
# Clone the repo
git clone https://github.com/DelphiABall/Yellowfin-Java-REST.git
cd Yellowfin-Java-REST/Source

# Build
mvn clean compile
```

---

## Running the Example

```bash
cd Source
mvn exec:java
```

On first run, you'll be prompted for your Yellowfin Sandbox URL, username, password, and tenant ID. These are saved to a local `YFSandbox.ini` file (password base64-encoded, not encrypted — do not use in production).

You can also pass overrides via system properties:

```bash
mvn exec:java -Dyf.url=https://sandbox.yellowfinbi.com -Dyf.user=admin@example.com -Dyf.password=secret -Dyf.org=org1
```

The example demonstrates: refresh tokens, auto-refreshing access tokens, user listing, user detail, SSO login token generation, report listing, SSO direct-to-report, and favourite/unfavourite toggling.

---

## Project Structure

```
Yellowfin-Java-REST/
└── Source/
    ├── pom.xml
    └── src/
        └── com/yellowfinbi/
            ├── api/
            │   ├── common/              # Shared infrastructure
            │   │   ├── IYFServerDetails  # Server connection config
            │   │   ├── IYFLoadFromJSON   # Base deserialization interface
            │   │   ├── IYFJSON           # Bidirectional JSON interface
            │   │   ├── YFFactoryRegistry # Generic factory for all models
            │   │   ├── YFFactoryRegistration  # Registers all defaults
            │   │   ├── YFJsonHelper      # Safe JSON reading utilities
            │   │   ├── YFModelList<T>    # Generic list deserialization
            │   │   └── YFServerConfig    # Credentials + server details
            │   ├── transport/            # HTTP transport layer
            │   │   ├── YFTransport       # java.net.http wrapper with auth
            │   │   ├── YFGenericExecutor # executeTyped<T> + executeRaw
            │   │   └── YFTransportResponse
            │   ├── security/             # Token management
            │   │   ├── YFTokenRequests   # All token CRUD operations
            │   │   └── YFAccessTokenManager  # Auto-refreshing access token
            │   └── sections/             # API sections (three-file pattern)
            │       ├── api/              # API version info
            │       ├── cache/            # Cache management
            │       ├── categories/       # Content categories
            │       ├── content/          # Content item listing
            │       ├── dashboards/       # Dashboard listing
            │       ├── datasources/      # Data source configuration
            │       ├── filters/          # Filter and sort options
            │       ├── health/           # Cluster health
            │       ├── images/           # Image retrieval
            │       ├── orgs/             # Client organisations
            │       ├── presentations/    # Presentation listing
            │       ├── reports/          # Report listing and metadata
            │       ├── roles/            # Role and function management
            │       ├── usergroups/       # User group management
            │       └── users/            # User CRUD, preferences, favourites
            └── connector/
                ├── ExampleProject.java   # Console demo
                └── YFSettingsINI.java    # INI file reader/writer
```

### Three-File Pattern

Each API section follows the same pattern as the Delphi, C#, and TypeScript wrappers:

| File | Purpose |
|------|---------|
| `IYF*.java` | Interface definitions (e.g. `IYFUser`, `IYFSimpleUserModel`) |
| `YF*.<section>.java` | Package-private class implementations with `loadFromJSON` and factory registration |
| `YF*Api.java` | Static API methods calling Yellowfin endpoints |

### API Sections

| Section | Package | Description |
|---------|---------|-------------|
| API Info | `sections.api` | API version and server information |
| Cache | `sections.cache` | Cache management and clearing |
| Categories | `sections.categories` | Content category management |
| Content | `sections.content` | Content item listing and search |
| Dashboards | `sections.dashboards` | Dashboard listing and report retrieval |
| Data Sources | `sections.datasources` | Data source configuration and access |
| Filters | `sections.filters` | Filter values, search options, and sort options |
| Health | `sections.health` | Cluster node health and cache status |
| Images | `sections.images` | Image retrieval (binary) |
| Orgs | `sections.orgs` | Client organisation management |
| Presentations | `sections.presentations` | Presentation listing and report retrieval |
| Reports | `sections.reports` | Report listing and metadata |
| Roles | `sections.roles` | Role and function management |
| User Groups | `sections.usergroups` | User group management |
| Users | `sections.users` | User CRUD, preferences, favourites, and SSO |

---

## Key Concepts

- **Authentication**: The Yellowfin API uses 3 tokens: Refresh, Access, and Login. See https://developers.yellowfinbi.com/dev/api-docs/current/

- **Transport**: All API calls go through `YFTransport` which uses `java.net.http.HttpClient` and adds the Yellowfin auth header (`{AppName} ts={epochMs}, nonce={uuid}, token={token}`). No third-party HTTP libraries required.

- **Generic Executor**: `YFGenericExecutor.executeTyped<T>()` is the DRY mechanism — API methods pass the interface class and endpoint, the executor handles HTTP call, JSON parsing, and deserialization via the factory registry. `executeRaw()` handles operations like DELETE that don't return a typed body.

- **Fully Interface-Based Design**: Every API model is defined as an interface (`IYFUser`, `IYFSimpleUserModel`, etc.) with package-private implementations. This promotes separation of concerns and easy mocking for testing.

- **Extensible via Factory Registry**: Any default implementation can be replaced with your own class by registering it in the factory registry. Call `YFFactoryRegistration.registerAll()` at startup, then override any type:
  ```java
  YFFactoryRegistry.registerFactory(IYFUser.class, MyCustomUser::new);
  ```

- **org.json**: The library uses `org.json` for JSON handling — the only runtime dependency. All models deserialize from `JSONObject` via `IYFLoadFromJSON`, and bidirectional models implement `IYFJSON` with `toJSON()` / `asJSON()` methods.

---

## Additional Resources

[![Watch the video](https://img.youtube.com/vi/qLfgiq51ceI/0.jpg)](https://www.youtube.com/watch?v=qLfgiq51ceI)
- [Watch Getting Started with the Yellowfin REST API and Java](https://www.youtube.com/watch?v=qLfgiq51ceI)

- [Download the Yellowfin & ISV Integration Infodoc (PDF)](https://img.en25.com/Web/Embarcadero/%7B03c73f74-5dd1-4d10-a1f0-e6eedb18822d%7D_Infodoc_-_Yellowfin_and_ISV_Integration.pdf)

---

## Cross-Language Learning

This library deliberately mirrors the [Delphi](https://github.com/DelphiABall/Yellowfin-Delphi-REST), [C#](https://github.com/DelphiABall/Yellowfin-CSharp-REST), and [TypeScript](https://github.com/DelphiABall/Yellowfin-TypeScript-REST) wrappers — same naming conventions, same pattern, same factory approach. If you know one, you'll recognise the other.

## References

- Delphi reference implementation: https://github.com/DelphiABall/Yellowfin-Delphi-REST
- C# implementation: https://github.com/DelphiABall/Yellowfin-CSharp-REST
- TypeScript implementation: https://github.com/DelphiABall/Yellowfin-TypeScript-REST
- Yellowfin REST API v4: https://developers.yellowfinbi.com/dev/api-docs/current/
- Developer sandbox: https://sandbox.yellowfinbi.com/
