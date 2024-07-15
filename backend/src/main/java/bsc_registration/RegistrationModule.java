package bsc_registration;

import bsc_registration.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationModule {

    @Autowired
    private MemberRepository repository;

    public void saveFormData(FormData data) {

        System.out.printf("Data received: %s", data.toString());

        FormData testData = FormData.builder().contributionType("ADULT_MEMBER").name("Max").sureName("Mustermann").birthday(LocalDate.now()).gender("MALE")
                .subMemberData(List.of())
                .email("test@gmail.com").phone("1231231").street("TestStraße 10").postalCode(75181).place("Pforzheim")
                .bankName("TestBank").bankOrt("Pforzheim").IBAN("123232312313123312313").BIC("12312312").KontoinhaberVorname("Max").KontoinhaberNachname("Mustermann").build();

        if (isValidData(testData)) {
            repository.saveAll(translateFormData(testData));
        }
    }

    private boolean isValidData(FormData data) {
        return (data.getIBAN().length() != 22 && isValidEmail(data.getEmail()));
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    private List<Member> translateFormData(FormData data) {

        List<Member> members = new ArrayList<>();

        Member mainMember = Member.builder()
                .contributionType(ContributionType.valueOf(data.getContributionType()))
                .name(data.getName())
                .sureName(data.getSureName()).
                birthday(data.getBirthday())
                .gender(Gender.valueOf(data.getGender()))
                .email(data.getEmail()).phone(data.getPhone())
                .street(data.getStreet())
                .postalCode(data.getPostalCode())
                .place(data.getPlace())
                .bankName(data.getBankName())
                .bankOrt(data.getBankOrt())
                .IBAN(data.getBankName())
                .BIC(data.getBIC())
                .KontoinhaberVorname(data.getKontoinhaberVorname())
                .KontoinhaberNachname(data.getKontoinhaberNachname())
                .build();

        members.add(mainMember);

        if (!data.getSubMemberData().isEmpty()) {
            for (SubMemberData subMember : data.getSubMemberData()) {
                Member buildedMember = Member.builder()
                        .name(subMember.getName())
                        .sureName(subMember.getSureName())
                        .birthday(subMember.getBirthday())
                        .gender(subMember.getGender())
                        .email(data.getEmail()).phone(data.getPhone())
                        .street(data.getStreet())
                        .postalCode(data.getPostalCode())
                        .place(data.getPlace())
                        .bankName(data.getBankName())
                        .bankOrt(data.getBankOrt())
                        .IBAN(data.getBankName())
                        .BIC(data.getBIC())
                        .KontoinhaberVorname(data.getKontoinhaberVorname())
                        .KontoinhaberNachname(data.getKontoinhaberNachname())
                        .build();

                members.add(buildedMember);
            }
        }

        return members;
    }

}
