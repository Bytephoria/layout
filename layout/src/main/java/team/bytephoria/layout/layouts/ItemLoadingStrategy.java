package team.bytephoria.layout.layouts;

public enum ItemLoadingStrategy {

    /**
     * Items are initialized and populated into the inventory
     * immediately when the layout is constructed.
     * <p>
     * This guarantees that all items are ready before the inventory is opened,
     * but may slightly increase initialization time.
     */
    INSTANT,

    /**
     * Items are initialized and populated lazily at a later time,
     * typically when the inventory is first accessed or opened.
     * <p>
     * This improves performance for layouts that may never be displayed,
     * or when item creation is resource-intensive.
     */
    LAZY;
}
