    package com.example.Member.service;

    import com.example.Member.dto.MemberFormDto;
    import com.example.Member.entity.Member;
    import com.example.Member.repository.MemberRepository;
    import jakarta.transaction.Transactional;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;

    import java.util.Optional;

    @Service
    @Transactional
    @RequiredArgsConstructor
    public class MemberService implements UserDetailsService {

        private final MemberRepository memberRepository;

        //회원 등록
        public Member saveMember(Member member) {
            validateDuplicateMember(member);
            return memberRepository.save(member);
        }

        //유효성 검사
        private void validateDuplicateMember(Member member) {
            Member findMember = memberRepository.findByMemId(member.getMemId());
            if (findMember != null) {
                throw new IllegalStateException("이미 가입된 회원입니다.");
            //이미 가입된 회원인 경우 예외처리
            }
        }

        //로그인 처리
        @Override
        public UserDetails loadUserByUsername(String memId) throws UsernameNotFoundException {
            Member member = memberRepository.findByMemId(memId);

            if(member == null) {
                throw new UsernameNotFoundException(memId);
            }

            return User.builder()                           // UserDetail을 구현하고 있는 User 객체를 반환해줌. User 객체를 생성하기 위해 생성자로 회원의 이메일, 비밀번호, role을 파라미터로 넘겨줌.
                    .username(member.getMemId())
                    .password(member.getPassword())
                    .roles(member.getRole().toString())
                    .build();
        }


        @Transactional
        // 회원 수정
        public Member updateMember(Long id, MemberFormDto memberFormDto) {
            Member member = memberRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException("해당 회원을 찾을 수 없습니다."));

            // 수정할 필드 업데이트
            member.setName(memberFormDto.getName());
            member.setPhonenumber(memberFormDto.getPhonenumber());
            member.setAddress(memberFormDto.getAddress());

            return memberRepository.save(member);
        }

        @Transactional
        // 회원 삭제
        public void deleteMember(Long id) {
            memberRepository.deleteById(id);
        }


        public Optional<Member> findById(Long id) {
            return memberRepository.findById(id);
        }

        // 아이디가 존재하는지 확인
        public boolean isUsernameExists(String memId) {
            return memberRepository.existsByMemId(memId);
        }

        public Member findMemberById(String memId) {
            return memberRepository.findByMemId(memId);

        }

    }