package com.webtechsolution.ghumantey.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.jakewharton.rxbinding3.view.RxView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.webtechsolution.ghumantey.R;
import com.webtechsolution.ghumantey.databinding.ImageDialogBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.disposables.DisposableContainer;

import static com.webtechsolution.ghumantey.helpers.UtilsKt.dpToPx;
import static com.webtechsolution.ghumantey.helpers.UtilsKt.moveToInternal;
import static com.webtechsolution.ghumantey.helpers.UtilsKt.saveBitmap;

public class ImagePicker {
    private static final int GALLERY_REQUEST = 12;
    private static final int CAMERA_REQUEST = 121;

    public static void pickImage(DisposableContainer container, Fragment fragment, boolean allowMultiple) {
        Dialog dialog = new Dialog(fragment.requireContext());
        ImageDialogBinding binding = DataBindingUtil.inflate(fragment.getLayoutInflater(),
                R.layout.image_dialog, null, false);
        dialog.setContentView(binding.getRoot());
        container.add(RxView.clicks(binding.tvTakePhoto)
                .doOnNext(unit -> dialog.dismiss())
                .subscribe(unit -> pickImageFromSource(container, ImageSource.CAMERA, fragment, allowMultiple)));
        container.add(RxView.clicks(binding.tvCancel)
                .subscribe(unit -> dialog.dismiss()));
        container.add(RxView.clicks(binding.tvFromLibrary).
                doOnNext(unit -> dialog.dismiss()).
                subscribe(unit -> pickImageFromSource(container, ImageSource.GALLERY, fragment, allowMultiple)));
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = dpToPx(400, fragment.requireActivity());
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    public static void pickImageFromSource(DisposableContainer container, ImageSource sources, Fragment fragment, boolean allowMultiple) {
        switch (sources) {
            case CAMERA:
                container.add(new RxPermissions(fragment)
                        .request(Manifest.permission.CAMERA)
                        .filter(it -> it)
                        .subscribe(aBoolean -> {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            fragment.startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        }));
                break;
            case GALLERY: {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, allowMultiple);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                fragment.startActivityForResult(Intent.createChooser(intent, "Select Pictures"), GALLERY_REQUEST);
                break;
            }
        }
    }

    public static Observable<List<Uri>> onImagePickResult(Fragment fragment, int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        try {
            if (requestCode == GALLERY_REQUEST) {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null & data.getClipData() != null) {
                        List<Uri> images = new ArrayList<>();
                        int count = data.getClipData().getItemCount();
                        for (int i = 0; i < count; i++)
                            images.add(data.getClipData().getItemAt(i).getUri());
                        return onPickImage(fragment, images);
                    } else if (data.getData() != null) {
                        Uri imagePath = data.getData();
                        return onPickImage(fragment, Collections.singletonList(imagePath));
                    }
                }
            } else if (requestCode == CAMERA_REQUEST) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                if (photo != null) return onCaptureImage(fragment, photo);
            }
        } catch (Exception e) {
            return Observable.error(new Throwable());
        }
        return Observable.empty();
    }

    private static Observable<List<Uri>> onCaptureImage(Fragment fragment, Bitmap photo) {
        return saveBitmap(fragment.requireContext(), photo)
                .observeOn(AndroidSchedulers.mainThread())
                .map(Collections::singletonList);
    }

    private static Observable<List<Uri>> onPickImage(Fragment fragment, List<Uri> images) {
        return Observable.fromIterable(images)
                .flatMap(it -> moveToInternal(fragment.requireContext(), it).observeOn(AndroidSchedulers.mainThread()))
                .toList()
                .toObservable();
    }


    public enum ImageSource {
        CAMERA, GALLERY
    }
}