package org.example.flexibleuiusingfragments.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeadlinesProvider {

    public static class Headline {

        public final String fID, fTitle, fDetails;

        public Headline(String id, String title, String details) {
            fID = id;
            fTitle = title;
            fDetails = details;
        }

        @Override
        public String toString() {
            return fTitle;
        }
    }

    /** A map of headlines, by ID. */
    public static final Map<String, Headline> ITEMS_MAP = new HashMap<>();
    public static final List<Headline>        ITEMS     = new ArrayList<>();

    static {    // just adding some sample items
        for (int i = 1; i <= 9; i++) {
            final Headline newHeadline = new Headline(genId(i), genContent(i), genDetails(i));
            ITEMS_MAP.put(newHeadline.fID, newHeadline);
            ITEMS.add(newHeadline);
        }
    }

    private static String genId(int i) {
        return String.valueOf(i);
    }

    private static String genContent(int i) {
        return "Item " + i;
    }

    private static String genDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }
}
