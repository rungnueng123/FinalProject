package com.android.orc.cloudvision;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by j.poobest on 14/12/2017 AD.
 */

public class CloudVision {
    public static void runImageDetection(String apiKey, CVRequest request, final Callback callback) {
        CVConnection.getConnection().runImageDetection(apiKey, request).enqueue(new retrofit2.Callback<CVResponse>() {
            @Override
            public void onResponse(Call<CVResponse> call, Response<CVResponse> response) {
                if (callback != null) {
                    callback.onImageDetectionSuccess(response.isSuccessful(), response.code(), response.headers(), response.body());
                }
            }

            @Override
            public void onFailure(Call<CVResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onImageDetectionFailure(t);
                }
            }
        });
    }

    public interface Callback {
        void onImageDetectionSuccess(boolean isSuccess, int statusCode, Headers headers, CVResponse cvResponse);

        void onImageDetectionFailure(Throwable t);
    }

    public static String convertBitmapToBase64String(Bitmap bitmap) {
        return convertByteArrayToBase64String(convertBitmapToByteArray(bitmap));
    }

    private static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream); //bm is the bitmap object
        return byteArrayOutputStream.toByteArray();
    }

    private static String convertByteArrayToBase64String(byte[] data) {
        return Base64.encodeToString(data, Base64.DEFAULT);
    }
}

