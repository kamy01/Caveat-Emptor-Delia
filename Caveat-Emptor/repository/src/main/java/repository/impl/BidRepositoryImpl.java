package repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import entities.Bid;
import entities.Item;
import repository.BidRepository;
import utils.exceptions.BidException;
import utils.exceptions.UserException;

@Stateless
@Remote(BidRepository.class)
public class BidRepositoryImpl implements BidRepository {

	private final static String GET_ALL_BIDS_ERROR = " inside getAllIBids() ";
	private final static String INSERT_BID_ERROR = " inside addBid() for parameter: ";

	@PersistenceContext(unitName = "myapp")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Bid> getAllBids() throws BidException {
		List<Bid> bidList = new ArrayList<Bid>();
		try {
			Query query = em.createNamedQuery(Bid.GET_ALL_BIDS);
			bidList = (List<Bid>) query.getResultList();
			return bidList;

		} catch (IllegalArgumentException e) {
			throw new BidException(e.getMessage() + GET_ALL_BIDS_ERROR);
		} catch (Exception e) {
			throw new BidException(e.getMessage() + GET_ALL_BIDS_ERROR);
		}
	}

	@Override
	public List<Bid> getBidsForItem(Item item) {
		return null;
	}

	@Override
	public void addBid(Bid bid) throws BidException {

		try {
			em.persist(bid);
			em.flush();

		} catch (IllegalArgumentException e) {
			throw new BidException(e.getMessage() + INSERT_BID_ERROR + bid.toString());
		} catch (EntityExistsException e) {
			throw new BidException(e.getMessage() + INSERT_BID_ERROR + bid.toString());
		} catch (TransactionRequiredException e) {
			throw new BidException(e.getMessage() + INSERT_BID_ERROR + bid.toString());
		} catch (Exception e) {
			throw new BidException(e.getMessage() + INSERT_BID_ERROR + bid.toString());
		}
		
	}

}
