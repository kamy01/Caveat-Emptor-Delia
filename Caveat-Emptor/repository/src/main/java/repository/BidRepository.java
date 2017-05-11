package repository;

import java.util.List;

import entities.Bid;
import entities.Item;
import utils.exceptions.BidException;

public interface BidRepository {

	public List<Bid> getAllBids() throws BidException;
	public List<Bid> getBidsForItem(Item item);

}
