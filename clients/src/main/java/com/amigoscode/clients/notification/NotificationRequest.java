//package com.amigoscode.clients.notification;
//
//public record NotificationRequest(
//        Integer toCustomerId,
//        String toCustomerEmail,
//        String message
//) {
//}

package com.amigoscode.clients.notification;

public record NotificationRequest(
        Integer toCustomerId,
        String toCustomerName,
        String message
) {
}