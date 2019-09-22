package brs.http

import brs.Blockchain
import brs.BurstException
import brs.http.common.Parameters.ACCOUNT_PARAMETER
import brs.http.common.ResultFields.REWARD_RECIPIENT_RESPONSE
import brs.services.AccountService
import brs.services.ParameterService
import brs.util.toUnsignedString
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import javax.servlet.http.HttpServletRequest

internal class GetRewardRecipient(private val parameterService: ParameterService, private val blockchain: Blockchain, private val accountService: AccountService) : APIServlet.JsonRequestHandler(arrayOf(APITag.ACCOUNTS, APITag.MINING, APITag.INFO), ACCOUNT_PARAMETER) {
    internal override fun processRequest(request: HttpServletRequest): JsonElement {
        val response = JsonObject()

        val account = parameterService.getAccount(request)
        val assignment = accountService.getRewardRecipientAssignment(account)
        val height = blockchain.lastBlock.height.toLong()
        when {
            assignment == null -> response.addProperty(REWARD_RECIPIENT_RESPONSE, account.id.toUnsignedString())
            assignment.fromHeight > height + 1 -> response.addProperty(REWARD_RECIPIENT_RESPONSE, assignment.prevRecipientId.toUnsignedString())
            else -> response.addProperty(REWARD_RECIPIENT_RESPONSE, assignment.recipientId.toUnsignedString())
        }

        return response
    }
}
