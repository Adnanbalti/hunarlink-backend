package com.hunarlink.modules.notification;

import com.hunarlink.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/my")
    public ApiResponse<List<Notification>> getMyNotifications(@RequestParam UUID userId) {
        List<Notification> notifications = notificationService.getMyNotifications(userId);
        return ApiResponse.ok("Notifications fetched", notifications);
    }

    @GetMapping("/unread-count")
    public ApiResponse<Long> getUnreadCount(@RequestParam UUID userId) {
        long count = notificationService.getUnreadCount(userId);
        return ApiResponse.ok("Unread count", count);
    }

    @PatchMapping("/{id}/read")
    public ApiResponse<Notification> markAsRead(@PathVariable UUID id) {
        Notification notification = notificationService.markAsRead(id);
        return ApiResponse.ok("Notification marked as read", notification);
    }

    @PatchMapping("/mark-all-read")
    public ApiResponse<String> markAllAsRead(@RequestParam UUID userId) {
        notificationService.markAllAsRead(userId);
        return ApiResponse.ok("All notifications marked as read", "Done");
    }
}