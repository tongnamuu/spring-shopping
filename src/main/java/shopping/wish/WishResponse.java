package shopping.wish;

import shopping.product.Product;

public record WishResponse(Long id, Long productId, String name, long price, String imageUrl) {

    public static WishResponse of(Wish wish, Product product) {
        return new WishResponse(wish.getId(), product.getId(), product.getName(),
                product.getPrice(), product.getImageUrl());
    }
}
