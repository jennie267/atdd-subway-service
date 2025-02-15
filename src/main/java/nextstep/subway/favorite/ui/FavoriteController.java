package nextstep.subway.favorite.ui;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nextstep.subway.auth.domain.AuthenticationPrincipal;
import nextstep.subway.auth.domain.LoginMember;
import nextstep.subway.favorite.application.FavoriteService;
import nextstep.subway.favorite.dto.FavoriteRequest;
import nextstep.subway.favorite.dto.FavoriteResponse;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity createFavorite(@AuthenticationPrincipal LoginMember loginMember, @RequestBody FavoriteRequest favoriteRequest) {
        FavoriteResponse favorite = favoriteService.create(loginMember.getId(), favoriteRequest);
        return ResponseEntity.ok().body(favorite);
    }

    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> findFavorites(@AuthenticationPrincipal LoginMember loginMember) {
        return ResponseEntity.ok(favoriteService.findResponse(loginMember.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFavorite(@PathVariable Long id, @AuthenticationPrincipal LoginMember loginMember) {
        favoriteService.delete(id, loginMember);
        return ResponseEntity.noContent().build();
    }
}
