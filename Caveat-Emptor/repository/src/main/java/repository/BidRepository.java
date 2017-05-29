package repository;

import java.util.List;

import entities.Bid;
import entities.Item;
import entities.User;
import utils.exceptions.BidException;

public interface BidRepository {

	public List<Bid> getAllBids() throws BidException;
	public List<Bid> getBidsForItem(Item item);
	public void addBid(Bid bid) throws BidException;
	public Bid getBidForUser(Item item, User user) throws BidException;
	public void editBid(Bid bid) throws BidException;
	public void removeBid(Bid bid) throws BidException;

}
