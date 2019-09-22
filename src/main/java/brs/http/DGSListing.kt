package brs.http

import brs.Attachment
import brs.BurstException
import brs.Constants
import brs.DependencyProvider
import brs.http.JSONResponses.INCORRECT_DGS_LISTING_DESCRIPTION
import brs.http.JSONResponses.INCORRECT_DGS_LISTING_NAME
import brs.http.JSONResponses.INCORRECT_DGS_LISTING_TAGS
import brs.http.JSONResponses.MISSING_NAME
import brs.http.common.Parameters.DESCRIPTION_PARAMETER
import brs.http.common.Parameters.NAME_PARAMETER
import brs.http.common.Parameters.PRICE_NQT_PARAMETER
import brs.http.common.Parameters.QUANTITY_PARAMETER
import brs.http.common.Parameters.TAGS_PARAMETER
import brs.util.Convert
import com.google.gson.JsonElement
import javax.servlet.http.HttpServletRequest

internal class DGSListing internal constructor(private val dp: DependencyProvider) : CreateTransaction(dp, arrayOf(APITag.DGS, APITag.CREATE_TRANSACTION), NAME_PARAMETER, DESCRIPTION_PARAMETER, TAGS_PARAMETER, QUANTITY_PARAMETER, PRICE_NQT_PARAMETER) {
    internal override fun processRequest(request: HttpServletRequest): JsonElement {
        var name = Convert.emptyToNull(request.getParameter(NAME_PARAMETER))
        val description = Convert.nullToEmpty(request.getParameter(DESCRIPTION_PARAMETER))
        val tags = Convert.nullToEmpty(request.getParameter(TAGS_PARAMETER))
        val priceNQT = ParameterParser.getPriceNQT(request)
        val quantity = ParameterParser.getGoodsQuantity(request)

        if (name == null) {
            return MISSING_NAME
        }
        name = name.trim { it <= ' ' }
        if (name.length > Constants.MAX_DGS_LISTING_NAME_LENGTH) {
            return INCORRECT_DGS_LISTING_NAME
        }

        if (description.length > Constants.MAX_DGS_LISTING_DESCRIPTION_LENGTH) {
            return INCORRECT_DGS_LISTING_DESCRIPTION
        }

        if (tags.length > Constants.MAX_DGS_LISTING_TAGS_LENGTH) {
            return INCORRECT_DGS_LISTING_TAGS
        }

        val account = dp.parameterService.getSenderAccount(request)
        val attachment = Attachment.DigitalGoodsListing(dp, name, description, tags, quantity, priceNQT, dp.blockchain.height)
        return createTransaction(request, account, attachment)
    }
}
