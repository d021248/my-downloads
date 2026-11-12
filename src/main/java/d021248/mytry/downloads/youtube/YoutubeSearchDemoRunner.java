package d021248.mytry.downloads.youtube;

/// Demo runner for YouTube search and download functionality.
/// Shows how to search for videos by topic and download them.
///
/// Note: Requires yt-dlp to be installed on the system.
/// Install with: pip install yt-dlp
public final class YoutubeSearchDemoRunner {
  public static void main() {
    String searchTopic = "java programming tutorial";
    int maxResults = 3;
    var outputDir = YoutubeDownloadDemoRunner.downloadDir("youtube");

    System.out.println("YouTube Search & Download Demo");
    System.out.println("==============================");
    System.out.println("Search topic: " + searchTopic);
    System.out.println("Max results: " + maxResults);
    System.out.println("Output directory: " + outputDir);
    System.out.println();

    System.out.println("=== SEARCH MODE ===");
    var urls = YoutubeSearchAndDownload.searchYouTube(searchTopic, maxResults);
    System.out.println("\nFound " + urls.size() + " videos:");
    for (int i = 0; i < urls.size(); i++) {
      System.out.println((i + 1) + ". " + urls.get(i));
    }

    System.out.println("\n=== DOWNLOAD MODE ===");
    System.out.println("To download the videos, uncomment the next line in the code:");
    System.out.println(
        "YoutubeSearchAndDownload.searchAndDownload(searchTopic, maxResults, outputDir);");

    // To actually download, uncomment this:
    // YoutubeSearchAndDownload.searchAndDownload(searchTopic, maxResults, outputDir);
  }
}
