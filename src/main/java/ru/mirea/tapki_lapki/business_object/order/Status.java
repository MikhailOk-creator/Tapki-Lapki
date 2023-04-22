package ru.mirea.tapki_lapki.business_object.order;

/**
 * Status entity. Help to create order and cart
 * @see Order
 */
public enum Status {
    CART, ///< Indicates that the storage is in the bucket state
    NEW, ///< Indicates that the storage is in the new state
    CONFIRMED, ///< Indicates that the storage is in the confirmed state
    COLLECT, ///< Indicates that the storage is in the collect state
    READY, ///< Indicates that the storage is in the ready state
    TRANSFERRED_TO_DELIVERY, ///< Indicates that the storage is in the transferred to delivery state
    COMPLETED; ///< Indicates that the storage is in the completed state
}
