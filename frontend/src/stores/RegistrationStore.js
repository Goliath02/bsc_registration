import {defineStore} from 'pinia'
import axios from "axios";

export const useRegistrationStore = defineStore('registrationStore', {
	state: () => ({

		registrationData: {
			mainData: {
				type: "",
				reason: "",
				name: "",
				surename: "",
				birtday: "",
				gender: "",
				email: "",
				phone: "",
				street: "",
				plz: "",
				place: "",
			},

			financial: {
				iban: "",
				bic: "",
				bankName: "",
				bankPlace: "",
				bankOwnerName: "",
				bankOwnerSureName: "",
			},

			dataProtection: false,
			correctness: false,
			hiddenSecurityCheck: false,

			morePersons: [],
		},

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

			financialData: {
				bankname: false,
				bankPlace: false,
				iban: false,
				bic: false,
				nameOfBankOwner: false,
				surenameOfBankOwner: false,
				dataProtection: false,
				correctness: false,
			}

		},

		triedToValidate: false

	}),

	actions: {
		postData() {
			var data = this.registrationData;

			axios.post("/registrate", data);
		},

		removeExtraPersonForm(index) {
			useRegistrationStore().registrationData.morePersons.splice(index, 1);
		},

		isFormCorrect() {

			useRegistrationStore().triedToValidate = true;

			useRegistrationStore().updateValidation();

			return useRegistrationStore().isFilled.defaultData.type &&
				useRegistrationStore().isFilled.defaultData.reason &&
				useRegistrationStore().isFilled.defaultData.name &&
				useRegistrationStore().isFilled.defaultData.surename &&
				useRegistrationStore().isFilled.defaultData.birthday &&
				useRegistrationStore().isFilled.defaultData.gender &&
				useRegistrationStore().isFilled.defaultData.email &&
				useRegistrationStore().isFilled.defaultData.phone &&
				useRegistrationStore().isFilled.defaultData.street &&
				useRegistrationStore().isFilled.defaultData.plz &&
				useRegistrationStore().isFilled.defaultData.place;
		},

		updateValidation() {
			useRegistrationStore().isFilled.defaultData.type = !(!useRegistrationStore().registrationData.mainData.type);
			useRegistrationStore().isFilled.defaultData.reason = !(!useRegistrationStore().registrationData.mainData.reason);
			useRegistrationStore().isFilled.defaultData.name = !(!useRegistrationStore().registrationData.mainData.name);
			useRegistrationStore().isFilled.defaultData.surename = !(!useRegistrationStore().registrationData.mainData.surename);
			useRegistrationStore().isFilled.defaultData.birthday = !(!useRegistrationStore().registrationData.mainData.birtday);
			useRegistrationStore().isFilled.defaultData.gender = !(!useRegistrationStore().registrationData.mainData.gender);
			useRegistrationStore().isFilled.defaultData.email = !(!useRegistrationStore().registrationData.mainData.email);
			useRegistrationStore().isFilled.defaultData.phone = !(!useRegistrationStore().registrationData.mainData.phone);
			useRegistrationStore().isFilled.defaultData.street = !(!useRegistrationStore().registrationData.mainData.street);
			useRegistrationStore().isFilled.defaultData.plz = !(!useRegistrationStore().registrationData.mainData.plz);
			useRegistrationStore().isFilled.defaultData.place = !(!useRegistrationStore().registrationData.mainData.place);
		},

		checkMorePersonsData() {

			const morePersons = useRegistrationStore().registrationData.morePersons;

			for (let i = 0; i < morePersons.length; i++) {

				if (!morePersons[i].name || !morePersons[i].surename || !morePersons[i].birtday || !morePersons[i].gender) {
					return false;
				}
			}
		}
	}
})