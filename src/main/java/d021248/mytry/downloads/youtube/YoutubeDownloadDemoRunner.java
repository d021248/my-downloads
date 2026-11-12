package d021248.mytry.downloads.youtube;

import java.nio.file.Path;

/// Demo runner for YouTube download functionality.
/// Downloads a sample YouTube video to a local directory.
///
/// Note: Requires yt-dlp to be installed on the system.
/// Install with: pip install yt-dlp
public final class YoutubeDownloadDemoRunner {
  public static void main() {
    String url = "ytsearch10:Van Halen official music video";
    var outputDir = downloadDir("youtube");

    System.out.println("YouTube Download Demo");
    System.out.println("=====================");
    System.out.println("Downloading from: " + url);
    System.out.println("Saving to: " + outputDir);
    System.out.println();

    YoutubeDownloadDemo.downloadYoutubeVideo(url, outputDir);
  }

  public static String downloadDir(String sub) {
    var base = System.getenv().getOrDefault("DOWNLOAD_DIR", "downloads");
    return Path.of(base, sub).toString();
  }
}
