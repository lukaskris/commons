package com.evodream.app.accountcommons.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.evodream.app.accountcommons.R;
import com.google.common.base.Optional;

import java.io.File;

/**
 * @author Erick Pranata
 * @since 2017/06/22
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class AndroidUtil {
	public enum ShowcaseMode {
		CIRCLE, SQUARE, SQUARE_FULL_WIDTH, NONE
	}

	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		View view = activity.getCurrentFocus();
		if (view == null) {
			view = new View(activity);
		}
		if (imm != null) {
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	public static void hideSoftKeyboard(Context context, View view) {
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
			if (imm != null) {
				imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
			}
		}
	}
	public static void showErrorMessage(Context context, Throwable throwable) {
		String message = "Unknown Error: debug please...";
		if (throwable instanceof Exception) {
			message = throwable.getMessage();
		}
		if (context != null) {
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}
	}

	public static Snackbar showMessage(View container, String message) {
		if (container == null) return null;

		Snackbar snackbar = Snackbar.make(container, message, Snackbar.LENGTH_LONG)
				.setAction("OK", new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						// do nothing
					}
				});
		snackbar.show();

		return snackbar;
	}

	public static ProgressDialog showProgressDialog(Context context) {
		return showProgressDialog(context, context.getString(R.string.lit_processing_), context.getString(R.string.lit_please_wait));
	}

	public static ProgressDialog showProgressDialog(Context context, String title, String message) {
		return ProgressDialog.show(context, title, message, true, false);
	}

	public static void switchToInanimate(int containerResId, Fragment fragment, FragmentManager fragmentManager) {
		fragmentManager
				.beginTransaction()
				.replace(containerResId, fragment)
				.commit();
	}

	public static void switchTo(int containerResId, Fragment fragment, FragmentManager fragmentManager) {
		switchTo(containerResId, fragment, R.anim.fade_in_fast, R.anim.fade_out_fast, fragmentManager);
	}

	public static void switchTo(int containerResId, Fragment fragment, int enterAnimResId, int exitAnimResId, FragmentManager fragmentManager) {
		fragmentManager
				.beginTransaction()
				.setCustomAnimations(enterAnimResId, exitAnimResId)
				.replace(containerResId, fragment)
				.commit();
	}

	public static void backstackSwitchTo(int containerResId, Fragment fragment, int enterAnimResId, int exitAnimResId, int popEnterAnimResId, int popExitAnimResId, FragmentManager fragmentManager, String tag) {
		fragmentManager
				.beginTransaction()
				.setCustomAnimations(enterAnimResId, exitAnimResId, popEnterAnimResId, popExitAnimResId)
				.replace(containerResId, fragment, tag)
				.addToBackStack(null)
				.commit();
	}

	public static void tintMenuIcon(Context context, MenuItem item, @ColorRes int color) {
		if(item.getIcon() != null) {
			Drawable normalDrawable = item.getIcon();
			Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
			DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(context, color));

			item.setIcon(wrapDrawable);
		}
	}

	public static Optional<String> getFilePathFromUri(Context context, Uri contentUri) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			if (contentUri.getScheme() != null && "file".equalsIgnoreCase(contentUri.getScheme())) {
				return Optional.of(contentUri.getPath());
			}

			String path = null;
			Cursor cursor = null;
			try {
				cursor = context.getContentResolver().query(contentUri, null, null, null, null);
				if (cursor != null) {
					int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					path = cursor.getString(column_index);
				}
			} catch (Exception e) {
				ExceptionUtil.handleException(e);
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
			return Optional.fromNullable(path);
		} else {
			return Optional.fromNullable(getPath(context, contentUri));
		}
	}

	@SuppressLint("NewApi")
	private static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] {
						split[1]
				};

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri The Uri to query.
	 * @param selection (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	private static String getDataColumn(Context context, Uri uri, String selection,
	                                    String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = {
				column
		};

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
					null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}


	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	private static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	private static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	private static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	private static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}


	public static Button createTutorialButton(Context context, ViewGroup.LayoutParams layoutParams, String text, int resBackground, View.OnClickListener onClickListener, @Nullable Object tag) {
		Button button = new Button(context);
		button.setLayoutParams(layoutParams);
		button.setText(text);
		button.setOnClickListener(onClickListener);
		button.setBackground(ContextCompat.getDrawable(context, resBackground));
		button.setTextColor(ContextCompat.getColor(context, android.R.color.white));
		button.setTag(tag);
		button.setTextSize(12);
		return button;
	}

	public static boolean isDebuggable(Context context) {
		return context != null && (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
	}

	public static Optional<Intent> getCameraIntent(Context context, File photoFile, Uri photoUri) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (intent.resolveActivity(context.getPackageManager()) != null) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
			intent.putExtra("file_path", photoFile);
			return Optional.of(intent);
		} else {
			return Optional.absent();
		}
	}

	public static Optional<Intent> getGalleryIntent(Context context) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		if (intent.resolveActivity(context.getPackageManager()) != null) {
			return Optional.of(Intent.createChooser(intent, context.getString(R.string.lit_select_picture)));
		} else {
			return Optional.absent();
		}
	}

	public static int getResId(Context context, String resName, String resourceType) {
		return context.getResources().getIdentifier(resName, resourceType, context.getPackageName());
	}

	public static void tintVector(Context context, MenuItem menuItem, int resColorId) {
		Drawable normalDrawable = menuItem.getIcon();
		Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
		DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(context, resColorId));
		menuItem.setIcon(wrapDrawable);
	}
}
