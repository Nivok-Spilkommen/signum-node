package brs.transaction.advancedPayment

import brs.*
import brs.transactionduplicates.TransactionDuplicationKey
import brs.util.toUnsignedString
import com.google.gson.JsonObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.ByteBuffer

class SubscriptionCancel(dp: DependencyProvider) : AdvancedPayment(dp) {
    override val subtype = SUBTYPE_ADVANCED_PAYMENT_SUBSCRIPTION_CANCEL
    override val description = "Subscription Cancel"

    override fun parseAttachment(buffer: ByteBuffer, transactionVersion: Byte) = Attachment.AdvancedPaymentSubscriptionCancel(dp, buffer, transactionVersion)
    override fun parseAttachment(attachmentData: JsonObject) = Attachment.AdvancedPaymentSubscriptionCancel(dp, attachmentData)

    override fun applyAttachmentUnconfirmed(transaction: Transaction, senderAccount: Account): Boolean {
        logger.trace("TransactionType SUBSCRIPTION_CANCEL")
        val attachment = transaction.attachment as Attachment.AdvancedPaymentSubscriptionCancel
        dp.subscriptionService.addRemoval(attachment.subscriptionId)
        return true
    }

    override fun applyAttachment(transaction: Transaction, senderAccount: Account, recipientAccount: Account?) {
        val attachment = transaction.attachment as Attachment.AdvancedPaymentSubscriptionCancel
        dp.subscriptionService.removeSubscription(attachment.subscriptionId)
    }

    override fun undoAttachmentUnconfirmed(transaction: Transaction, senderAccount: Account) = Unit

    override fun getDuplicationKey(transaction: Transaction): TransactionDuplicationKey {
        val attachment = transaction.attachment as Attachment.AdvancedPaymentSubscriptionCancel
        return TransactionDuplicationKey(SubscriptionCancel::class, attachment.subscriptionId.toUnsignedString())
    }

    override fun validateAttachment(transaction: Transaction) {
        val attachment = transaction.attachment as Attachment.AdvancedPaymentSubscriptionCancel
        if (attachment.subscriptionId == null) {
            throw BurstException.NotValidException("Subscription cancel must include subscription id")
        }

        val subscription = dp.subscriptionService.getSubscription(attachment.subscriptionId)!!

        if (subscription.senderId != transaction.senderId && subscription.recipientId != transaction.senderId) {
            throw BurstException.NotValidException("Subscription cancel can only be done by participants")
        }

        if (!dp.subscriptionService.isEnabled) {
            throw BurstException.NotYetEnabledException("Subscription cancel not yet enabled")
        }
    }

    override fun hasRecipient() = false

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(SubscriptionCancel::class.java)
    }
}
