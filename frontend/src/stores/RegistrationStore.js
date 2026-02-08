import { defineStore } from "pinia";
import * as dateUtil from "@/utils/dateUtil.js";
import { RegistrationType } from "@/stores/Registration.ts";

export const useRegistrationStore = defineStore("registrationStore", {
  state: () => ({
    registrationData: {
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
        plz: "",
        place: "",
        entryDate: null,

        morePersons: [],
      },

      financial: {
        iban: "",
        nameOfBankOwner: "",
        sureNameBankOwner: "",
        dataProtection: false,
        dataStatute: false,
        dataCorrectness: false,
        hiddenSecurityCheck: false,
      },
    },

    studentIdentification: [],

    isFilled: {
      defaultData: {
        type: false,
        reason: false,
        name: false,
        surename: false,
        birthday: false,
        gender: false,
        email: false,
        phone: false,
        street: false,
        plz: false,
        place: false,
      },

      studentIdentification: false,

      financialData: {
        iban: false,
        nameOfBankOwner: false,
        surenameOfBankOwner: false,
      },
      dataProtection: false,
      statute: false,
      correctness: false,
    },

    triedToValidateBasicForm: false,
    triedToValidateFinancialForm: false,

    isLoadingRequest: false,
    requestFailed: false,
    requestFailedWithError: "",
  }),

  getters: {
    price(state) {
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
      return state.registrationData.mainData.type === "Schüler/Student über 18";
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
        : state.studentIdentification.length > 0;

      return baseValid && morePersonsValid && studentIdValid;
    },

    isFinancialFormValid(state) {
      const f = state.registrationData.financial;

      return (
        !!f.iban &&
        !!f.nameOfBankOwner &&
        !!f.sureNameBankOwner &&
        f.dataProtection === true &&
        f.dataStatute === true &&
        f.dataCorrectness === true
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



