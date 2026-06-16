import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.Frame;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class IntroPlayer {

    private static final String ASCII_CHARS =
            " .:-=+*#%@";

    private static final int TARGET_WIDTH = 110;
    private static final int TARGET_HEIGHT = 55;

    

    public static void play(String videoPath, IntroController controller) {

        try (FFmpegFrameGrabber grabber =
                     new FFmpegFrameGrabber(videoPath)) {

            grabber.start();

            double frameRate =
                    grabber.getFrameRate();

            if (frameRate <= 0)
                frameRate = 30;

            long delayNanos =
                    (long) (1_000_000_000 / frameRate);

            Java2DFrameConverter converter =
                    new Java2DFrameConverter();

            Frame frame;

            System.out.print("\033[H\033[2J");
            System.out.flush();

            long startTime =
                    System.nanoTime();

            long frameCount = 0;

            while ((frame = grabber.grabImage()) != null) {


                if (controller.isSkipped()) {
                    break;
                }

                BufferedImage img =
                        converter.getBufferedImage(frame);

                if (img == null)
                    continue;

                BufferedImage resized =
                        resizeImage(
                                img,
                                TARGET_WIDTH,
                                TARGET_HEIGHT
                        );

                String ascii =
                        convertToCinematicColor(resized);

                ascii = centerAscii(ascii);

                long expected =
                        startTime +
                                (frameCount * delayNanos);

                long now =
                        System.nanoTime();

                long sleepMs =
                        (expected - now) / 1_000_000;

                if (sleepMs > 0)
                    Thread.sleep(sleepMs);

                System.out.print("\033[H");
                System.out.print(ascii);

                frameCount++;
            }

            grabber.stop();

        } catch (Exception e) {
            System.out.println("BLAD ODTWARZANIA INTRO:");
            e.printStackTrace();
        }
    }

    private static BufferedImage resizeImage(
            BufferedImage original,
            int w,
            int h) {

        BufferedImage resized =
                new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = resized.createGraphics();

        g.drawImage(original, 0, 0, w, h, null);

        g.dispose();

        return resized;
    }

    private static String centerAscii(String ascii) {

        StringBuilder result = new StringBuilder();

        String[] lines = ascii.split("\n");

        int longestLine = 0;

        for (String line : lines) {

            String clean = line.replaceAll("\u001B\\[[;\\d]*m", "");

            longestLine = Math.max(longestLine, clean.length());
        }

        int terminalWidth = 180;

        int leftPadding =
                Math.max(0, (terminalWidth - longestLine) / 2);

        String spaces = " ".repeat(leftPadding);

        for (int i = 0; i < 3; i++)
            result.append("\n");

        for (String line : lines) {
            result.append(spaces).append(line).append("\n");
        }

        return result.toString();
    }

    private static String convertToCinematicColor(BufferedImage image) {

        StringBuilder sb = new StringBuilder();

        int width = image.getWidth();
        int height = image.getHeight();

        int min = 255;
        int max = 0;

        int[][] gray = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int rgb = image.getRGB(x, y);

                int r = (rgb >> 16) & 255;
                int g = (rgb >> 8) & 255;
                int b = rgb & 255;

                int gval = (int) (0.299 * r + 0.587 * g + 0.114 * b);

                gray[y][x] = gval;

                min = Math.min(min, gval);
                max = Math.max(max, gval);
            }
        }

        int range = Math.max(1, max - min);

        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {

                int rgb = image.getRGB(x, y);

                int r = (rgb >> 16) & 255;
                int g = (rgb >> 8) & 255;
                int b = rgb & 255;

                int gval = gray[y][x];

                int normalized =
                        (gval - min) * 255 / range;

                normalized =
                        (int) (255 * Math.pow(normalized / 255.0, 0.85));

                normalized = 255 - normalized;

                int index =
                        normalized * (ASCII_CHARS.length() - 1) / 255;

                sb.append("\u001B[38;2;")
                        .append(r).append(";")
                        .append(g).append(";")
                        .append(b).append("m")
                        .append(ASCII_CHARS.charAt(index));
            }

            sb.append("\u001B[0m\n");
        }

        return sb.toString();
    }
}