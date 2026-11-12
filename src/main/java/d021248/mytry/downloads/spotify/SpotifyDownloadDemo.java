package d021248.mytry.downloads.spotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

/// Spotify download utility using spotdl command line tool.
/// Downloads tracks, playlists, albums, or artists from Spotify.
///
/// Requires spotdl to be installed: pip install spotdl
public final class SpotifyDownloadDemo {
  /// Downloads Spotify content using spotdl command line tool.
  ///
  /// @param url       The URL of the Spotify track, playlist, album, or artist.
  /// @param outputDir The directory to save the downloaded music.
  public static void downloadSpotifyContent(String url, String outputDir) {
    // Validate and create output directory
    var outputPath = Path.of(outputDir);
    try {
      Files.createDirectories(outputPath);
    } catch (IOException e) {
      System.err.println("Failed to create output directory: " + e.getMessage());
      return;
    }

    // Check if spotdl is available
    if (!isSpotdlAvailable()) {
      System.err.println("Error: spotdl is not installed or not in PATH.");
      System.err.println("Install it with: pip install spotdl");
      return;
    }

    ProcessBuilder pb = new ProcessBuilder("spotdl", url, "--output", outputDir);
    pb.redirectErrorStream(true);
    try {
      Process process = pb.start();
      try (BufferedReader reader =
          new BufferedReader(new InputStreamReader(process.getInputStream()))) {
        String line;
        while ((line = reader.readLine()) != null) {
          System.out.println(line);
        }
      }
      int exitCode = process.waitFor();
      if (exitCode == 0) {
        System.out.println("Download completed successfully.");
      } else {
        System.out.println("Download failed with exit code: " + exitCode);
      }
    } catch (IOException e) {
      System.err.println("IO Error: " + e.getMessage());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.err.println("Download interrupted: " + e.getMessage());
    }
  }

  /// Checks if spotdl is available in the system PATH.
  ///
  /// @return true if spotdl is available, false otherwise
  private static boolean isSpotdlAvailable() {
    try {
      Process process = new ProcessBuilder("spotdl", "--version").start();
      int exitCode = process.waitFor();
      return exitCode == 0;
    } catch (IOException e) {
      return false;
    } catch (InterruptedException _) {
      Thread.currentThread().interrupt();
      return false;
    }
  }

  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("Usage: java SpotifyDownloadDemo <Spotify-URL> <output-directory>");
      System.out.println();
      System.out.println("Examples:");
      System.out.println("  Track:    spotdl https://open.spotify.com/track/... ./downloads");
      System.out.println("  Playlist: spotdl https://open.spotify.com/playlist/... ./downloads");
      System.out.println("  Album:    spotdl https://open.spotify.com/album/... ./downloads");
      System.out.println("  Artist:   spotdl https://open.spotify.com/artist/... ./downloads");
      return;
    }
    String url = args[0];
    String outputDir = args[1];
    downloadSpotifyContent(url, outputDir);
  }
}
