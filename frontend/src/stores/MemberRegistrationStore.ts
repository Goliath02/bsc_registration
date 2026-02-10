import { defineStore } from "pinia";
import {
  AgeType,
  RegistrationData,
  RegistrationType,
} from "@/stores/Registration";
import * as dateUtil from "@/utils/dateUtil";
import { ImageFile } from "@/stores/Registration";


export const MemberRegistrationStore = defineStore("memberRegistrationStore", {
  state: () => ({
    registrationData: {
      verificationFiles: [],
      mainData: {
        type: "",
        reason: "",
        name: "",
        surename: "",
        birthday: null,
        gender: "",
        email: "",
        phone: "",
        street: "",
      },
      financialData: {
        iban: "",
        nameOfBankOwner: "",
        sureNameBankOwner: "",
      },
      dataApproval: {
        dataProtection: false,
        dataStatute: false,
        dataCorrectness: false,
      },
    } as RegistrationData,
    studentVerification: [] as ImageFile[],
  }),

  getters: {
    price(state): number {
      const main = state.registrationData.mainData;
      let total = 0;

      switch (main.type) {
        case RegistrationType.MEMBER:
          total = 105;
          break;
        case RegistrationType.STUDENT:
          total = 85;
          break;
        case RegistrationType.FAMILY:
          total = 85;
          for (const person of main.morePersons) {
            total +=
              dateUtil.getTypeByBirthday(person.birthday) === AgeType.CHILD
                ? 15
                : 25;
          }
          break;
      }
      return total;
    },
    isStudentIdentificationActive(state) {
      return state.registrationData.mainData.type === RegistrationType.STUDENT;
    },

    isBasicFormValid(state) {
      const m = state.registrationData.mainData;

      const baseValid =
        !!m.type &&
        !!m.reason &&
        !!m.name &&
        !!m.surename &&
        !!m.birthday &&
        !!m.gender &&
        !!m.email &&
        !!m.phone &&
        !!m.street &&
        !!m.plz &&
        !!m.place;

      const morePersonsValid = m.morePersons.every(
        (p) => !!p.name && !!p.surename && !!p.birthday && !!p.gender,
      );

      const studentIdValid = !this.isStudentIdentificationActive
        ? true
        : state.studentVerification.length > 0;

      return baseValid && morePersonsValid && studentIdValid;
    },

    isFinancialFormValid(state) {
      const financialData = state.registrationData.financialData;
      const dataApproval = state.registrationData.dataApproval;

      return (
        !!financialData.iban &&
        !!financialData.nameOfBankOwner &&
        !!financialData.sureNameBankOwner &&
        dataApproval.dataProtection === true &&
        dataApproval.dataStatute === true &&
        dataApproval.dataCorrectness === true
      );
    },
  },

  actions: {
    removeExtraPersonForm(index) {
      this.registrationData.mainData.morePersons.splice(index, 1);
    },

    validateBasic() {
      this.triedToValidateBasicForm = true;
      return this.isBasicFormValid;
    },
    validateFinancial() {
      this.triedToValidateFinancialForm = true;
      return this.isFinancialFormValid;
    },

  },
});
