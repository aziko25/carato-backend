package carato.carato_backend.Services.Payments;

import carato.carato_backend.Models.Orders.Orders;
import carato.carato_backend.Repositories.Orders.OrdersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClickService {

    private final OrdersRepository ordersRepository;

    @Value("${clickServiceId}")
    private Integer clickServiceId;

    @Value("${clickMerchantId}")
    private Integer clickMerchantId;

    public String createTransaction(Orders order, String returnUrl) {

        return "https://my.click.uz/services/pay?service_id=" + clickServiceId + "&merchant_id=" + clickMerchantId +
                "&return_url=" + returnUrl +
                "&amount=" + order.getTotalSum() +
                "&transaction_param=" + order.getOrderPaymentId();
    }

    public Map<String, String> prepareOrder(Map<String, String> body) {

        String clickTransId = body.get("click_trans_id");
        String merchantTransId = body.get("merchant_trans_id");

        Map<String, String> response = new HashMap<>();

        response.put("click_trans_id", clickTransId);
        response.put("merchant_trans_id", merchantTransId);
        response.put("merchant_prepare_id", merchantTransId);
        response.put("error", "0");
        response.put("error_note", "Success");

        return response;
    }

    public Map<String, Object> completeOrder(Map<String, String> body) {

        String clickTransId = body.get("click_trans_id");
        String merchantTransId = body.get("merchant_trans_id");
        String error = body.get("error");

        Map<String, Object> response = new HashMap<>();

        response.put("click_trans_id", clickTransId);
        response.put("merchant_trans_id", merchantTransId);
        Integer merchantConfirmId = error.equals("0") ? 1 : null;
        response.put("merchant_confirm_id", merchantConfirmId);

        String orderId = String.valueOf(body.get("merchant_trans_id"));

        Orders order = ordersRepository.findByOrderPaymentId(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order Not Found"));

        order.setIsPaymentDone(true);

        ordersRepository.save(order);

        response.put("error", "0");
        response.put("error_note", "Success");

        return response;
    }
}