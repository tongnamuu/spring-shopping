package shopping.wish;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shopping.auth.AuthenticationService;
import shopping.product.FindProduct;

@RestController
@RequestMapping("/api/wishes")
public class WishController {

    private final AddWish addWish;
    private final RemoveWish removeWish;
    private final FindWish findWish;
    private final FindProduct findProduct;
    private final AuthenticationService authenticationService;

    public WishController(AddWish addWish, RemoveWish removeWish, FindWish findWish,
            FindProduct findProduct, AuthenticationService authenticationService) {
        this.addWish = addWish;
        this.removeWish = removeWish;
        this.findWish = findWish;
        this.findProduct = findProduct;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<WishResponse> add(@RequestHeader("Authorization") String authorization,
            @PathVariable Long productId) {
        Long memberId = authenticationService.extractMemberId(authorization);
        Wish wish = addWish.execute(memberId, productId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(WishResponse.of(wish, findProduct.execute(productId)));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> remove(@RequestHeader("Authorization") String authorization,
            @PathVariable Long productId) {
        Long memberId = authenticationService.extractMemberId(authorization);
        removeWish.execute(memberId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<WishResponse>> findAll(
            @RequestHeader("Authorization") String authorization) {
        Long memberId = authenticationService.extractMemberId(authorization);
        List<WishResponse> wishes = findWish.execute(memberId).stream()
                .map(wish -> WishResponse.of(wish, findProduct.execute(wish.getProductId())))
                .toList();
        return ResponseEntity.ok(wishes);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }
}
