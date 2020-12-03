package com.example.hostel1.controllers.restcontrollers.admincontrollers;

import com.example.hostel1.entities.Order;
import com.example.hostel1.entities.Reserve;
import com.example.hostel1.helpers.orderhelpers.IsRoomFreeInDates;
import com.example.hostel1.helpers.roomhelpers.datehelpers.TimestampMaker;
import com.example.hostel1.repositories.OrderRepository;
import com.example.hostel1.repositories.ReserveRepository;
import com.example.hostel1.servicies.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping(path = "admin/reserve", produces = "application/json")
public class AdminRestReserveController {
    @Autowired
    private IsRoomFreeInDates isRoomFreeInDates;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ReserveRepository reserveRepository;
    @Autowired
    private TimestampMaker timestampMaker;
    @Autowired
    private ReserveService reserveService;

    @PostMapping(value = {"isFree"}, produces = {MimeTypeUtils.TEXT_PLAIN_VALUE})
    public ResponseEntity<StringBuilder> makePrice(@RequestParam("dateIn") String dateIn,
                                                   @RequestParam("dateOut") String dateOut,
                                                   @RequestParam("roomId") long id,
                                                   @RequestParam("orderId") long orderId) {
        try {
            StringBuilder result = new StringBuilder();
            StringBuilder dateInBuild = new StringBuilder();
            StringBuilder dateOutBuild = new StringBuilder();
            dateInBuild.append(dateIn.substring(0, 10)).append(" ").append(dateIn.substring(11, 19));
            dateOutBuild.append(dateOut.substring(0, 10)).append(" ").append(dateOut.substring(11, 19));
            Timestamp inDate = Timestamp.valueOf(dateInBuild.toString());
            Timestamp outDate = Timestamp.valueOf(dateOutBuild.toString());
            if (isRoomFreeInDates.isRoomFree(id, inDate, outDate)) {
                Order order = orderRepository.findById(orderId).orElse(new Order());
                double price = order.getTotalPrice();
                result.append(price);
            } else {
                result.append("notFree");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("error");
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public Reserve createReserve(//@RequestBody Order order,
                                  RequestEntity<Order> orderRequestEntity) {
        HttpHeaders httpHeaders = orderRequestEntity.getHeaders();
        System.out.println(httpHeaders.getLocation());
        System.out.println(httpHeaders.getAcceptLanguageAsLocales());
        Reserve reserve = reserveService.createReserve(orderRequestEntity.getBody());
        return reserve;
    }
}
