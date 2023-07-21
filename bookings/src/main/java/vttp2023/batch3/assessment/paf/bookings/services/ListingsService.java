package vttp2023.batch3.assessment.paf.bookings.services;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import vttp2023.batch3.assessment.paf.bookings.models.Accommodation;
import vttp2023.batch3.assessment.paf.bookings.models.Constants;
import vttp2023.batch3.assessment.paf.bookings.models.Details;
import vttp2023.batch3.assessment.paf.bookings.repositories.ListingsRepository;

@Service
public class ListingsService {

	@Autowired
	ListingsRepository repo;
	
	//TODO: Task 2
	public List<String> getCountries(){
		return repo.getCountries();
	}
	//TODO: Task 3
	public List<Accommodation> getResults(String country, Integer numperson, Integer minprice, Integer maxprice){ 
		return repo.getResults(country, numperson, minprice, maxprice).stream()
			.map(d -> {
				return new Accommodation(
					d.getString(Constants.A_ID),
					d.getString(Constants.A_NAME),
					d.getInteger(Constants.A_PRICE),
					d.get("images", Document.class).getString("picture_url"));
			}).toList();
	}

	//TODO: Task 4
	public Details getDetails(String _id){
		Document doc = repo.getDetails(_id);
		Details details = new Details(
			doc.getString("_id"), 
			doc.getString("description"),
			doc.get("address", Document.class).getString("street")
			+ "\n"
			+ doc.get("address", Document.class).getString("suburb")
			+ "\n"
			+ doc.get("address", Document.class).getString("country"),
			doc.get("images", Document.class).getString("picture_url"),
			doc.getInteger("price"),
			doc.get("amenities").toString());
			return details;
	}

	//TODO: Task 5


}
