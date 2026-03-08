package shopping.member;

import shopping.wish.Wish;
import shopping.wish.WishRepository;

public class Member {

    private Long id;
    private String email;
    private String password;

    public Member(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Member(String email, String password) {
        this(null, email, password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Member withId(Long id) {
        return new Member(id, this.email, this.password);
    }

    public void login(String rawPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(rawPassword, this.password)) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
        }
    }

    public Wish wish(Long productId, WishRepository wishRepository) {
        if (wishRepository.existsByMemberIdAndProductId(this.id, productId)) {
            throw new IllegalArgumentException("이미 위시리스트에 추가된 상품입니다.");
        }
        return wishRepository.save(new Wish(this.id, productId));
    }
}
