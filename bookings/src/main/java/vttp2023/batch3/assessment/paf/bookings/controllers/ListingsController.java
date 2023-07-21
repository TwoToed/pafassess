package vttp2023.batch3.assessment.paf.bookings.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vttp2023.batch3.assessment.paf.bookings.models.Accommodation;
import vttp2023.batch3.assessment.paf.bookings.models.Details;
import vttp2023.batch3.assessment.paf.bookings.models.Form;
import vttp2023.batch3.assessment.paf.bookings.services.ListingsService;

@Controller
@RequestMapping
public class ListingsController {
    @Autowired
    ListingsService ser;

    //TODO: Task 2
    @GetMapping("/")
    public ModelAndView landingpage(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("landing");
        return mav;
    }

    @PostMapping("/search")
    public ModelAndView search(@RequestBody MultiValueMap <String, String> form,
    @Valid Form validform, BindingResult result){
        ModelAndView mav = new ModelAndView();
        System.out.println(ser.getCountries());
        mav.addObject("listofcountries", ser.getCountries());
        if(result.hasErrors()){
            System.out.println("fail");
            return landingpage();
        }
        String country = form.getFirst("country");
        Integer numperson = Integer.parseInt(form.getFirst("numperson"));
        Integer minprice = Integer.parseInt(form.getFirst("minprice"));
        Integer maxprice = Integer.parseInt(form.getFirst("maxprice"));

        System.out.println(ser.getCountries());
        
        return searchResults(country, numperson, minprice, maxprice);
    }
    
    //TODO: Task 3
    @GetMapping("/search")
    public ModelAndView searchResults(@RequestParam String country, Integer numperson,
    Integer minprice, Integer maxprice){
        ModelAndView mav = new ModelAndView();
        List<Accommodation> accom = ser.getResults(country, numperson, minprice, maxprice);
        if (accom.isEmpty()){
            mav.addObject("nothing", "nothing found");
        };
        mav.setViewName("listings");
        mav.addObject("accom", accom);
        
        System.out.printf("\n\n>>> %s\n\\n", accom);
        
        return mav;
    }

    //TODO: Task 4
    @GetMapping("/search/{_id}")
    public ModelAndView getDetails(@PathVariable String _id) {
        ModelAndView mav = new ModelAndView();
        Details details = ser.getDetails(_id);
        System.out.println(details.toString());
        mav.setViewName("details");
        mav.addObject("id", _id);
        mav.addObject("details", details);
        return mav;
    }

    //TODO: Task 5


}

