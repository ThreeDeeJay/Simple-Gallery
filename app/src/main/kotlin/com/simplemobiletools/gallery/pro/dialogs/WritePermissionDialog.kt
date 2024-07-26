package com.simplemobiletools.gallery.pro.dialogs

import android.app.Activity
import android.text.Html
import androidx.appcompat.app.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.simplemobiletools.commons.R
import com.simplemobiletools.commons.activities.BaseSimpleActivity
import com.simplemobiletools.commons.compose.alert_dialog.AlertDialogState
import com.simplemobiletools.commons.compose.alert_dialog.rememberAlertDialogState
import com.simplemobiletools.commons.compose.extensions.MyDevices
import com.simplemobiletools.commons.compose.theme.AppThemeSurface
import com.simplemobiletools.commons.databinding.DialogWritePermissionBinding
import com.simplemobiletools.commons.databinding.DialogWritePermissionOtgBinding
import com.simplemobiletools.commons.extensions.getAlertDialogBuilder
import com.simplemobiletools.commons.extensions.humanizePath
import com.simplemobiletools.commons.extensions.setupDialogStuff


class WritePermissionDialog(activity: Activity, val mode: Mode, val callback: () -> Unit) {

    @Immutable
    sealed class Mode {
        @Immutable
        data object Otg : Mode()

        @Immutable
        data object SdCard : Mode()

        @Immutable
        data class OpenDocumentTreeSDK30(val path: String) : Mode()

        @Immutable
        data object CreateDocumentSDK30 : Mode()
    }

    private var dialog: AlertDialog? = null

    init {
        val sdCardView = DialogWritePermissionBinding.inflate(activity.layoutInflater, null, false)
        val otgView = DialogWritePermissionOtgBinding.inflate(
            activity.layoutInflater,
            null,
            false
        )

        var dialogTitle = R.string.confirm_storage_access_title

        val glide = Glide.with(activity)
        val crossFade = DrawableTransitionOptions.withCrossFade()
        when (mode) {
            Mode.Otg -> {
                otgView.writePermissionsDialogOtgText.setText(R.string.confirm_usb_storage_access_text)
                glide.load(R.drawable.img_write_storage_otg).transition(crossFade)
                    .into(otgView.writePermissionsDialogOtgImage)
            }

            Mode.SdCard -> {
                glide.load(R.drawable.img_write_storage).transition(crossFade)
                    .into(sdCardView.writePermissionsDialogImage)
                glide.load(R.drawable.img_write_storage_sd).transition(crossFade)
                    .into(sdCardView.writePermissionsDialogImageSd)
            }

            is Mode.OpenDocumentTreeSDK30 -> {
                dialogTitle = R.string.confirm_folder_access_title
                val humanizedPath = activity.humanizePath(mode.path)
                otgView.writePermissionsDialogOtgText.text =
                    Html.fromHtml(
                        activity.getString(
                            R.string.confirm_storage_access_android_text_specific,
                            humanizedPath
                        )
                    )
                glide.load(R.drawable.img_write_storage_sdk_30).transition(crossFade)
                    .into(otgView.writePermissionsDialogOtgImage)

                otgView.writePermissionsDialogOtgImage.setOnClickListener {
                    dialogConfirmed()
                }
            }

            Mode.CreateDocumentSDK30 -> {
                dialogTitle = R.string.confirm_folder_access_title
                otgView.writePermissionsDialogOtgText.text =
                    Html.fromHtml(activity.getString(R.string.confirm_create_doc_for_new_folder_text))
                glide.load(R.drawable.img_write_storage_create_doc_sdk_30).transition(crossFade)
                    .into(otgView.writePermissionsDialogOtgImage)

                otgView.writePermissionsDialogOtgImage.setOnClickListener {
                    dialogConfirmed()
                }
            }
        }

        activity.getAlertDialogBuilder()
            .setPositiveButton(R.string.ok) { _, _ -> dialogConfirmed() }
            .setOnCancelListener {
                BaseSimpleActivity.funAfterSAFPermission?.invoke(false)
                BaseSimpleActivity.funAfterSAFPermission = null
            }
            .apply {
                activity.setupDialogStuff(
                    if (mode == Mode.SdCard) sdCardView.root else otgView.root,
                    this,
                    dialogTitle
                ) { alertDialog ->
                    dialog = alertDialog
                }
            }
    }

    private fun dialogConfirmed() {
        dialog?.dismiss()
        callback()
    }
}

@Composable
fun WritePermissionAlertDialog(
    alertDialogState: AlertDialogState,
    modifier: Modifier = Modifier
) {
    //todo in progress
}

@Composable
@MyDevices
private fun WritePermissionAlertDialogPreview() {
    AppThemeSurface {
        WritePermissionAlertDialog(rememberAlertDialogState())
    }
}
