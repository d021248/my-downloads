package d021248.mytry.downloads.youtube;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Path;

/// Plain Java demo: downloads a file from a direct video URL (not YouTube, as
/// YouTube requires special handling).
public final class PlainDownloadDemo {
  /// Downloads a file from a direct URL to the specified output file.
  ///
  /// @param fileURL    The direct URL to the file.
  /// @param outputFile The path to save the downloaded file.
  public static void downloadFile(String fileURL, String outputFile) {
    var uri = URI.create(fileURL);

    // Ensure output directory exists
    var outputPath = Path.of(outputFile);
    var parentDir = outputPath.getParent();
    if (parentDir != null) {
      try {
        Files.createDirectories(parentDir);
      } catch (IOException e) {
        System.err.println("Failed to create output directory: " + e.getMessage());
        return;
      }
    }

    try {
      var client = HttpClient.newHttpClient();
      var request = HttpRequest.newBuilder().uri(uri).build();
      var response = client.send(request, BodyHandlers.ofInputStream());
      try (var in = new BufferedInputStream(response.body());
          FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
        byte[] dataBuffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
          fileOutputStream.write(dataBuffer, 0, bytesRead);
        }
        System.out.println("Download completed: " + outputFile);
      }
    } catch (IOException | InterruptedException e) {
      System.err.println("Download failed: " + e.getMessage());
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
    }
  }

  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("Usage: java PlainDownloadDemo <direct-file-URL> <output-file>");
      return;
    }
    String fileURL = args[0];
    String outputFile = args[1];
    downloadFile(fileURL, outputFile);
  }
}
