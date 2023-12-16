package com.example.Member.entity;


import com.example.Member.constant.Role;
import com.example.Member.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
@Entity
@Table(name="member")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;
    private String memId;   // 로그인 아이디
    private String name;    //이름
    @Column(unique = true)
    private String email;   //이메일
    private String phonenumber; //핸드폰번호
    private String password; //비밀번호
    private String address;  //주소

    @Enumerated(EnumType.STRING)    //사용자 권한
    private Role role;

    private LocalDateTime createdAt; //가입시간

//    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL) //멤버가 삭제되면 글도 삭제되게
//    private List<Board> boards = new ArrayList<>();

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder, Role role){
        Member member = new Member();
        member.setMemId(memberFormDto.getMemId());
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setPhonenumber(memberFormDto.getPhonenumber());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);  // 여기서 전달받은 role을 사용하도록 변경
        return member;
    }


}