package services.bidding.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import entities.Bid;
import model.BidDto;
import repository.BidRepository;
import services.bidding.BidService;
import services.util.EntityDtoMapper;
import utils.exceptions.BidException;

@Stateless
@Remote(BidService.class)
public class BidServiceImpl implements BidService {
	
	@EJB
	BidRepository repository;

	@Override
	public List<BidDto> getAllBids() throws BidException {
		List<Bid> entities = repository.getAllBids();
		return EntityDtoMapper.getBidsFromEntity(entities);
	}

	@Override
	public void addBid(BidDto currentBid) throws BidException {

		repository.addBid(EntityDtoMapper.getBidFromDto(currentBid));
		
	}

}
