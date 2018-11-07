package com.androidacademy.msk.exerciseproject.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;
import com.androidacademy.msk.exerciseproject.network.model.NetworkNewsItem;

import java.util.Collections;
import java.util.List;

public class NewsDataUtils {

    private NewsDataUtils() {
        throw new UnsupportedOperationException("There should be no class instance");
    }

    @Nullable
    public static String getPreviewImageUrl(@NonNull NetworkNewsItem item) {
        if (item.getMultimedia() != null && item.getMultimedia().size() > 1) {
            // Get the position of the best quality preview image.
            // It is the second position from the end of a list.
            int previewImagePosition = item.getMultimedia().size() - 2;
            return item.getMultimedia().get(previewImagePosition).getUrl();
        }
        return null;
    }

    @Nullable
    public static String getFullsizeImageUrl(@NonNull NetworkNewsItem item) {
        if (item.getMultimedia() != null && item.getMultimedia().size() > 1) {
            // Get the position of the fullsize image. It is the latest position a list.
            int previewImagePosition = item.getMultimedia().size() - 1;
            return item.getMultimedia().get(previewImagePosition).getUrl();
        }
        return null;
    }

    @NonNull
    public static List<DbNewsItem> sortByDate(@NonNull List<DbNewsItem> newsItems) {

        Collections.sort(newsItems, (o1, o2) -> {
            String timestamp1 = o1.getPublishedDate();
            String timestamp2 = o2.getPublishedDate();
            if (timestamp1 != null && timestamp2 == null) {
                return 1;
            }
            if (timestamp1 == null && timestamp2 == null) {
                return 0;
            }
            if (timestamp1 == null && timestamp2 != null) {
                return -1;
            }

            return Long.compare(
                    DateUtils.getUnixDate(timestamp2),
                    DateUtils.getUnixDate(timestamp1));
        });

        return newsItems;
    }


}