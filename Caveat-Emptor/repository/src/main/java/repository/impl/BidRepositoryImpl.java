package repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Bid;
import entities.Item;
import repository.BidRepository;
import utils.exceptions.BidException;

@Stateless
@Remote(BidRepository.class)
public class BidRepositoryImpl implements BidRepository {

	private final static String GET_ALL_BIDS_ERROR = " inside getAllIBids() ";

	@PersistenceContext(unitName = "myapp")
	EntityManager em;

	@Override
	public List<Bid> getAllBids() throws BidException {
		List<Bid> bidList = new ArrayList<Bid>();
		try {
			Query query = em.createNamedQuery(Bid.GET_ALL_BIDS);
			bidList = (List<Bid>) query.getResultList();
			return bidList;

		} catch (IllegalArgumentException e) {
			throw new BidException(e + GET_ALL_BIDS_ERROR);
		} catch (Exception e) {
			throw new BidException(e + GET_ALL_BIDS_ERROR);
		}
	}

	@Override
	public List<Bid> getBidsForItem(Item item) {
		// TODO Auto-generated method stub
		return null;
	}

}
