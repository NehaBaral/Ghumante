package com.webtechsolution.ghumantey.helpers;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FormUtils {
    public static String getRealPathFromURIPath(Context context, Uri contentURI) {
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public static MultipartBody.Part prepareFilePart(String partName, Uri fileUri, Context context) {
        File file = new File(getRealPathFromURIPath(context, fileUri));
        RequestBody requestFile = RequestBody.create(ImageUtility.getStreamByteFromImage(file), MediaType.parse("image/jpg"));
        return MultipartBody.Part.createFormData(partName, UUID.randomUUID().toString().concat(".jpg"), requestFile);
    }

    public static MultipartBody.Part prepareString(String name, String value) {
        if (value == null)
            value = "";
        return MultipartBody.Part.createFormData(name, value);
    }
}
