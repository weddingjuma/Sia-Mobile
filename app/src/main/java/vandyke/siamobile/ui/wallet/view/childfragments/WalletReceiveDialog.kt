/*
 * Copyright (c) 2017 Nicholas van Dyke
 *
 * This file is subject to the terms and conditions defined in 'LICENSE.md'
 */

package vandyke.siamobile.ui.wallet.view.childfragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import kotlinx.android.synthetic.main.fragment_wallet_receive.*
import net.glxn.qrgen.android.QRCode
import vandyke.siamobile.R
import vandyke.siamobile.util.GenUtil
import vandyke.siamobile.util.SnackbarUtil
import vandyke.siamobile.util.observe

class WalletReceiveDialog : BaseWalletFragment() {
    override val layout: Int = R.layout.fragment_wallet_receive

    override fun create(view: View, savedInstanceState: Bundle?) {
        viewModel.address.observe(this) {
            if (isVisible) {
                receiveAddress.text = it.address
                setQrCode(it.address)
            }
        }

        viewModel.getAddress()

        receiveAddress.setOnClickListener {
            GenUtil.copyToClipboard(context!!, receiveAddress.text)
            SnackbarUtil.snackbar(view, "Copied receive address", Snackbar.LENGTH_SHORT)
        }
    }

    fun setQrCode(walletAddress: String) {
        walletQrCode.visibility = View.VISIBLE
        walletQrCode.setImageBitmap(QRCode.from(walletAddress).bitmap())
    }
}
