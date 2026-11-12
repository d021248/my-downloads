# my-downloads

[![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)](https://openjdk.org/projects/jdk/25/)
[![Build](https://img.shields.io/badge/Build-Maven-blue?logo=apachemaven)](https://maven.apache.org/)
[![CI](https://github.com/d021248/my-downloads/actions/workflows/ci.yml/badge.svg)](https://github.com/d021248/my-downloads/actions/workflows/ci.yml)

Java 25 CLI wrappers for [`yt-dlp`](https://github.com/yt-dlp/yt-dlp) (YouTube) and
[`spotdl`](https://github.com/spotDL/spotify-downloader) (Spotify).
Shells out to system-installed tools; no external Java dependencies.

Part of the [`d021248`](https://github.com/d021248) learning suite alongside
[my-try](https://github.com/d021248/my-try) · [my-demos](https://github.com/d021248/my-demos) · [my-lisp](https://github.com/d021248/my-lisp).

---

## Prerequisites

```bash
pip install yt-dlp spotdl
```

---

## Build

```bash
mvn compile      # compile (no tests — wraps external CLI tools)
```

Java 25 with `--enable-preview`. Spotless (Google Java Format) runs on every compile.

---

## Configuration

The output base directory is controlled by the `DOWNLOAD_DIR` environment variable
(default: `downloads` relative to the working directory).

| Sub-directory | Content |
|---|---|
| `$DOWNLOAD_DIR/youtube` | YouTube downloads |
| `$DOWNLOAD_DIR/spotify` | Spotify downloads |

```bash
export DOWNLOAD_DIR=/path/to/my/music
```

---

## Running

```bash
# Download a YouTube video
mvn exec:java -Dexec.mainClass="d021248.mytry.downloads.youtube.YoutubeDownloadDemoRunner"

# Search YouTube and list results
mvn exec:java -Dexec.mainClass="d021248.mytry.downloads.youtube.YoutubeSearchDemoRunner"

# Download from Spotify
mvn exec:java -Dexec.mainClass="d021248.mytry.downloads.spotify.SpotifyDownloadDemoRunner"
```

---

## Structure

```
src/main/java/d021248/mytry/downloads/
├── youtube/
│   ├── YoutubeDownloadDemo          low-level yt-dlp wrapper
│   ├── YoutubeDownloadDemoRunner    entry point; provides downloadDir(String sub)
│   ├── YoutubeSearchAndDownload     search + batch download
│   ├── YoutubeSearchDemoRunner      entry point
│   └── PlainDownloadDemo            minimal single-URL download
└── spotify/
    ├── SpotifyDownloadDemo          low-level spotdl wrapper
    └── SpotifyDownloadDemoRunner    entry point
```
