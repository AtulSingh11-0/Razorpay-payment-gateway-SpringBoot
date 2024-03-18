package mega.portal.paymentdemo;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {

  @Value("${RAZORPAY_KEY_ID}")
  private String keyId;

  @Value("${RAZORPAY_SECRET_KEY}")
  private String secretKey;

  private final String currency = "INR";

  public TransactionDetails createOrder(Integer amount) {
    try {
      JSONObject options = new JSONObject();
      options.put("amount", (amount * 100) ); // for eg: amount = 500 INR i.e. 500*100
      options.put("currency", currency);
      options.put("receipt", "order_" + System.currentTimeMillis()); 
      options.put("payment_capture", 1);

      RazorpayClient razorpay = new RazorpayClient(keyId, secretKey);
      Order order = razorpay.orders.create(options);
      String orderId = order.get("id");
      return new TransactionDetails(orderId, amount, currency);

    } catch (RazorpayException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }
}
