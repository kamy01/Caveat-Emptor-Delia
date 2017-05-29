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
import entities.User;
import repository.BidRepository;
import utils.exceptions.BidException;

@Stateless
@Remote(BidRepository.class)
public class BidRepositoryImpl implements BidRepository {

	private final static String GET_ALL_BIDS_ERROR = " inside getAllIBids() ";
	private final static String GET_BID_FOR_USER_ERROR = " inside getBidForUser() ";
	private final static String INSERT_BID_ERROR = " inside addBid() for parameter: ";
	private final static String UPDATE_BID_ERROR = " inside editBid() for parameter: ";
	private final static String REMOVE_BID_ERROR = " inside editBid() for parameter: ";

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

	@Override
	public Bid getBidForUser(Item item, User user) throws BidException {

		Bid bid = new Bid();

		try {
			Query query = em.createNamedQuery(Bid.GET_BID_FOR_USER);
			query.setParameter("userId", user.getId());
			query.setParameter("itemId", item.getId());
			bid = (Bid) query.getSingleResult();
			return bid;

		} catch (IllegalArgumentException e) {
			throw new BidException(e.getMessage() + GET_BID_FOR_USER_ERROR + "for user: " + user.toString()
					+ ", and item: " + item.toString());
		} catch (Exception e) {
			throw new BidException(e.getMessage() + GET_BID_FOR_USER_ERROR + "for user: " + user.toString()
					+ ", and item: " + item.toString());
		}

	}

	@Override
	public void editBid(Bid bid) throws BidException {

		try {
			em.merge(bid);
		} catch (IllegalArgumentException e) {
			throw new BidException(e.getMessage() + UPDATE_BID_ERROR + bid.toString());
		} catch (TransactionRequiredException e) {
			throw new BidException(e.getMessage() + UPDATE_BID_ERROR + bid.toString());
		} catch (Exception e) {
			throw new BidException(e.getMessage() + UPDATE_BID_ERROR + bid.toString());
		}

	}

	@Override
	public void removeBid(Bid bid) throws BidException {

		try {
			em.remove(em.contains(bid) ? bid : em.merge(bid));

		} catch (IllegalArgumentException e) {
			throw new BidException(e.getMessage() + REMOVE_BID_ERROR + bid.toString());
		} catch (TransactionRequiredException e) {
			throw new BidException(e.getMessage() + REMOVE_BID_ERROR + bid.toString());
		} catch (Exception e) {
			throw new BidException(e.getMessage() + REMOVE_BID_ERROR + bid.toString());
		}
	}

}
