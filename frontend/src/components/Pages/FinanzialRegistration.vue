<script>
import PeronenAuswahl from "@/components/BasicRegistration/PeronenAuswahl.vue";
import FormHeader from "@/components/FormHeader.vue";
import GrundAuwahl from "@/components/BasicRegistration/GrundAuwahl.vue";
import GenderSelection from "@/components/BasicRegistration/GenderSelection.vue";
import IBANInput from "@/components/FinancesRegistration/IBANInput.vue";
import BSCCheckbox from "@/components/FinancesRegistration/BSCCheckbox.vue";
import DatenschutzCheckbox from "@/components/FinancesRegistration/DatenschutzCheckbox.vue";
import ReworkedBSCInput from "@/components/BasicRegistration/BSCInput.vue";
import {useRegistrationStore} from "@/stores/RegistrationStore.js";

export default {
	name: "FinanzialRegistration",
	components: {
		BSCInput: ReworkedBSCInput,
		DatenschutzCheckbox, BSCCheckbox, IBANInput,  GenderSelection, GrundAuwahl, FormHeader, PeronenAuswahl
	},
	data() {
		return {

			financial: {
				iban: "",
				bankName: "",
				bankPlace: "",
				bankOwnerName: "",
				bankOwnerSureName: "",
			},

			datenschutz: "",
			richtigkeit: "",
			hiddenSecurityCheck: ""
		}
	},

	methods: {
		useRegistrationStore,

		receiveIBAN(iban) {
			useRegistrationStore().registrationData.financial.iban = iban;
		},
	},

}
</script>

<template>

	<FormHeader header-text="Mitgliederregistrierung"/>

	<div class="flex flex-1 flex-col gap-[1em] max-h-[65vh] overflow-y-auto px-[2em] py-[1em]">
		<div class="flex gap-[1em]">
			<BSCInput v-model="useRegistrationStore().registrationData.financial.bankName" header-field="Bankname"/>
			<BSCInput v-model="useRegistrationStore().registrationData.financial.bankPlace" header-field="Bankort"/>
		</div>

		<i-b-a-n-input @IBAN-change="receiveIBAN"/>

		<div>
			<BSCInput v-model="useRegistrationStore().registrationData.financial.bic" header-field="BIC"/>
		</div>

		<div class="flex gap-[1em]">
			<BSCInput v-model="useRegistrationStore().registrationData.financial.bankOwnerName"
			                      header-field="Vorname des Kontoinhabers"/>
			<BSCInput v-model="useRegistrationStore().registrationData.financial.bankOwnerSureName"
			                      header-field="Nachname des Kontoinhabers"/>
		</div>

		<datenschutz-checkbox v-model="useRegistrationStore().registrationData.dataProtection"/>
		<b-s-c-checkbox v-model="useRegistrationStore().registrationData.correctness"
		                label-text="Hiermit bestätige ich, dass alle angegebenen Daten der richtigkeit ensprechen."/>

		<input v-model="useRegistrationStore().registrationData.hiddenSecurityCheck" class="invisible h-0" type="checkbox">

	</div>

</template>

<style scoped>

</style>