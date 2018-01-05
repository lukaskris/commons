package com.evodream.app.accountcommons.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Erick Pranata
 * @since 2017/09/14
 */

@SuppressWarnings("unused")
public class BaseFragment extends Fragment {
    @SuppressLint("UseSparseArrays")
    private Map<Integer, HandlerHolder> resultHandlers = new HashMap<>();
    @SuppressLint("UseSparseArrays")
    private Map<Integer, PermissionHandler> permissionHandlers = new HashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    private int getNewRequestCode() {
        return counter.incrementAndGet();
    }

    protected void startActivityForResult(Intent intent, int successResultCode, SuccessResultHandler handler) {
        int requestCode = getNewRequestCode();
        resultHandlers.put(requestCode, new HandlerHolder(successResultCode, handler));
        startActivityForResult(intent, requestCode);
    }

    protected void startActivityForResult(Intent intent, ResultHandler handler) {
        int requestCode = getNewRequestCode();
        resultHandlers.put(requestCode, new HandlerHolder(handler));
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        HandlerHolder handler = resultHandlers.get(requestCode);
        if (handler != null) {
            if (handler.successHandler != null) {
                if (resultCode == handler.successResultCode) {
                    handler.successHandler.onResult(data);
                }
                return;
            }
            if (handler.resultHandler != null) {
                handler.resultHandler.onResult(resultCode, data);
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    public interface SuccessResultHandler {
        void onResult(Intent data);
    }

    @SuppressWarnings("WeakerAccess")
    public interface ResultHandler {
        void onResult(int resultCode, Intent data);
    }

    private class HandlerHolder {
        private Integer successResultCode;
        private SuccessResultHandler successHandler;
        private ResultHandler resultHandler;

        HandlerHolder(Integer successResultCode, SuccessResultHandler successHandler) {
            this.successResultCode = successResultCode;
            this.successHandler = successHandler;
        }

        HandlerHolder(ResultHandler resultHandler) {
            this.resultHandler = resultHandler;
        }
    }

    protected void checkPermission(@NonNull String[] permissions, @NonNull PermissionHandler handler) {
        boolean allPermissionsGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) allPermissionsGranted = false;
        }

        if (!allPermissionsGranted) {
            int requestCode = getNewRequestCode();
            requestPermissions(permissions, requestCode);
            permissionHandlers.put(requestCode, handler);
        } else {
            handler.onPermissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHandler handler = permissionHandlers.get(requestCode);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (handler != null) handler.onPermissionGranted();
        } else {
            if (handler != null) handler.onPermissionDenied();
        }
    }

    @SuppressWarnings("WeakerAccess")
    public interface PermissionHandler {
        void onPermissionGranted();
        void onPermissionDenied();
    }
}
