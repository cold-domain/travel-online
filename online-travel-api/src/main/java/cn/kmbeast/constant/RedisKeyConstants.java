package cn.kmbeast.constant;

public final class RedisKeyConstants {

    private RedisKeyConstants() {
    }

    public static final String LOGIN_TOKEN_PREFIX = "travel:auth:token:user:";
    public static final String TOKEN_BLACKLIST_PREFIX = "travel:auth:blacklist:";
    public static final String VIEWS_STATIC_CONTROLS_CACHE = "travel:cache:views:staticControls";
    public static final String SELECTED_SCENIC_CACHE = "travel:cache:scenic:selected";
    public static final String NOTICE_QUERY_CACHE_PREFIX = "travel:cache:notice:query:";
    public static final String SCENIC_CATEGORY_QUERY_CACHE_PREFIX = "travel:cache:scenicCategory:query:";
    public static final String HOTEL_ROOM_BED_QUERY_CACHE_PREFIX = "travel:cache:hotelRoomBed:query:";
    public static final String SCENIC_SAVE_USERS_PREFIX = "travel:scenic:save:users:";
    public static final String SCENIC_VIEW_USERS_PREFIX = "travel:scenic:view:users:";
    public static final String SCENIC_HOT_RANK = "travel:scenic:hot:rank";
    public static final String SCENIC_TICKET_STOCK_PREFIX = "travel:stock:ticket:";
    public static final String SCENIC_TICKET_ORDER_LOCK_PREFIX = "travel:lock:ticketOrder:";
    public static final String HOTEL_ROOM_ORDER_LOCK_PREFIX = "travel:lock:hotelRoomOrder:";
    public static final String HOTEL_ROOM_BOOKED_PREFIX = "travel:hotel:room:booked:";
    public static final String RATE_LIMIT_PREFIX = "travel:rateLimit:";

    public static String userTokenKey(Integer userId) {
        return LOGIN_TOKEN_PREFIX + userId;
    }

    public static String tokenBlacklistKey(String tokenId) {
        return TOKEN_BLACKLIST_PREFIX + tokenId;
    }

    public static String scenicSaveUsersKey(Integer scenicId) {
        return SCENIC_SAVE_USERS_PREFIX + scenicId;
    }

    public static String scenicViewUsersKey(Integer scenicId) {
        return SCENIC_VIEW_USERS_PREFIX + scenicId;
    }

    public static String scenicTicketStockKey(Integer ticketId) {
        return SCENIC_TICKET_STOCK_PREFIX + ticketId;
    }

    public static String scenicTicketOrderLockKey(Integer ticketId) {
        return SCENIC_TICKET_ORDER_LOCK_PREFIX + ticketId;
    }

    public static String hotelRoomOrderLockKey(Integer roomId) {
        return HOTEL_ROOM_ORDER_LOCK_PREFIX + roomId;
    }

    public static String hotelRoomBookedKey(Integer roomId) {
        return HOTEL_ROOM_BOOKED_PREFIX + roomId;
    }
}
