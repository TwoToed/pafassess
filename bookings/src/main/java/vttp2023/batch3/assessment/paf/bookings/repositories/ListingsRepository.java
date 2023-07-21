package vttp2023.batch3.assessment.paf.bookings.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.assessment.paf.bookings.models.Constants;

@Repository
public class ListingsRepository {
	@Autowired
	private MongoTemplate template;

	@Autowired
    JdbcTemplate jdbcTemplate;

	//TODO: Task 2

	//db.listings.distinct("address.country")
	public List<String> getCountries(){
		return template.findDistinct(new Query(), Constants.A_ADDRESS_COUNTRY, Constants.C_LISTINGS, String.class);
	}

	
	//TODO: Task 3
	
	// db.listings.find({"address.country" : {$regex: "Australia", $options: "i"}, 
    // accommodates : 2, 
    // price : {$gte:1, $lte:10000}},
    // {_id: 1, name: 1, price : 1, "images.picture_url": 1}
	// ).sort({ price: -1 })


	public List<Document> getResults(String country, Integer numperson, Integer minprice, Integer maxprice){
		Criteria cri = Criteria.where(Constants.A_ADDRESS_COUNTRY)
		.regex(country,"i")
		.and(Constants.A_ACCOMMODATES).is(numperson)
		.and(Constants.A_PRICE).gte(minprice).lte(maxprice);

		Query que = Query.query(cri)
		.with(Sort.by(Direction.DESC,Constants.A_PRICE));
		que.fields()
		.include(Constants.A_ID, Constants.A_NAME, Constants.A_PRICE, Constants.A_IMAGE);
		List<Document> result = template.find(que, Document.class, Constants.C_LISTINGS);
		return result;
	}

	//TODO: Task 4
	// db.listings.find({ 
	// 	_id : "27498126" },
	// 	{_id: 1, description: 1, "address.street":1, "address.suburb":1, "address.country":1, "images.picture_url":1, "price":1, "amenities":1}
	// )
	
	public Document getDetails(String _id){
		Criteria cri = Criteria.where(Constants.A_ID).is(_id);
		Query que = Query.query(cri);
		que.fields()
		.include("_id","description","address.street","address.suburb",
		"address.country","images.picture_url","price","amenities");
		Document result = template.findOne(que, Document.class, Constants.C_LISTINGS);
		return result;
	}

	//TODO: Task 5
	//didnt finish in time
	private String findvancancies = "select vacancy from acc_occupancy where acc_id = ?";
	private String updatevacancies = "update acc_occupancy set vacancy = ?";
	private String insertbooking = "insert into reservations (resv_id, name, email, acc_id, date, duration) values (?, ?, ?, ?, ?, ?)";
	public Integer book(String acc_id, Integer days, String name, String email, String date, String duration){
		Integer vacancies = jdbcTemplate.queryForObject(findvancancies, BeanPropertyRowMapper.newInstance(Integer.class));
		
		if (days > vacancies){
			return 0;
		}
		//imagine this is randomly generated
		String resv_id = "1324fsd32";
		Integer vacanciesleft = vacancies - days;
		jdbcTemplate.update(updatevacancies, vacanciesleft);
		jdbcTemplate.update(insertbooking, resv_id, name, email, acc_id, date, duration);
		return 1;
	}

}
