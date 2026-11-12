package d021248.mytry.downloads.youtube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/// YouTube search and download utility using yt-dlp.
/// Allows searching for videos by topic/keywords and downloading them.
public final class YoutubeSearchAndDownload {

  /// Searches YouTube for videos matching the query.
  ///
  /// @param searchQuery The search terms/topic to look for
  /// @param maxResults  Maximum number of results to return (default: 5)
  /// @return List of video URLs found
  public static List<String> searchYouTube(String searchQuery, int maxResults) {
    List<String> videoUrls = new ArrayList<>();

    if (!YoutubeDownloadDemo.isYtDlpAvailable()) {
      System.err.println("Error: yt-dlp is not installed or not in PATH.");
      return videoUrls;
    }

    System.out.println("Searching YouTube for: " + searchQuery);

    ProcessBuilder pb =
        new ProcessBuilder(
            "yt-dlp",
            "--get-id",
            "--flat-playlist",
            "--max-downloads",
            String.valueOf(maxResults),
            "ytsearch" + maxResults + ":" + searchQuery);
    pb.redirectErrorStream(true);

    try {
      Process process = pb.start();
      try (BufferedReader reader =
          new BufferedReader(new InputStreamReader(process.getInputStream()))) {
        String line;
        while ((line = reader.readLine()) != null) {
          if (!line.isEmpty() && line.length() == 11) { // YouTube video IDs are 11 characters
            String url = "https://www.youtube.com/watch?v=" + line;
            videoUrls.add(url);
            System.out.println("Found: " + url);
          }
        }
      }
      process.waitFor();
    } catch (IOException e) {
      System.err.println("IO Error: " + e.getMessage());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.err.println("Search interrupted: " + e.getMessage());
    }

    return videoUrls;
  }

  /// Searches for videos and downloads them directly.
  ///
  /// @param searchQuery The search terms/topic to look for
  /// @param maxResults  Maximum number of videos to download
  /// @param outputDir   Directory to save the videos
  public static void searchAndDownload(String searchQuery, int maxResults, String outputDir) {
    // Validate and create output directory
    var outputPath = Path.of(outputDir);
    try {
      Files.createDirectories(outputPath);
    } catch (IOException e) {
      System.err.println("Failed to create output directory: " + e.getMessage());
      return;
    }

    if (!YoutubeDownloadDemo.isYtDlpAvailable()) {
      System.err.println("Error: yt-dlp is not installed or not in PATH.");
      System.err.println("Install it with: pip install yt-dlp");
      return;
    }

    System.out.println("Searching and downloading: " + searchQuery);
    System.out.println("Max results: " + maxResults);
    System.out.println("Output directory: " + outputDir);
    System.out.println();

    ProcessBuilder pb =
        new ProcessBuilder(
            "yt-dlp",
            "-o",
            outputDir + "/%(title)s.%(ext)s",
            "--max-downloads",
            String.valueOf(maxResults),
            "ytsearch" + maxResults + ":" + searchQuery);
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
        System.out.println("\nDownload completed successfully.");
      } else {
        System.out.println("\nDownload failed with exit code: " + exitCode);
      }
    } catch (IOException e) {
      System.err.println("IO Error: " + e.getMessage());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.err.println("Download interrupted: " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("Usage: java YoutubeSearchAndDownload <search-mode> <arguments...>");
      System.out.println();
      System.out.println("Modes:");
      System.out.println("  search <query> [max-results]");
      System.out.println("    - Search for videos and list URLs");
      System.out.println("    - Example: search \"java tutorial\" 5");
      System.out.println();
      System.out.println("  download <query> <max-results> <output-dir>");
      System.out.println("    - Search and download videos directly");
      System.out.println("    - Example: download \"relaxing music\" 3 ./music");
      return;
    }

    String mode = args[0].toLowerCase();

    switch (mode) {
      case "search" -> {
        if (args.length < 2) {
          System.out.println("Error: search query required");
          return;
        }
        String query = args[1];
        int maxResults = args.length > 2 ? Integer.parseInt(args[2]) : 5;
        List<String> urls = searchYouTube(query, maxResults);
        System.out.println("\nFound " + urls.size() + " videos.");
      }
      case "download" -> {
        if (args.length < 4) {
          System.out.println("Error: query, max-results, and output-dir required");
          return;
        }
        String query = args[1];
        int maxResults = Integer.parseInt(args[2]);
        String outputDir = args[3];
        searchAndDownload(query, maxResults, outputDir);
      }
      default -> System.out.println("Unknown mode: " + mode);
    }
  }
}
