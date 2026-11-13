# CLAUDE.md — my-downloads

## Project Overview

CLI wrappers for `yt-dlp` (YouTube) and `spotdl` (Spotify).
Java 25, Maven. No external Java dependencies — shells out to system tools.
No tests (wraps external CLI tools; not unit-testable in isolation).

> **Architectural excellence is not a means but a goal.**
> No ad-hoc solutions. No classes with more than one responsibility. No hidden side effects.
> No premature abstractions. Every design decision is explicit and justified — the code is the learning material.

Sibling repos: [my-try](../my-try) (core Java learning), [my-demos](../my-demos), [my-lisp](../my-lisp).

---

## Build

```bash
mvn compile              # compile only
```

Java version: **25** (`--enable-preview` active).

**Prerequisites:** `yt-dlp` and `spotdl` must be installed on the system.
```bash
pip install yt-dlp spotdl
```

**Output directory:** controlled by the `DOWNLOAD_DIR` env var (default: `downloads`).
YouTube files go to `$DOWNLOAD_DIR/youtube`, Spotify to `$DOWNLOAD_DIR/spotify`.

---

## Key Conventions

### Language
All code, comments, Javadoc, and `package-info.java` files must be in **English**.

### Demo Classes

Two valid structures:

**A — Behaviour demo** (shows how to *use* an API):
```java
public final class FooDemo {
  public static void main() { }          // no String[] args
  private static void helper() {}       // helpers after main
}
```

**B — Concept demo** (defines the types it demonstrates):
```java
public final class BarDemo {
  record Pair<A, B>(A first, B second) {}  // inner types first
  public static void main() { }
}
```

Rules for both: `final` class, `main()` without `String[] args`.

### Documentation
- Use `///` Markdown Javadoc (Java 23+), not `/** */`
- Every `package` needs a `package-info.java`
- `@since 1.0` on `package-info.java` blocks
- Keep `///` lines under ~100 chars (spotless wraps at ~100)

### Java 25 Style
- `var` where the type is obvious from the right-hand side
- `"...".formatted(...)` over `String.format` or `+` chains
- Never inline FQCNs — always `import`
- Records for immutable data

---

## Package Map

```
src/main/java/d021248/mytry/downloads/
│
├── youtube/   YoutubeDownloadDemo, YoutubeSearchAndDownload, PlainDownloadDemo
│              YoutubeDownloadDemoRunner (entry point; also provides downloadDir())
│              YoutubeSearchDemoRunner
└── spotify/   SpotifyDownloadDemo
               SpotifyDownloadDemoRunner
```
