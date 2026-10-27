# Refactoring playground

Java code from my talks and workshops on refactoring and unit testing, collected in one place.

Every example is small, runs on its own and shows a typical problem from real business code: an anemic model, an object that grew too big, logic hidden behind Spring configuration, tests that break with every change. That makes the repository a base for practice. Refactor it by hand, or give it to an AI agent and check whether the result is still correct and still readable.

The talks and workshops were given in Polish 🇵🇱 PL. The code and names are in English.

## What's inside

| Directory | Origin | What it shows | Stack |
| --- | --- | --- | --- |
| [`talk-refactoring-without-harm/`](talk-refactoring-without-harm) | talk "Refactoring - How to do it without harming yourself?", WDI 2024 | seven refactoring cases | Java 17, Spring Boot 3.2, Maven |
| [`talk-spring-harder-and-easier/`](talk-spring-harder-and-easier) | talk "How to make your life harder and easier with Spring", WDI 2025 | Spring features that help and hurt | Java 21, Spring Boot 3.4, Gradle |
| [`workshop-legacy/`](workshop-legacy) | workshop on refactoring legacy code | eight exercises in an insurance and parcel locker domain | Java 21, Spring Boot 3.5, Gradle |
| [`workshop-unit-testing/`](workshop-unit-testing) | workshop "Unit testing revisited" | exercises and solutions, from test basics to observable behavior | Java, Spock (Groovy), Gradle |
| [`legacy-football-events/`](legacy-football-events) | open-source project [djarza/football-events](https://github.com/djarza/football-events), MIT | a real event-driven system that is hard to change: a larger refactoring target | Java 17, Kafka Streams, Debezium, PostgreSQL, Docker |

Each directory is a separate project with its own build. The git history of the original repositories is kept.

### talk-refactoring-without-harm

Packages are numbered in the order of the talk:

1. `_1_vo`: value objects (`VinNumber`, `RegistrationNumber`) instead of raw strings
2. `_2_bigobject`: one large `Vehicle` object used by everyone
3. `_3_anemicmodel`: an anemic `Battery` with logic in `BatteryService`
4. `_4_externalclass`: an object from an external library leaking into the domain
5. `_5_splitentities`: splitting `Vehicle` and `Policy` into separate entities
6. `_6_complexlogic`: a class with too many methods
7. `_7_cognitiveload`: cognitive load in a `Book` model

```bash
cd talk-refactoring-without-harm && ./mvnw test
```

### talk-spring-harder-and-easier

A Spring Boot application with services wired through configuration classes, an anti-corruption layer for an external API, a fake and a very slow client, and an `ApplicationEventStopwatchListener` registered in `spring.factories` that measures time between startup events up to `ApplicationReadyEvent`.

```bash
cd talk-spring-harder-and-easier && ./gradlew test
```

### workshop-legacy

| Exercise | Domain |
| --- | --- |
| `_01-exercise` | statistics downloaded from an external system, premium account granting |
| `_02-exercise` | parcel locker reservations within a time window |
| `_03-exercise` | insurance claims against a policy |
| `_04-exercise` | buying a policy with signature verification |
| `_05-exercise` | insurance premium based on vehicles |
| `_06-exercise` | claims, policy numbers and reporting |
| `_07-exercise` | premium and agent commission with taxes and margins |
| `_08-exercise` | policy report with an N+1 query problem |

```bash
cd workshop-legacy && ./gradlew test
```

### workshop-unit-testing

`exercises/` is the starting point and `solution/` is where it ends. Modules go in order:

`01-basics` → `02-base` → `02a-stateful` → `02b-testschools` → `02bc-helpers` → `02c-fakes` → `02d-grouping` → `02e-spring` → `03-refactor` → `04-observable-behavior` → `05-given` → `06-assert` → `07-tdvc`

```bash
cd workshop-unit-testing/exercises && ./gradlew test
```

### legacy-football-events

Football statistics built from events with Kafka Streams, split into microservices: `football-match`, `football-player`, `football-view-basic`, `football-view-top`, `football-ui` and a shared `football-common`. The original author is [djarza](https://github.com/djarza/football-events), released under the MIT license (see [`LICENSE`](legacy-football-events/LICENSE)). I migrated it to Java 17 and got it running again in 2024.

It is the largest example here and it is hard to change: model code shared between services and logic spread across stream topologies. Use it to check how an AI agent copes with refactoring a system rather than a single class.

See [`legacy-football-events/README.md`](legacy-football-events/README.md) for the architecture and how to run it with Docker.

## Using it with an AI agent

Pick one directory, ask the agent to refactor it, and compare its version with the original:

- Do the existing tests still pass without changes?
- Did the agent keep the domain language or replace it with technical names?
- Is the result easier to read than the starting code, or only shorter?

## License

MIT, see [`LICENSE`](LICENSE). `legacy-football-events/` keeps the MIT license of its original author, see [`legacy-football-events/LICENSE`](legacy-football-events/LICENSE).
