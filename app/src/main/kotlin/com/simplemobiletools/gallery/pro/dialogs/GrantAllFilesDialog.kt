package com.simplemobiletools.gallery.pro.dialogs

import android.os.Build
import androidx.annotation.RequiresApi
import com.simplemobiletools.gallery.pro.activities.BaseSimpleActivity
import com.simplemobiletools.gallery.pro.extensions.applyColorFilter
import com.simplemobiletools.gallery.pro.extensions.getAlertDialogBuilder
import com.simplemobiletools.gallery.pro.extensions.getProperTextColor
import com.simplemobiletools.gallery.pro.extensions.setupDialogStuff
import com.simplemobiletools.gallery.pro.databinding.DialogGrantAllFilesBinding
import com.simplemobiletools.gallery.pro.extensions.launchGrantAllFilesIntent

@RequiresApi(Build.VERSION_CODES.R)
class GrantAllFilesDialog(val activity: BaseSimpleActivity) {
    init {
        val binding = DialogGrantAllFilesBinding.inflate(activity.layoutInflater)
        binding.grantAllFilesImage.applyColorFilter(activity.getProperTextColor())

        activity.getAlertDialogBuilder()
            .setPositiveButton(com.simplemobiletools.commons.R.string.ok) { _, _ -> activity.launchGrantAllFilesIntent() }
            .setNegativeButton(com.simplemobiletools.commons.R.string.cancel, null)
            .apply {
                activity.setupDialogStuff(binding.root, this) { }
            }
    }
}
