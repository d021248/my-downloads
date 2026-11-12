package d021248.mytry.downloads.youtube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public final class YoutubeDownloadDemo {
  /// Downloads a YouTube video using yt-dlp command line tool.
  ///
  /// @param url       The URL of the YouTube video.
  /// @param outputDir The directory to save the downloaded video.
  public static void downloadYoutubeVideo(String url, String outputDir) {
    // Validate and create output directory
    var outputPath = Path.of(outputDir);
    try {
      Files.createDirectories(outputPath);
    } catch (IOException e) {
      System.err.println("Failed to create output directory: " + e.getMessage());
      return;
    }

    // Check if yt-dlp is available
    if (!isYtDlpAvailable()) {
      System.err.println("Error: yt-dlp is not installed or not in PATH.");
      System.err.println("Install it with: pip install yt-dlp");
      return;
    }

    ProcessBuilder pb = new ProcessBuilder("yt-dlp", "-o", outputDir + "/%(title)s.%(ext)s", url);
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

  /// Checks if yt-dlp is available in the system PATH.
  ///
  /// @return true if yt-dlp is available, false otherwise
  public static boolean isYtDlpAvailable() {
    try {
      Process process = new ProcessBuilder("yt-dlp", "--version").start();
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
      System.out.println("Usage: java YoutubeDownloadDemo <YouTube-URL> <output-directory>");
      return;
    }
    String url = args[0];
    String outputDir = args[1];
    downloadYoutubeVideo(url, outputDir);
  }
}
