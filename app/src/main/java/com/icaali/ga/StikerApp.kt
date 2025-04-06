package com.icaali.ga

import android.app.Application
import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryPurchasesParams
import com.facebook.drawee.backends.pipeline.Fresco
import com.icaali.PrefManager

internal class StikerApp : Application() {

    private lateinit var billingClient: BillingClient
    private var prefManager: PrefManager? = null

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this);
        appContext = applicationContext
        //        Fresco.initialize(this);
        prefManager = PrefManager(this)
        setupBillingClient()
    }

    private fun setupBillingClient() {
        billingClient = BillingClient.newBuilder(this)
            .enablePendingPurchases()
            .setListener(purchasesUpdatedListener)
            .build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    checkPreviousPurchases()
                }
            }

            override fun onBillingServiceDisconnected() {
                // Coba reconnect nanti
            }
        })
    }

    private fun checkPreviousPurchases() {
        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.INAPP)
            .build()

        billingClient.queryPurchasesAsync(params) { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                var found = false
                purchases.forEach { purchase ->
                    if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                        if (purchase.products.contains("remove.ads.one.time")) {
                            found = true
                        }
                    }
                }
                prefManager?.savePurchaseStatus(found)
            }
        }
    }



    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->

    }

    companion object {
        var appContext: Context? = null
            private set
    }
}