package bsc_registration.features.invite.controller;

import bsc_registration.features.invite.entity.MemberInvite;
import bsc_registration.features.registration.service.InviteService;
import bsc_registration.webInterface.dto.InviteDto;
import bsc_registration.webInterface.dto.InviteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invites")
@RequiredArgsConstructor
@Slf4j
public class InviteController {

    private final InviteService inviteService;

    @GetMapping("/history")
    public List<MemberInvite> getAllInvites() {
        return inviteService.getAllInvites();
    }

    ;

    @PostMapping("/member")
    public ResponseEntity<InviteResponse> inviteMember(@RequestBody final InviteDto inviteDto) {

        try {
            inviteService.createInvite(inviteDto);
        } catch (MailSendException e) {
            return ResponseEntity.badRequest().body(InviteResponse.MAIL_INVALID);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(InviteResponse.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(InviteResponse.SUCCESS);
    }

    @DeleteMapping
    public void invalidateInvite(@RequestParam final long inviteId) {
        inviteService.deleteInvite(inviteId);
    }


}
