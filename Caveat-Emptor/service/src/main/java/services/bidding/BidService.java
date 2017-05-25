package services.bidding;

import java.util.List;

import model.BidDto;
import utils.exceptions.BidException;

public interface BidService {

	List<BidDto> getAllBids() throws BidException;

	void addBid(BidDto currentBid) throws BidException;
	
}
