package bsc_registration.domain.service;

import bsc_registration.domain.entities.MemberInvite;
import bsc_registration.infrastructure.repository.MemberInviteRepository;
import bsc_registration.webInterface.dto.InviteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InviteService {


    private final MemberInviteRepository memberInviteRepository;

    public void createInvite(final InviteDto inviteDto) {




    }

    public List<MemberInvite> getAllInvites() {

        return memberInviteRepository.findAll();
    }
}
