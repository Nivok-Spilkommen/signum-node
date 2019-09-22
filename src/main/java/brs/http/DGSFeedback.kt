package brs.http

import brs.Attachment
import brs.BurstException
import brs.DependencyProvider
import brs.http.JSONResponses.GOODS_NOT_DELIVERED
import brs.http.JSONResponses.INCORRECT_ACCOUNT
import brs.http.JSONResponses.INCORRECT_PURCHASE
import brs.http.common.Parameters.PURCHASE_PARAMETER
import com.google.gson.JsonElement
import javax.servlet.http.HttpServletRequest

internal class DGSFeedback internal constructor(private val dp: DependencyProvider) : CreateTransaction(dp, arrayOf(APITag.DGS, APITag.CREATE_TRANSACTION), PURCHASE_PARAMETER) {
    internal override fun processRequest(request: HttpServletRequest): JsonElement {
        val purchase = dp.parameterService.getPurchase(request)
        val buyerAccount = dp.parameterService.getSenderAccount(request)

        if (buyerAccount.id != purchase.buyerId) {
            return INCORRECT_PURCHASE
        }
        if (purchase.encryptedGoods == null) {
            return GOODS_NOT_DELIVERED
        }

        val sellerAccount = dp.accountService.getAccount(purchase.sellerId) ?: return INCORRECT_ACCOUNT
        val attachment = Attachment.DigitalGoodsFeedback(dp, purchase.id, dp.blockchain.height)

        return createTransaction(request, buyerAccount, sellerAccount.id, 0, attachment)
    }
}
