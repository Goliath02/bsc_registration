import {defineStore} from 'pinia'

export const useRegistrationStore = defineStore('registrationStore', {
    state: () => ({

        registrationData: {
            mainData: {
                type: "",
                reason: "",
                name: "",
                surename: "",
                birthday: "",
                gender: "",
                email: "",
                phone: "",
                street: "",
                plz: "",
                place: "",
                entryDate: "",
            },

            financial: {
                iban: "",
                nameOfBankOwner: "",
                sureNameBankOwner: "",
            },

            dataProtection: false,
            statute: false,
            correctness: false,
            hiddenSecurityCheck: false,

            morePersons: [],

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
        requestFailedWithError: ""
    }),

    actions: {

        removeExtraPersonForm(index) {
            useRegistrationStore().registrationData.morePersons.splice(index, 1);
        },

        isDefaultDataFormCorrect() {

            useRegistrationStore().triedToValidateBasicForm = true;

            useRegistrationStore().updateBasicValidation();

            return useRegistrationStore().isFilled.defaultData.type &&
                useRegistrationStore().isFilled.defaultData.reason &&
                (useRegistrationStore().isFilled.studentIdentification || !this.isStudenIdentificationActive()) &&
                useRegistrationStore().isFilled.defaultData.name &&
                useRegistrationStore().isFilled.defaultData.surename &&
                useRegistrationStore().isFilled.defaultData.birthday &&
                useRegistrationStore().isFilled.defaultData.gender &&
                useRegistrationStore().isFilled.defaultData.email &&
                useRegistrationStore().isFilled.defaultData.phone &&
                useRegistrationStore().isFilled.defaultData.street &&
                useRegistrationStore().isFilled.defaultData.plz &&
                useRegistrationStore().isFilled.defaultData.place &&
                useRegistrationStore().checkMorePersonsData();
        },

        isStudenIdentificationActive() {

            if (useRegistrationStore().registrationData.mainData.type === 'Schüler/Student über 18') {
                return true;
            }
            return useRegistrationStore().isFilled.studentIdentification;
        },

        isFinancialFormCorrect() {

            useRegistrationStore().triedToValidateFinancialForm = true;

            useRegistrationStore().updateFinancialValidation();

            return useRegistrationStore().isFilled.financialData.iban &&
                useRegistrationStore().isFilled.financialData.nameOfBankOwner &&
                useRegistrationStore().isFilled.financialData.surenameOfBankOwner &&
                useRegistrationStore().isFilled.dataProtection &&
                useRegistrationStore().isFilled.correctness &&
                useRegistrationStore().isFilled.statute;
        },

        updateBasicValidation() {
            useRegistrationStore().isFilled.defaultData.type = !(!useRegistrationStore().registrationData.mainData.type);
            useRegistrationStore().isFilled.defaultData.reason = !(!useRegistrationStore().registrationData.mainData.reason);
            useRegistrationStore().isFilled.studentIdentification = !((useRegistrationStore().studentIdentification.length === 0 || !this.isStudenIdentificationActive()));
            useRegistrationStore().isFilled.defaultData.name = !(!useRegistrationStore().registrationData.mainData.name);
            useRegistrationStore().isFilled.defaultData.surename = !(!useRegistrationStore().registrationData.mainData.surename);
            useRegistrationStore().isFilled.defaultData.birthday = !(!useRegistrationStore().registrationData.mainData.birthday);
            useRegistrationStore().isFilled.defaultData.gender = !(!useRegistrationStore().registrationData.mainData.gender);
            useRegistrationStore().isFilled.defaultData.email = !(!useRegistrationStore().registrationData.mainData.email);
            useRegistrationStore().isFilled.defaultData.phone = !(!useRegistrationStore().registrationData.mainData.phone);
            useRegistrationStore().isFilled.defaultData.street = !(!useRegistrationStore().registrationData.mainData.street);
            useRegistrationStore().isFilled.defaultData.plz = !(!useRegistrationStore().registrationData.mainData.plz);
            useRegistrationStore().isFilled.defaultData.place = !(!useRegistrationStore().registrationData.mainData.place);
        },

        updateFinancialValidation() {
            useRegistrationStore().isFilled.financialData.nameOfBankOwner = !(!useRegistrationStore().registrationData.financial.nameOfBankOwner);
            useRegistrationStore().isFilled.financialData.surenameOfBankOwner = !(!useRegistrationStore().registrationData.financial.sureNameBankOwner);
            useRegistrationStore().isFilled.dataProtection = !(!useRegistrationStore().registrationData.dataProtection);
            useRegistrationStore().isFilled.statute = !(!useRegistrationStore().registrationData.statute);
            useRegistrationStore().isFilled.correctness = !(!useRegistrationStore().registrationData.correctness);

        },

        checkMorePersonsData() {

            const morePersons = useRegistrationStore().registrationData.morePersons;

            for (let i = 0; i < morePersons.length; i++) {

                if (!morePersons[i].name || !morePersons[i].surename || !morePersons[i].birthday || !morePersons[i].gender) {
                    return false;
                }
            }
            return true;
        },

        getTargetURL() {
            return import.meta.env.DEV ? "http://localhost:8080" : "";
        }
    }
})