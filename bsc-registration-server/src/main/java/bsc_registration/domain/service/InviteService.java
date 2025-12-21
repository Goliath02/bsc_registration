package bsc_registration.domain.service;

import bsc_registration.domain.entities.MemberInvite;
import bsc_registration.domain.entities.SignUpKey;
import bsc_registration.features.invite.repository.MemberInviteRepository;
import bsc_registration.webInterface.dto.AuthorityType;
import bsc_registration.webInterface.dto.InviteDto;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InviteService {


    private final MemberInviteRepository memberInviteRepository;
    private final AuthService authService;
    private final  EmailService emailService;

    @Transactional(rollbackOn = Exception.class)
    public void createInvite(final InviteDto inviteDto) throws MessagingException, IOException {

        final SignUpKey signUpKey = authService.createSignUpKey(AuthorityType.COURSE_OWNER);

        emailService.sendTrainerInviteMail(inviteDto.getEmail(), signUpKey.getKey());

        authService.saveSignUpKey(signUpKey);
    }

    public List<MemberInvite> getAllInvites() {

        return memberInviteRepository.findAll();
    }

    public void deleteInvite(long inviteId) {
        memberInviteRepository.deleteById(inviteId);
        authService.deleteMemberWithKey(inviteId);
    }
}
