package brs.http

import brs.Account
import brs.Asset
import brs.assetexchange.AssetExchange
import brs.common.AbstractUnitTest
import brs.common.QuickMocker
import brs.common.QuickMocker.MockParam
import brs.services.ParameterService
import brs.util.JSON
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.junit.Before
import org.junit.Test

import javax.servlet.http.HttpServletRequest
import java.util.Arrays

import brs.http.common.Parameters.FIRST_INDEX_PARAMETER
import brs.http.common.Parameters.LAST_INDEX_PARAMETER
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever

class GetAssetsByIssuerTest : AbstractUnitTest() {

    private var t: GetAssetsByIssuer? = null

    private var mockParameterService: ParameterService? = null
    private var mockAssetExchange: AssetExchange? = null

    @Before
    fun setUp() {
        mockParameterService = mock<ParameterService>()
        mockAssetExchange = mock<AssetExchange>()

        t = GetAssetsByIssuer(mockParameterService!!, mockAssetExchange!!)
    }

    @Test
    fun processRequest() {
        val firstIndex = 1
        val lastIndex = 2

        val request = QuickMocker.httpServletRequest(
                MockParam(FIRST_INDEX_PARAMETER, firstIndex),
                MockParam(LAST_INDEX_PARAMETER, lastIndex)
        )


        val mockAccount = mock<Account>()
        whenever(mockAccount.id).doReturn(1L)
        whenever(mockParameterService!!.getAccounts(eq<HttpServletRequest>(request))).doReturn(listOf(mockAccount))

        val mockAssetId: Long = 1

        val mockAsset = mock<Asset>()
        whenever(mockAsset.id).doReturn(1L)
        whenever(mockAsset.id).doReturn(mockAssetId)
        whenever(mockAsset.name).doReturn("name")
        whenever(mockAsset.description).doReturn("description")
        whenever(mockAsset.decimals).doReturn(1.toByte())
        whenever(mockAsset.quantityQNT).doReturn(2L)

        val mockAssetIterator = mockCollection<Asset>(mockAsset)

        whenever(mockAssetExchange!!.getAssetsIssuedBy(eq(mockAccount.id), eq(firstIndex), eq(lastIndex))).doReturn(mockAssetIterator)
        whenever(mockAssetExchange!!.getAssetAccountsCount(eq(mockAssetId))).doReturn(1)
        whenever(mockAssetExchange!!.getTransferCount(eq(mockAssetId))).doReturn(2)
        whenever(mockAssetExchange!!.getTradeCount(eq(mockAssetId))).doReturn(3)

        val result = t!!.processRequest(request) as JsonObject
        assertNotNull(result)

        val assetsForAccountsResult = result.get(ASSETS_RESPONSE) as JsonArray
        assertNotNull(assetsForAccountsResult)
        assertEquals(1, assetsForAccountsResult.size().toLong())

        val assetsForAccount1Result = assetsForAccountsResult.get(0) as JsonArray
        assertNotNull(assetsForAccount1Result)
        assertEquals(1, assetsForAccount1Result.size().toLong())

        val assetResult = assetsForAccount1Result.get(0) as JsonObject
        assertNotNull(assetResult)

        assertEquals(mockAsset.name, JSON.getAsString(assetResult.get(NAME_RESPONSE)))
        assertEquals(mockAsset.description, JSON.getAsString(assetResult.get(DESCRIPTION_RESPONSE)))
        assertEquals(mockAsset.decimals.toLong(), JSON.getAsByte(assetResult.get(DECIMALS_RESPONSE)).toLong())
        assertEquals("" + mockAsset.quantityQNT, JSON.getAsString(assetResult.get(QUANTITY_QNT_RESPONSE)))
        assertEquals("" + mockAsset.id, JSON.getAsString(assetResult.get(ASSET_RESPONSE)))
        assertEquals(1, JSON.getAsInt(assetResult.get(NUMBER_OF_ACCOUNTS_RESPONSE)).toLong())
        assertEquals(2, JSON.getAsInt(assetResult.get(NUMBER_OF_TRANSFERS_RESPONSE)).toLong())
        assertEquals(3, JSON.getAsInt(assetResult.get(NUMBER_OF_TRADES_RESPONSE)).toLong())
    }

}
