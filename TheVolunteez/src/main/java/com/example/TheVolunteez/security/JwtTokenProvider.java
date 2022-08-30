package com.example.TheVolunteez.security;

import com.example.TheVolunteez.repository.MemberRepository;
import com.example.TheVolunteez.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    // secret key 설정
    private final UserDetailsService userDetailsService;

    private String secretKey = "thevolunteez";
    //
    private long tokenValidTime = 30 * 60 * 1000L;

    // PostConstruct 는 초기화 즉 값을 로딩 시점에 미리 넣어줌
    // secretKey 를 Base64로 인코딩함
    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT 페이로드에 저장되는 정보단위
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 발행된 시간
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // 만료 시간(현재 시간을 기준으로 만료할 시간을 더함)
                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 signature 에 들어갈 secret 값 세팅
                .compact(); // 만들기
    }

    //JWT 토큰에서 인증 정보를 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    // 토큰에서 회원 정보 추출
    private String getUserPk(String token) {
        return Jwts.parser() // 토큰 분석
                .setSigningKey(secretKey) // 토큰을 만들 때 사용했던 키
                .parseClaimsJws(token) // 토큰 해석
                .getBody() // body 를 가져옴
                .getSubject(); // body 에 있는 subject 를 가져옴
    }

    // Request 의 Header 에서 token 값을 가져옴 " X-AUTH-TOKEN : "TOKEN 값"
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성(토큰의 형식이 맞는지) + 만료되는 날짜 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
