package mega.portal.paymentdemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RazorpayController {

  private final RazorpayService razorpayService;

  public RazorpayController(RazorpayService razorpayService) {
    this.razorpayService = razorpayService;
  }

  @GetMapping("/payment")
  public String showPaymentForm() {
    return "paymentForm";
  }

  @PostMapping("/createOrder")
  @ResponseBody
  public ResponseEntity<?> createOrder(
      @RequestParam Integer amount
  ) {
    try {
      TransactionDetails transaction = razorpayService.createOrder(amount);
      return ResponseEntity.ok(transaction);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
