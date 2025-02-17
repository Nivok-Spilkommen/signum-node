/*
 * This file is generated by jOOQ.
 */
package brs.schema;


import brs.schema.tables.Account;
import brs.schema.tables.AccountAsset;
import brs.schema.tables.Alias;
import brs.schema.tables.AliasOffer;
import brs.schema.tables.AskOrder;
import brs.schema.tables.Asset;
import brs.schema.tables.AssetTransfer;
import brs.schema.tables.At;
import brs.schema.tables.AtState;
import brs.schema.tables.BidOrder;
import brs.schema.tables.Block;
import brs.schema.tables.Escrow;
import brs.schema.tables.EscrowDecision;
import brs.schema.tables.FlywaySchemaHistory;
import brs.schema.tables.Goods;
import brs.schema.tables.IndirectIncoming;
import brs.schema.tables.Peer;
import brs.schema.tables.Purchase;
import brs.schema.tables.PurchaseFeedback;
import brs.schema.tables.PurchasePublicFeedback;
import brs.schema.tables.RewardRecipAssign;
import brs.schema.tables.Subscription;
import brs.schema.tables.Trade;
import brs.schema.tables.Transaction;
import brs.schema.tables.UnconfirmedTransaction;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Db extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>DB</code>
     */
    public static final Db DB = new Db();

    /**
     * The table <code>DB.account</code>.
     */
    public final Account ACCOUNT = Account.ACCOUNT;

    /**
     * The table <code>DB.account_asset</code>.
     */
    public final AccountAsset ACCOUNT_ASSET = AccountAsset.ACCOUNT_ASSET;

    /**
     * The table <code>DB.alias</code>.
     */
    public final Alias ALIAS = Alias.ALIAS;

    /**
     * The table <code>DB.alias_offer</code>.
     */
    public final AliasOffer ALIAS_OFFER = AliasOffer.ALIAS_OFFER;

    /**
     * The table <code>DB.ask_order</code>.
     */
    public final AskOrder ASK_ORDER = AskOrder.ASK_ORDER;

    /**
     * The table <code>DB.asset</code>.
     */
    public final Asset ASSET = Asset.ASSET;

    /**
     * The table <code>DB.asset_transfer</code>.
     */
    public final AssetTransfer ASSET_TRANSFER = AssetTransfer.ASSET_TRANSFER;

    /**
     * The table <code>DB.at</code>.
     */
    public final At AT = At.AT;

    /**
     * The table <code>DB.at_state</code>.
     */
    public final AtState AT_STATE = AtState.AT_STATE;

    /**
     * The table <code>DB.bid_order</code>.
     */
    public final BidOrder BID_ORDER = BidOrder.BID_ORDER;

    /**
     * The table <code>DB.block</code>.
     */
    public final Block BLOCK = Block.BLOCK;

    /**
     * The table <code>DB.escrow</code>.
     */
    public final Escrow ESCROW = Escrow.ESCROW;

    /**
     * The table <code>DB.escrow_decision</code>.
     */
    public final EscrowDecision ESCROW_DECISION = EscrowDecision.ESCROW_DECISION;

    /**
     * The table <code>DB.flyway_schema_history</code>.
     */
    public final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>DB.goods</code>.
     */
    public final Goods GOODS = Goods.GOODS;

    /**
     * The table <code>DB.indirect_incoming</code>.
     */
    public final IndirectIncoming INDIRECT_INCOMING = IndirectIncoming.INDIRECT_INCOMING;

    /**
     * The table <code>DB.peer</code>.
     */
    public final Peer PEER = Peer.PEER;

    /**
     * The table <code>DB.purchase</code>.
     */
    public final Purchase PURCHASE = Purchase.PURCHASE;

    /**
     * The table <code>DB.purchase_feedback</code>.
     */
    public final PurchaseFeedback PURCHASE_FEEDBACK = PurchaseFeedback.PURCHASE_FEEDBACK;

    /**
     * The table <code>DB.purchase_public_feedback</code>.
     */
    public final PurchasePublicFeedback PURCHASE_PUBLIC_FEEDBACK = PurchasePublicFeedback.PURCHASE_PUBLIC_FEEDBACK;

    /**
     * The table <code>DB.reward_recip_assign</code>.
     */
    public final RewardRecipAssign REWARD_RECIP_ASSIGN = RewardRecipAssign.REWARD_RECIP_ASSIGN;

    /**
     * The table <code>DB.subscription</code>.
     */
    public final Subscription SUBSCRIPTION = Subscription.SUBSCRIPTION;

    /**
     * The table <code>DB.trade</code>.
     */
    public final Trade TRADE = Trade.TRADE;

    /**
     * The table <code>DB.transaction</code>.
     */
    public final Transaction TRANSACTION = Transaction.TRANSACTION;

    /**
     * The table <code>DB.unconfirmed_transaction</code>.
     */
    public final UnconfirmedTransaction UNCONFIRMED_TRANSACTION = UnconfirmedTransaction.UNCONFIRMED_TRANSACTION;

    /**
     * No further instances allowed
     */
    private Db() {
        super("DB", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            Account.ACCOUNT,
            AccountAsset.ACCOUNT_ASSET,
            Alias.ALIAS,
            AliasOffer.ALIAS_OFFER,
            AskOrder.ASK_ORDER,
            Asset.ASSET,
            AssetTransfer.ASSET_TRANSFER,
            At.AT,
            AtState.AT_STATE,
            BidOrder.BID_ORDER,
            Block.BLOCK,
            Escrow.ESCROW,
            EscrowDecision.ESCROW_DECISION,
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            Goods.GOODS,
            IndirectIncoming.INDIRECT_INCOMING,
            Peer.PEER,
            Purchase.PURCHASE,
            PurchaseFeedback.PURCHASE_FEEDBACK,
            PurchasePublicFeedback.PURCHASE_PUBLIC_FEEDBACK,
            RewardRecipAssign.REWARD_RECIP_ASSIGN,
            Subscription.SUBSCRIPTION,
            Trade.TRADE,
            Transaction.TRANSACTION,
            UnconfirmedTransaction.UNCONFIRMED_TRANSACTION);
    }
}
