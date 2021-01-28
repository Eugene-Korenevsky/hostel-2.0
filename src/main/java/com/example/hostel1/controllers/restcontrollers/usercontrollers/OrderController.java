package com.example.hostel1.controllers.restcontrollers.usercontrollers;

import com.example.hostel1.AppProperties;
import com.example.hostel1.entities.Order;
import com.example.hostel1.entities.Room;
import com.example.hostel1.entities.User;
import com.example.hostel1.helpers.orderhelpers.OrderCorrectDate;
import com.example.hostel1.helpers.pricehelpers.TotalPrice;
import com.example.hostel1.helpers.roomhelpers.datehelpers.TimestampMaker;
import com.example.hostel1.repositories.OrderRepository;
import com.example.hostel1.repositories.RoomRepository;
import com.example.hostel1.repositories.UserRepository;
import com.example.hostel1.servicies.OrderService;
import com.example.hostel1.servicies.ReserveService;
import com.example.hostel1.servicies.RoomService;
import com.example.hostel1.servicies.UserService;
import com.example.hostel1.servicies.exceptions.ResourceNotFoundException;
import com.example.hostel1.servicies.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController("restUserOrderController")
@RequestMapping("orders")
public class OrderController {
    @Autowired
    private TimestampMaker timestampMaker;
    @Autowired
    private OrderCorrectDate orderCorrectDate;
    @Autowired
    private TotalPrice totalPrice;
    //  @Autowired
    // private RoomRepository roomRepository;
    //@Autowired
    //private UserRepository userRepository;
    //@Autowired
    //private OrderRepository orderRepository;
    @Autowired
    private ReserveService reserveService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AppProperties appProperties;


    @PostMapping(value = {"isFree"}, produces = {MimeTypeUtils.TEXT_PLAIN_VALUE})
    public ResponseEntity<?> makePrice(@RequestParam("dateIn") String dateIn,
                                       @RequestParam("dateOut") String dateOut,
                                       @RequestParam("roomId") long id) {
        StringBuilder result = new StringBuilder();
        Timestamp inDate = timestampMaker.getTimestamp(dateIn, appProperties.getArriveTime());
        Timestamp outDate = timestampMaker.getTimestamp(dateOut, appProperties.getArriveTime());
        try {
            Room room = roomService.findById(id);
            if (orderCorrectDate.isCorrectDates(dateIn, appProperties.getArriveTime(),
                    dateOut, appProperties.getArriveTime())) {
                try {
                    if (reserveService.isFreeInDates(inDate, outDate, id)) {
                        String price = totalPrice.getTotalPrice(room.getPrice(), inDate, outDate);
                        result.append(price);
                    } else {
                        result.append("notFree");
                    }
                } catch (ResourceNotFoundException e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                } catch (ValidationException e) {
                    return new ResponseEntity<>(e.getValidationError(), HttpStatus.BAD_REQUEST);
                }
            } else {
                result.append("wrongDates");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getValidationError(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        //roomRepository.findById(id).orElse(new Room());

    }

    @PostMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> makeOrder(@RequestParam("dateIn") String dateIn,
                                       @RequestParam("dateOut") String dateOut,
                                       @RequestParam("roomId") long id) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        try {
            User user1 = userService.findByEmail(user.getUsername());
            if (user1 != null) {
                //userRepository.findByEmail(user.getUsername());
                Timestamp inDate = timestampMaker.getTimestamp(dateIn, appProperties.getArriveTime());
                Timestamp outDate = timestampMaker.getTimestamp(dateOut, appProperties.getArriveTime());
                try {
                    Room room = roomService.findById(id);
                    //roomRepository.findById(id).orElse(new Room());
                    // long a = 1;
                    // User user1 = userRepository.findById(a).orElse(new User());
                    double priceDouble = totalPrice.getTotalPrice(room.getPrice(), dateIn,
                            appProperties.getArriveTime(), dateOut, appProperties.getArriveTime());
                    Order order = new Order();
                    order.setTotalPrice(priceDouble);
                    order.setDateIn(inDate);
                    order.setDateOut(outDate);
                    order.setRoom(room);
                    order.setUser(user1);
                    order = orderService.create(order);
                    return new ResponseEntity<>(order, HttpStatus.OK);
                    //orderRepository.save(order);
                } catch (ResourceNotFoundException e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                }
            } else return new ResponseEntity<>("you should login", HttpStatus.FORBIDDEN);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getValidationError(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = {"{id}"})
    public ResponseEntity<?> deleteOrder(@PathVariable("id") long id) {
        try {
            orderService.deleteById(id);
            //orderRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getValidationError(), HttpStatus.BAD_REQUEST);
        }
    }
}
