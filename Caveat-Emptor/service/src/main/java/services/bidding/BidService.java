package services.bidding;

import java.util.List;

import model.BidDto;
import model.ItemDto;
import model.UserDto;
import utils.exceptions.BidException;

public interface BidService {

	List<BidDto> getAllBids() throws BidException;

	void addBid(BidDto currentBid) throws BidException;

	BidDto getBidForUser(ItemDto item, UserDto user) throws BidException;

	void editBid(BidDto bid) throws BidException;

	void removeBid(BidDto bid) throws BidException;
	
}
