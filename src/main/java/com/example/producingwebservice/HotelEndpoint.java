package com.example.producingwebservice;


import com.baeldung.springsoap.gen.GetHotelRequest;
import com.baeldung.springsoap.gen.GetHotelResponse;
import com.baeldung.springsoap.gen.GetReservationRequest;
import com.baeldung.springsoap.gen.GetReservationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class HotelEndpoint {
    private static final String NAMESPACE_URI = "http://www.baeldung.com/springsoap/gen";

    private HotelRepository hotelRepository;

    @Autowired
    public HotelEndpoint(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getHotelRequest")
    @ResponsePayload
    public GetHotelResponse getCountry(@RequestPayload GetHotelRequest request) {
GetHotelResponse response = new GetHotelResponse();
response.setList(hotelRepository.findChambre(request));
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getReservationRequest")
    @ResponsePayload
    public GetReservationResponse getReserve(@RequestPayload GetReservationRequest request) {

        return hotelRepository.reserver(request);
    }

}