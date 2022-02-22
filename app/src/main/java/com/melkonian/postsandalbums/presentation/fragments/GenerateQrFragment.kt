package com.melkonian.postsandalbums.presentation.fragments

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.melkonian.postsandalbums.databinding.FmtGenerateQrBinding
import com.melkonian.postsandalbums.presentation.base.BaseFragment
import com.melkonian.postsandalbums.presentation.viewmodels.GenerateQrViewModel
import com.melkonian.postsandalbums.utils.setupToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenerateQrFragment : BaseFragment() {
    companion object {
        private const val QR_CODE_IMAGE_SIZE = 600
    }

    private lateinit var binding: FmtGenerateQrBinding

    override val viewModel: GenerateQrViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FmtGenerateQrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(binding.toolbar)
        binding.textToGenerate.addTextChangedListener(PhraseToQrEditText())

        binding.generateQrBtn.setOnClickListener { generateQR() }
    }

    private fun generateQR() {
        val textToGenerate = viewModel.phaseToQr.value?.toString() ?: return
        val writer = MultiFormatWriter()

        try {
            val mMatrix: BitMatrix = writer.encode(
                textToGenerate,
                BarcodeFormat.QR_CODE,
                QR_CODE_IMAGE_SIZE,
                QR_CODE_IMAGE_SIZE
            )

            val mEncoder = BarcodeEncoder()
            val mBitmap: Bitmap = mEncoder.createBitmap(mMatrix)
            binding.qrResultCode.setImageBitmap(mBitmap)

            val manager =
                requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(binding.textToGenerate.applicationWindowToken, 0)
        } catch (e: WriterException) {
            e.printStackTrace()
        }

        // TODO for scanning via camera
//        try {
//            barcodeLauncher.launch(ScanOptions())
//        } catch (e: ActivityNotFoundException) {
//            e.printStackTrace()
//        }
    }

    // TODO for scanning via camera
//    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
//        if (result.contents == null) {
//            Timber.d(getString(R.string.generate_qr_canceled_msg))
//        } else {
//            Timber.d(getString(R.string.fmt_generate_qr_scanned_w_results_msg, result.contents))
//        }
//    }

    private inner class PhraseToQrEditText : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            editable?.let { viewModel.setPhraseToQr(it.toString().trim()) }
        }
    }
}