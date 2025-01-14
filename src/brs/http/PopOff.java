package brs.http;

import brs.Block;
import brs.Blockchain;
import brs.BlockchainProcessor;
import brs.props.PropertyService;
import brs.props.Props;
import brs.services.BlockService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static brs.http.JSONResponses.ERROR_NOT_ALLOWED;
import static brs.http.common.Parameters.API_KEY_PARAMETER;
import static brs.http.common.Parameters.HEIGHT_PARAMETER;
import static brs.http.common.Parameters.NUM_BLOCKS_PARAMETER;
import static brs.http.common.ResultFields.BLOCKS_RESPONSE;
import static brs.http.common.ResultFields.ERROR_RESPONSE;

final class PopOff extends APIServlet.JsonRequestHandler {

  private final BlockchainProcessor blockchainProcessor;
  private final Blockchain blockchain;
  private final BlockService blockService;
  private final List<String> apiAdminKeyList;

  PopOff(BlockchainProcessor blockchainProcessor, Blockchain blockchain, BlockService blockService, PropertyService propertyService) {
    super(new APITag[] {APITag.ADMIN}, NUM_BLOCKS_PARAMETER, HEIGHT_PARAMETER, API_KEY_PARAMETER);
    this.blockchainProcessor = blockchainProcessor;
    this.blockchain = blockchain;
    this.blockService = blockService;
    
    apiAdminKeyList = propertyService.getStringList(Props.API_ADMIN_KEY_LIST);
  }

  @Override
  JsonElement processRequest(HttpServletRequest req) {
    
    String apiKey = req.getParameter(API_KEY_PARAMETER);
    if(!apiAdminKeyList.contains(apiKey)) {
      return ERROR_NOT_ALLOWED;
    }

    JsonObject response = new JsonObject();
    int numBlocks = 0;
    try {
      numBlocks = Integer.parseInt(req.getParameter(NUM_BLOCKS_PARAMETER));
    } catch (NumberFormatException ignored) {}
    int height = 0;
    try {
      height = Integer.parseInt(req.getParameter(HEIGHT_PARAMETER));
    } catch (NumberFormatException ignored) {}

    List<? extends Block> blocks;
    JsonArray blocksJSON = new JsonArray();
    if (numBlocks > 0) {
      blocks = blockchainProcessor.popOffTo(blockchain.getHeight() - numBlocks);
    }
    else if (height > 0) {
      blocks = blockchainProcessor.popOffTo(height);
    }
    else {
      response.addProperty(ERROR_RESPONSE, "invalid numBlocks or height");
      return response;
    }
    for (Block block : blocks) {
      blocksJSON.add(JSONData.block(block, true, blockchain.getHeight(), blockService.getBlockReward(block), blockService.getScoopNum(block)));
    }
    response.add(BLOCKS_RESPONSE, blocksJSON);
    return response;
  }

  @Override
  final boolean requirePost() {
    return true;
  }

}
