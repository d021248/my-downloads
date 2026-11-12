package d021248.mytry.downloads.spotify;

import d021248.mytry.downloads.youtube.YoutubeDownloadDemoRunner;

/// Demo runner for Spotify download functionality.
/// Downloads a sample Spotify track/playlist to a local directory.
///
/// Note: Requires spotdl to be installed on the system.
/// Install with: pip install spotdl
public final class SpotifyDownloadDemoRunner {
  public static void main() {
    String url =
        "https://open.spotify.com/track/3n3Ppam7vgaVa1iaRUc9Lp"; // Example: "Mr. Brightside"
    var outputDir = YoutubeDownloadDemoRunner.downloadDir("spotify");

    System.out.println("Spotify Download Demo");
    System.out.println("=====================");
    System.out.println("Downloading from: " + url);
    System.out.println("Saving to: " + outputDir);
    System.out.println();

    SpotifyDownloadDemo.downloadSpotifyContent(url, outputDir);
  }
}
