package es.hol.galvisoft.aerolina.data.config;

import android.content.Context;

import java.io.IOException;
import java.util.Scanner;

public class AssetsHelper {
    public static String readFileAsset(Context ctx, String filename) throws IOException {
        StringBuilder builder = new StringBuilder();

        Scanner s = new Scanner(ctx.getAssets().open(filename));
        while (s.hasNextLine()) {
            builder.append(s.nextLine());
        }
        return builder.toString();
    }

    public static boolean fileExists(Context ctx, String filename) {
        try {
            for (String f : ctx.getAssets().list("")) {
                if (f.equals(filename))
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
